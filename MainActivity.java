package com.example.flashcardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestions = findViewById(R.id.flashcard_question_textview);
        TextView flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        flashcardQuestions.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View w)
        {
            flashcardQuestions.setVisibility(View.INVISIBLE);
            flashcardAnswer.setVisibility(View.VISIBLE);
        }
    });
 }
}