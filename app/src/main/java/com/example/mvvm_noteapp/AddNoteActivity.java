package com.example.mvvm_noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {


    public static final String EXTRA_ID =
            "com.example.mvvm_noteapp.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.mvvm_noteapp.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.mvvm_noteapp.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.mvvm_noteapp.EXTRA_PRIORITY";

    private EditText editTextTitle, editTextDiscription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDiscription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_piker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent =getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDiscription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else{
            setTitle("Add Note");
        }


    }

    private void saveNote() {
        String title=editTextTitle.getText().toString();
        String description=editTextDiscription.getText().toString();
        int priority=numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please insert title and description...",Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE , title);
        data.putExtra(EXTRA_DESCRIPTION , description);
        data.putExtra(EXTRA_PRIORITY , priority);

        int id=getIntent().getIntExtra(EXTRA_ID , -1);
        if(id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult( RESULT_OK , data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
