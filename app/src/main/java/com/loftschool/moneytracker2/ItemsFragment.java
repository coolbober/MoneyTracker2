package com.loftschool.moneytracker2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.loftschool.moneytracker2.api.AddResult;
import com.loftschool.moneytracker2.api.Api;
import com.loftschool.moneytracker2.api.RemoveResult;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.loftschool.moneytracker2.Item.TYPE_UNKNOWN;


public class ItemsFragment extends Fragment {

    private static final int LOAD_ITEMS = 0;
    private static final int LOAD_ADD = 1;
    private static final int LOAD_REMOVE = 2;
    private static final String KEY_TYPE = "TYPE";

    private  String type = TYPE_UNKNOWN;

    private  ItemsAdapter adapter;
    private Api api;

    private ActionMode actionMode;

    public static  ItemsFragment createItemFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.KEY_TYPE, type);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        type = getArguments().getString(KEY_TYPE, TYPE_UNKNOWN);
        if (type.equals(TYPE_UNKNOWN)) {
            throw new IllegalStateException("Unkhown fragment Type");
        }

        api = ((App)getActivity().getApplication()).getApi();
        adapter = new ItemsAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);

        adapter.setListener(new ItemsAdapterListener() {

            @Override
            public void onItemClick(Item item, int position) {
                if(isInActionMode()){
                    toggleSelection(position);
                }
            }

            @Override
            public void onItemLongClick(Item item, int position) {
                if(isInActionMode()){
                    return;
                }
                actionMode = ((AppCompatActivity)getActivity()).startSupportActionMode(actionModeCallback);
                toggleSelection(position);
            }
            private void toggleSelection(int position) {

                adapter.toggleSelection(position);
                if (actionMode != null)
                    actionMode.setTitle(getString(R.string.selected).concat(" ").concat((String.valueOf(adapter.getSelectedItemsIndex().size()))));
            }
            private boolean isInActionMode(){
                return actionMode != null;
            }
        });



        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_TYPE, type);
                startActivityForResult(intent, AddActivity.RC_ADD_ITEM);
            }
        });
        loadItems();
    }

    private  void loadItems() {
        getLoaderManager().initLoader(LOAD_ITEMS, null, new LoaderManager.LoaderCallbacks<List<Item>>() {

            @Override
            public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<List<Item>>(getContext()) {
                    @Override
                    public List<Item> loadInBackground() {
                        try {
                            List<Item> items = api.items(type).execute().body();
                            return items;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<List<Item>> loader, List<Item> items) {
                if (items == null) {
                showError(getString(R.string.errorLoading));
                }else {
                    adapter.setItems(items);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Item>> loader) {

            }
        }).forceLoad();
    }


    private void addItem(final Item item) {
        getLoaderManager().restartLoader(LOAD_ADD, null, new
                LoaderManager.LoaderCallbacks<AddResult>() {
                    @Override
                    public Loader<AddResult> onCreateLoader(int id, Bundle args) {
                        return new AsyncTaskLoader<AddResult>(getContext()) {
                            @Override
                            public AddResult loadInBackground() {
                                try {
                                    return api.add(item.name, item.price, item.type).
                                            execute().body();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        };
                    }

                    @Override
                    public void onLoadFinished(Loader<AddResult> loader, AddResult data) {
                       // adapter.updateId(item, data.id);
                       // adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onLoaderReset(Loader<AddResult> loader) {
                    }

                }).forceLoad();
    }

//    private void removeItem(final Item item) {
//        getLoaderManager().restartLoader(LOAD_REMOVE, null, new
//                LoaderManager.LoaderCallbacks<RemoveResult>() {
//                    @SuppressLint("StaticFieldLeak")
//                    @Override
//                    public Loader<RemoveResult> onCreateLoader(int id, Bundle args) {
//                        return new AsyncTaskLoader<RemoveResult>(getContext()) {
//                            @Override
//                            public RemoveResult loadInBackground() {
//                                try {
//                                    return api.remove(item.id).execute().body();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                    return null;
//                                }
//                            }
//                        };
//                    }
//
//                    @Override
//                    public void onLoadFinished(Loader<RemoveResult> loader, RemoveResult data) {
//
//                    }
//
//                    @Override
//                    public void onLoaderReset(Loader<RemoveResult> loader) {
//
//                    }
//                }).forceLoad();
//    }

    private void removeItem( int id) {
        api.remove(id).enqueue(new Callback<RemoveResult>() {
            @Override
            public void onResponse(Call<RemoveResult> call, Response<RemoveResult> response) {
                //adapter.notifyDataSetChanged();
                if (response.isSuccessful())
                    adapter.notifyDataSetChanged();
                else {
                    showError("Ошибка запроса!");
                }
            }
            @Override
            public void onFailure(Call<RemoveResult> call, Throwable t) {
                showError("Error");
            }
        });
    }

    private void showError (String error) {
        makeText(getContext(), error, LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddActivity.RC_ADD_ITEM && resultCode == RESULT_OK) {
            Item item = (Item) data.getSerializableExtra(AddActivity.RESULT_ITEM);
            addItem(item);
        }
    }

    private void removeSelectedItems(){
        for (int i = adapter.getSelectedItemCount() - 1; i >= 0; i--) {
            Item item = adapter.remove(adapter.getSelectedItemsIndex().get(i));
            removeItem(item.id);
        }
        loadItems();
    }


    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.menu_remove:
                     showDialog();
                     return true;

                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    };


    private void showDialog(){
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.show(getFragmentManager(), "Confirmation");
        dialog.setListener(new ConfirmationDialogListener() {
            @Override
            public void onPositiveClick(DialogInterface dialog, int button) {
                removeSelectedItems();
                actionMode.finish();
            }

            @Override
            public void onNegativeClick(DialogInterface dialog, int button) {
                actionMode.finish();
            }
        });
    }

}
