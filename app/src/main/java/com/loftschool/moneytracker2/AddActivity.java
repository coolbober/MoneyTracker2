package com.loftschool.moneytracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import static com.loftschool.moneytracker2.R.layout.activity_add;

public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final String RESULT_ITEM = "item";
    public static final String RESULT_NUMBER = "number";
    public static final int RC_ADD_ITEM = 99;

    private EditText name;
    private  EditText price;
    private ImageButton add;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.name);
        price = findViewById(R.id.number);
        add = findViewById(R.id.add);
        add.setEnabled(false);
        type = getIntent().getStringExtra(EXTRA_TYPE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra(RESULT_ITEM, new Item(name.getText().toString(), Integer.valueOf(price.getText().toString()), type));
                setResult(RESULT_OK, result);
                finish();
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                add.setEnabled(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(price.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                add.setEnabled(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty((price.getText())));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

}
