/*  created by Tran Tuan Anh - HUST - 2016
 *  copyright (c) 2016
 */

package com.example.mylaptop.colorblock;

import android.content.Intent;

import android.graphics.drawable.ColorDrawable;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import android.widget.TextView;

import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {

    final static int REQUEST_CODE = 1;

    public boolean isOver = false;          // --- isOver = false : game doesn't end ; isOver = true : game ended ---
    public boolean easy = true;             // --- easy = true : level easy -> level will be changed after 9 seconds ---

    public TextView block0, block1, block2, block3;        // --- 4 blocks color in game ---
    public TextView textViewHorizontal;                    // --- horizontal color bar above 4 buttons in game ---
    public TextView textViewScore;                         // --- display score of user ---

    public Random random = new Random();                   // --- random digit tool ---

    public int currentScore = 0,highScore = 0;

    public TranslateAnimation animation0,animation1,animation2,animation3;      // --- 4 animations match with 4 blocks each one ---

    public CountDownTimer countDownTimer;                                       // --- count down timer for set game 's level ---

    int colorID[] = {0, 0, 0, 0};                   // --- array id of color in resource ---
    int drawbleID[] = {0, 0, 0, 0};                 // --- array id of drawable ---
    int idOfBlockBackground[] = {0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen_activity);
        makeColorAndDrawable();
        playGame();
    }

    public void playGame(){
        getView();          // --- get view and initialize background for its ---
        makeAnimation();
        makeCountDownTimer();

        countDownTimer.start();
        startGame();
    }

    public void makeCountDownTimer(){
        countDownTimer = new CountDownTimer(9000,1) {       // --- after 9 seconds level will be changed
            @Override
            public void onTick(long millisUntilFinished) {
                easy = true;
            }

            @Override
            public void onFinish() {
                easy = false;
            }
        };
    }

    public void makeColorAndDrawable(){
        colorID[0] = getResources().getColor(R.color.colorRed);
        colorID[1] = getResources().getColor(R.color.colorYellowDark);
        colorID[2] = getResources().getColor(R.color.colorGreen);
        colorID[3] = getResources().getColor(R.color.colorBlue);

        drawbleID[0] = getResources().getIdentifier("border_red", "drawable", getPackageName());
        drawbleID[1] = getResources().getIdentifier("border_yellow","drawable",getPackageName());
        drawbleID[2] = getResources().getIdentifier("border_green","drawable",getPackageName());
        drawbleID[3] = getResources().getIdentifier("border_blue","drawable",getPackageName());
    }

    public void makeAnimation(){
        animation0 = new TranslateAnimation(0, 0, 0, 1260);
        animation0.setDuration(1800);           // --- set time run for each process of animation ---
        animation0.setFillAfter(true);
        animation0.setAnimationListener(new MyAnimationListener(block0, 0));


        animation1 = new TranslateAnimation(0, 0, 0, 1260);
        animation1.setDuration(2400);           // --- set time run for each process of animation ---
        animation1.setFillAfter(true);
        animation1.setAnimationListener(new MyAnimationListener(block1,1));


        animation2 = new TranslateAnimation(0, 0, 0, 1260);
        animation2.setDuration(2000);           // --- set time run for each process of animation ---
        animation2.setFillAfter(true);
        animation2.setAnimationListener(new MyAnimationListener(block2,2));

        animation3 = new TranslateAnimation(0, 0, 0, 1260);
        animation3.setDuration(2650);           // --- set time run for each process of animation ---
        animation3.setFillAfter(true);
        animation3.setAnimationListener(new MyAnimationListener(block3, 3));
    }

    private class MyAnimationListener implements Animation.AnimationListener{

        View view;
        int index;      // --- id of view ---

        public MyAnimationListener(View view, int index){
            this.view = view;
            this.index = index;
        }

        public void onAnimationEnd(Animation animation) {
            if (index == 0) {
                finishOnTimer0(view);
            }
            else if(index == 1){
                finishOnTimer1(view);
            }
            else if(index == 2){
                finishOnTimer2(view);
            }
            else{
                finishOnTimer3(view);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            if(index == 0) {
                animation.setStartOffset(350);          // --- set time distance between twice times run of each animation ---
            }
            else if(index == 1){
                animation.setStartOffset(500);          // --- set time distance between twice times run of each animation ---
            }
            else if(index == 2){
                animation.setStartOffset(450);          // --- set time distance between twice times run of each animation ---
            }
            else{
                animation.setStartOffset(600);          // --- set time distance between twice times run of each animation ---
            }
            setBlockBackground(view,index);
        }

        @Override
        public void onAnimationStart(Animation animation) {
            setBlockBackground(view,index);
        }
    }

    public void startGame(){
        int value;
        value = random.nextInt(2 - 0) + 0;      // --- choose which two blocks will be dropped first ---
        if(value == 0){
            block0.startAnimation(animation0);
            block1.startAnimation(animation1);
        }
        else{
            block2.startAnimation(animation2);
            block3.startAnimation(animation3);
        }
    }

    public void finishOnTimer0(View view){          // --- process when animation ended ---
        if(view.getX() == 0 && view.getY() == -269) {
            if (getBlockBackground(0) == getViewColor(textViewHorizontal)) {
                block0.clearAnimation();
                if(!isOver) {
                    if(!easy) {
                        block0.startAnimation(animation0);
                        block2.startAnimation(animation2);
                    }
                    else{
                        block2.startAnimation(animation2);
                    }
                    updateScore();
                    return;
                }
            }
            gameOver();
        }
    }

    public void finishOnTimer1(View view){          // --- process when animation ended ---
        if(view.getX() == 270 && view.getY() == -269) {
            if (getBlockBackground(1) == getViewColor(textViewHorizontal)) {
                block1.clearAnimation();
                if(!isOver) {
                    block3.startAnimation(animation3);
                    updateScore();
                    return;
                }
            }
            gameOver();
        }
    }

    public void finishOnTimer2(View view){          // --- process when animation ended ---
        if(view.getX() == 540 && view.getY() == -269) {
            if (getBlockBackground(2) == getViewColor(textViewHorizontal)) {
                block2.clearAnimation();
                if(!isOver){
                    block0.startAnimation(animation0);
                    updateScore();
                    return;
                }
            }
            gameOver();
        }
    }

    public void finishOnTimer3(View view){          // --- process when animation ended ---
        if(view.getX() == 810 && view.getY() == -269) {
            if (getBlockBackground(3) == getViewColor(textViewHorizontal)) {
                block3.clearAnimation();
                if(!isOver) {
                    block1.startAnimation(animation1);
                    updateScore();
                    return;
                }
            }
            gameOver();
        }
    }

    public void updateScore(){
        currentScore ++;
        textViewScore.setText(String.valueOf(currentScore));
    }

    public int getViewColor(View view){             // --- get background color of view ---
        int color = ((ColorDrawable)view.getBackground()).getColor();
        for(int i = 0 ; i <= 3; i++){
            if(colorID[i] == color){
                return i;
            }
        }
        return 4;
    }

    public void setBlockBackground(View view,int index){        // --- set background drawable for each block ---
        int value = random.nextInt(4 - 0) + 0;
        view.setBackgroundResource(drawbleID[value]);
        idOfBlockBackground[index] = drawbleID[value];
    }

    public int getBackgroundResource(int index){        // --- get background drawble of view via index ---
        return idOfBlockBackground[index];
    }

    public int getBlockBackground(int id){
        int viewBackgroundID = getBackgroundResource(id);
        if (viewBackgroundID == drawbleID[0]){
            return 0;
        }
        else if (viewBackgroundID == drawbleID[1]){
            return 1;
        }
        else if (viewBackgroundID == drawbleID[2]){
            return 2;
        }
        return 3;
    }

    public void gameOver(){
        if(!isOver){
            isOver = true;

            block0.clearAnimation();
            block1.clearAnimation();
            block2.clearAnimation();
            block3.clearAnimation();

            if(MainActivity.highScore < currentScore){      // --- used to set highscore ---
                MainActivity.highScore = currentScore;
            }
            highScore = MainActivity.highScore;
            Intent data = new Intent(GameScreenActivity.this, GameOverActivity.class);

            data.putExtra("currentScore",currentScore);
            data.putExtra("highScore",highScore);

            startActivityForResult(data, REQUEST_CODE); // --- call GameOverActivity ---
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("back")){
                if(data.getExtras().getInt("back") == 2){
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);        // --- Move to home page ---
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    playGame();
                    // --- Play game again ---
                }
            }
        }
    }

    public void getView(){
        block0 = (TextView)findViewById(R.id.block0);
        block1 = (TextView)findViewById(R.id.block1);
        block2 = (TextView)findViewById(R.id.block2);
        block3 = (TextView)findViewById(R.id.block3);

        block0.setBackgroundResource(drawbleID[0]);
        block1.setBackgroundResource(drawbleID[1]);
        block2.setBackgroundResource(drawbleID[2]);
        block3.setBackgroundResource(drawbleID[3]);

        for(int i = 0 ; i < 3 ; i++){
            idOfBlockBackground[i] = drawbleID[i];
        }

        textViewScore = (TextView)findViewById(R.id.txtViewScore);
        textViewHorizontal = (TextView)findViewById(R.id.txtViewHorizon);
    }

    public void onPressed(View view){           // --- handle button was clicked 's event ---
        textViewHorizontal = (TextView)findViewById(R.id.txtViewHorizon);
        switch (view.getId()){
            case (R.id.buttonRed):{
                textViewHorizontal.setBackgroundColor(getResources().getColor(R.color.colorRed));   // --- change color of horizontal bar above button ---
                break;
            }
            case (R.id.buttonYellow):{
                textViewHorizontal.setBackgroundColor(getResources().getColor(R.color.colorYellowDark));    // --- change color of horizontal bar above button ---
                break;
            }
            case (R.id.buttonGreen):{
                textViewHorizontal.setBackgroundColor(getResources().getColor(R.color.colorGreen));     // --- change color of horizontal bar above button ---
                break;
            }
            case (R.id.buttonBlue):{
                textViewHorizontal.setBackgroundColor(getResources().getColor(R.color.colorBlue));      // --- change color of horizontal bar above button ---
                break;
            }
        }
    }
}
