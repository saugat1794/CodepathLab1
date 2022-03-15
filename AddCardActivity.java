package com.example.flashcardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddCardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView closeIcon = findViewById(R.id.flashcard_close_button);
        ImageView saveIcon = findViewById(R.id.flashcard_save_button);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveIcon.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View v){
            Intent data = new Intent();
            String inputQuestion = ((EditText) findViewById(R.id.flashcard_question_editText)).getText().toString();
            String inputAnswer = ((EditText) findViewById(R.id.flashcard_answer_editText)).getText().toString();
            data.putExtra("QUESTION", inputQuestion);
            data.putExtra("ANSWER",inputAnswer);
            setResult(RESULT_OK, data);
            finish();
        }
        });
    }



}

