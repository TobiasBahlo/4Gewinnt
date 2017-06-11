package com.bahlot.a4gewinnt.frontend;


        import android.content.Intent;
        import android.graphics.Color;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import com.bahlot.a4gewinnt.backend.*;


public class Spielbrett extends AppCompatActivity implements View.OnClickListener{
    Bundle extra = new Bundle();
    public Button buttonOne;
    public Button buttonSecond;
    public Button buttonThird;
    public Button buttonFourth;
    public Button buttonFith;
    public Button buttonSixth;
    public Button buttonSeventh;
    public ImageButton buttonExit;
    public ImageButton buttonTheExit;
    public Button buttonSinglePStart;
    public Button buttonMultiPStart;
    public Button buttonFacebookAPIConnect;

    private String nameOne;
    private String nameTwo;

    private eColor colorOne;
    private eColor colorTwo;
    private String colorOneS;
    private String colorTwoS;
    private TextView textVActive;
    iGame game = new Game();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielbrett);

        firstStart();

        //setContentView(R.layout.landscape);
        //setContentView(R.layout.portrait);

    }
    public void setCoins(String color,int x, int y){
        String ext="";
        if (color.equals("red")){
            ext = "redcoin";
        }
        else{
            ext="bluecoin";
        }

        if (y==4){
            y=3;
        }
        else if(y==3){
            y=4;
        }
        if (y==5){
            y=2;
        }
        else if(y==2){
            y=5;
        }
        if (y==6){
            y=1;
        }
        else if(y==1){
            y=6;
        }
        ext +="_"+x+"_"+y;
        int coinID = getResources().getIdentifier(ext, "id", getPackageName());
        findViewById(coinID).setVisibility(View.VISIBLE);


    }
    public void firstStart() {
        buttonTheExit = (ImageButton) findViewById(R.id.exittoclose);
        //buttonTheExit.setOnClickListener(this);
        buttonSinglePStart = (Button) findViewById(R.id.SinglePlayerStart);
        //buttonSinglePStart.setOnClickListener(this);
        buttonMultiPStart = (Button) findViewById(R.id.CreateMultiplayer);
        //buttonMultiPStart.setOnClickListener(this);
        buttonFacebookAPIConnect = (Button) findViewById(R.id.facebookApiConnect);
        //buttonFacebookAPIConnect.setOnClickListener(this);
        buttonOne = (Button) findViewById(R.id.firstRow);
        //buttonOne.setOnClickListener(this);
        buttonSecond = (Button) findViewById(R.id.secondRow);
        //buttonSecond.setOnClickListener(this);
        buttonThird = (Button) findViewById(R.id.thirdRow);
        //buttonThird.setOnClickListener(this);
        buttonFourth = (Button) findViewById(R.id.fourthRow);
        //buttonFourth.setOnClickListener(this);
        buttonFith = (Button) findViewById(R.id.fithRow);
        //buttonFith.setOnClickListener(this);
        buttonSixth = (Button) findViewById(R.id.sixthRow);
        //buttonSixth.setOnClickListener(this);
        buttonSeventh = (Button) findViewById(R.id.seventhRow);
        //buttonSeventh.setOnClickListener(this);
        buttonExit = (ImageButton) findViewById(R.id.close);
        //buttonExit.setOnClickListener(this);
        extra = getIntent().getExtras();
        nameOne = (String) extra.get("nameOne");
        nameTwo = extra.getString("nameTwo");
        colorOneS = extra.getString("colorOne");
        colorTwoS = extra.getString("colorTwo");
        if(colorOneS.equals("blue")){
            colorOne = eColor.blue;
            colorTwo = eColor.red;
        }else{
            colorOne = eColor.red;
            colorTwo = eColor.blue;
        }
        Log.d("colorOne",colorOneS);
        Log.d("colorTwo",colorTwoS);
        Log.d("nameOne",nameOne);
        Log.d("nameTwo",nameTwo);

        game.startGame(nameOne, colorOne, nameTwo, colorTwo);
        textVActive = (TextView) findViewById(R.id.activePlayer);
        setName();
    }
    public void setName(){
        if(game.getCurentPlayerColor().equals("red")){
            textVActive.setTextColor(Color.RED);
        }else {
            textVActive.setTextColor(Color.BLUE);
        }
        textVActive.setText(game.getCurentPlayerName());
        Log.d("Name:"," "+game.getCurentPlayerName());
    }
    public void checkWin(){
        if(game.winGame()){

            Intent intent = new Intent(Spielbrett.this,GameWin.class);
            intent.putExtra("winner",game.getPositionOfPlayer(game.getCurentPlayerName()));
            intent.putExtra("winPlayerName",game.getCurentPlayerWinName().toString());
            intent.putExtra("wincolor",game.getCurentPlayerWinColor());
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.firstRow : game.setCoin(0);
                Log.d("First move","Value X: "+game.getMove()[0]);
                Log.d("First move","Value Y: "+game.getMove()[1]);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.secondRow : game.setCoin(1);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.thirdRow : game.setCoin(2);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.fourthRow : game.setCoin(3);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.fithRow : game.setCoin(4);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.sixthRow : game.setCoin(5);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.seventhRow : game.setCoin(6);
                setCoins(game.getCurentPlayerColorZug(),game.getMove()[1]+1,game.getMove()[0]+1);
                checkWin();
                setName();
                break;
            case R.id.close :finish();
                System.exit(0); //Exit;
                break;
            case R.id.exittoclose :finish();
                System.exit(0); //Exit;
                break;

            //case R.id.menu : //Menu;
            //  break;


        }
    }
}
