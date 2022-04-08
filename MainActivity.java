package com.example.flashcardsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        answerTextView.setVisibility(View.INVISIBLE);

        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
              //  View answerSideView = findViewById(R.id.flashcard_answer_textview);

                // get the center for the clipping circle
                int cx = answerTextView.getWidth() / 2;
                int cy = answerTextView.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerTextView, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                questionTextView.setVisibility(View.INVISIBLE);
                answerTextView.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();

            }
        });


        answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                answerTextView.setVisibility(View.INVISIBLE);
                questionTextView.setVisibility(View.VISIBLE);

            }
        });


        ImageView icon = findViewById(R.id.flashcard_add_button);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                // MainActivity.this.startActivity(intent);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right, R.anim.left);

            }
        });



        allFlashCards = flashcardDatabase.getAllCards();
        ImageView arrowIcon = findViewById(R.id.flashcard_arrow_button);
        if (allFlashCards != null && allFlashCards.size() > 0) {
            Flashcard firstCard = allFlashCards.get(0);
            questionTextView.setText(firstCard.getQuestion());
            answerTextView.setText(firstCard.getAnswer());
        }

        arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashCards.size() == 0)
                    return;
                currentCardIndex++;

                if (currentCardIndex >= allFlashCards.size()) {
                    Snackbar.make(questionTextView, "You've reached the end of the card, going back to start",
                            Snackbar.LENGTH_SHORT).show();
                    currentCardIndex = 0;
                }

                final Animation leftAni = AnimationUtils.loadAnimation(v.getContext(), R.anim.left);
                final Animation rightAni = AnimationUtils.loadAnimation(v.getContext(), R.anim.right);

                leftAni.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        questionTextView.startAnimation(rightAni);
                        allFlashCards = flashcardDatabase.getAllCards();
                        Flashcard flashcard = allFlashCards.get(currentCardIndex);
                        questionTextView.setText(flashcard.getQuestion());
                        answerTextView.setText(flashcard.getAnswer());
                        questionTextView.setVisibility(View.VISIBLE); // might
                        answerTextView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                questionTextView.startAnimation(leftAni);
            }
        });
    }
        @Override
        public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == RESULT_OK) {
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

