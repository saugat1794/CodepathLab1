package com.example.flashcardsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView questionTextView;
    TextView answerTextView;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashCards;
    int currentCardIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         flashcardDatabase = new FlashcardDatabase(getApplicationContext());

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

        ImageView arrowIcon = findViewById(R.id.flashcard_arrow_button);

        allFlashCards = flashcardDatabase.getAllCards();

        if(allFlashCards != null && allFlashCards.size() > 0) {
            Flashcard firstCard = allFlashCards.get(0);
            questionTextView.setText(firstCard.getQuestion());
            answerTextView.setText(firstCard.getAnswer());
        }

           arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(allFlashCards.size()==0)
                    return;
                currentCardIndex++;

                if(currentCardIndex >= allFlashCards.size()){
                    Snackbar.make(questionTextView,"You've reached the end of the card, going back to start",
                            Snackbar.LENGTH_SHORT).show();
                    currentCardIndex = 0;
                }

                allFlashCards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashCards.get(currentCardIndex);
                questionTextView.setText(flashcard.getAnswer());
                answerTextView.setText(flashcard.getQuestion());
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
                    questionTextView.setText(questionString);
                    answerTextView.setText(answerString);
                    flashcardDatabase.insertCard(new Flashcard(questionString, answerString));
                    allFlashCards = flashcardDatabase.getAllCards();
                }
            }
        }

}