package com.example.flashcardsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView questionTextView;
    TextView answerTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         questionTextView = findViewById(R.id.flashcard_question_textview);
         answerTextView = findViewById(R.id.flashcard_answer_textview);

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                questionTextView.setVisibility(View.INVISIBLE);
                answerTextView.setVisibility(View.VISIBLE);
            }
        });

        answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                questionTextView.setVisibility(View.VISIBLE);
                answerTextView.setVisibility(View.INVISIBLE);
            }
        });

        ImageView icon = findViewById(R.id.flashcard_add_button);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                // MainActivity.this.startActivity(intent);
                startActivityForResult(intent, 100);
            }
        });

    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode== RESULT_OK) {
                if (data != null) {
                    String questionString = data.getExtras().getString("QUESTION");
                    String answerString = data.getExtras().getString("ANSWER");
                    answerTextView.setText(answerString);
                    questionTextView.setText(questionString);
                }
            }
        }

}