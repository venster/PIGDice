package com.ashleyvenny.pairodice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {  //activity is an android class actionbar is used for menus

    private FrameLayout die1,die2; //relates to the tags in the XML
    private TextView scoreNum, p1scoreNum,p2scoreNum;
    private Button roll,hold;
    private int score=0;
    private int prevscore=-1;
    private int p1score=0;
    private int p2score=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) { //gets started when pressed
        super.onCreate(savedInstanceState); //calling method from actionbaractivity
        setContentView(R.layout.activity_main); //builds the layout     Do "clean project" to restore R


                                            //put the layout in
        die1 = (FrameLayout)findViewById(R.id.die1);  //how to instantiate  cast it to a FrameLayout
        die2 = (FrameLayout)findViewById(R.id.die2);
        scoreNum=(TextView)findViewById(R.id.RoundNum);
        roll= (Button)findViewById(R.id.button);
        p1scoreNum=(TextView)findViewById(R.id.p1score);
        p2scoreNum=(TextView)findViewById(R.id.p2score);

        //check intent if there is one, then adjust scores for players
        Intent i = getIntent();

            prevscore= i.getIntExtra("score",-1);
            p1score= i.getIntExtra("p1score",0);
            p2score= i.getIntExtra("p2score",0);
            p1scoreNum.setText(Integer.toString(p1score));
            p2scoreNum.setText(Integer.toString(p2score));
            if(prevscore>=0) {
                Toast.makeText(this, "Player 2's last round: " + prevscore + " pts", Toast.LENGTH_LONG).show();
            }
        roll.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
              if(roll.getText()=="Next")
               {
                   toPlayer2();
               }
               else{
                   rollDice();
               }
               if(roll.getText()=="Restart")
               {
                restart();
               }
               else if(p1score+score>=100)
               {
                   AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                   alertDialog.setTitle("You Won!");
                   alertDialog.setMessage("You are awesome/lucky");
                   alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                           new DialogInterface.OnClickListener() {

                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           });
                   alertDialog.show();
                   roll.setText("Restart");
                   hold.setVisibility(View.GONE);

               }

           }

        });
//when people click the hold button
        hold=(Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //add round score to player one
                p1score+=score;


                   /* // DIALOG BOX FOR WINNER
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("You Won!");
                alertDialog.setMessage("You are awesome/lucky");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                });
                alertDialog.show();


                Intent intent = new Intent(MainActivity.this,Player2.class); //like an envelope where it's starting then where its going to go.
                intent.putExtra("score",score); // take info from one activity to another.  key is "score"
                intent.putExtra("p1score",p1score);
                intent.putExtra("p2score",p2score);

                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);*/
                toPlayer2();
            }


    });



    }


    //set up the intent and go to next page
    public void toPlayer2()
    {
        Intent intent = new Intent(MainActivity.this,Player2.class); //like an envelope where it's starting then where its going to go.
        intent.putExtra("score",score); // take info from one activity to another.  key is "score"
        intent.putExtra("p1score",p1score);
        intent.putExtra("p2score",p2score);

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    public void restart()
    {
        hold.setVisibility(View.VISIBLE);
        roll.setText("ROLLOUT!");
        p1score=0;
        p2score=0;
        score=0;
        p1scoreNum.setText(Integer.toString(p1score));
        p2scoreNum.setText(Integer.toString(p2score));
        scoreNum.setText(Integer.toString(score));

    }


    //get two random numbers, change the dice to have the appropriate image

    public void rollDice(){
        int roll1 = 1+ (int)(6*Math.random());
        int roll2 = 1+ (int)(6*Math.random());
        int total= roll1+roll2;
        score = score + total;
       if(roll1==1 || roll2==1)
        {
            score = 0;
            Toast.makeText(this, "Rolled one on either dice! On to next player", Toast.LENGTH_LONG).show();
            roll.setText("Next");
            hold.setVisibility(View.GONE);
            scoreNum.setText("0");
        }

        scoreNum.setText(Integer.toString(score));
        setDie(roll1,die1);
        setDie(roll2,die2);
    }
    //set appropriate image to the FrameView for an int
    public void setDie(int value,FrameLayout die){

        Drawable pic;//pictures are drawables
        switch (value)
        {
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                die.setBackground(pic);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                die.setBackground(pic);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                die.setBackground(pic);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                die.setBackground(pic);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                die.setBackground(pic);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                die.setBackground(pic);
                break;

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
