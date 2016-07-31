package com.teabreaktechnology.kuzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KuzzleActivity extends AppCompatActivity {

    int[] colorCodes = {0xff0000, 0x00ff00, 0x0000ff,0xFFFFFF,0xFFFF00,0xFF00FF};
    char[] colors = {'R', 'G', 'B', 'W','Y','P'};


    String[] players = new String[]{"Kishore","Karthik"};
    int[] scores = new int[]{0,0};
    Spinner[] spinners= new Spinner[3];
    int[] spinnerIds = new int[]{R.id.color1,R.id.color2,R.id.color3};
    int currentPlayerId = 0;
    List<Play> priorPlays = new ArrayList<>();
    private List<Play> priorPlayDetails;
    int[] answer = new int[]{2,1,4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuzzle);
        setPlayerDetailsText(currentPlayerId);
        setPriorPlayDetails(priorPlays);

        for(int i=0; i<Constants.colors.length/2; i++) {
            createSpinner(Constants.colors,i);
        }
        setColoredSpinner();
        setOnClickListner();


        ImageView image = (ImageView) findViewById(R.id.imageView);
        CharacterDrawable drawable = new CharacterDrawable('A', 0xFF805781);
        image.setImageDrawable(drawable);

        ArrayList<ItemData> list = new ArrayList<>();
        for (int colorCode : colorCodes) {
            list.add(new ItemData("", colorCode));
        }

        final Spinner sp = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this,R.layout.spinner_layout, R.id.txt, list);

    }

    private void setOnClickListner() {
        Button button =  (Button)findViewById(R.id.next);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int[] selectedColors = readColors();
                Play play = scoreThePlay(players[currentPlayerId], selectedColors, answer);
                priorPlays.add(play);
                setNextPlayer();
                setPlayerDetailsText(currentPlayerId);
                setPriorPlayDetails(priorPlays);

            }
        });

    }

    private void setNextPlayer() {
        if(currentPlayerId==0){
            currentPlayerId=1;
        }else{
            currentPlayerId=0;
        }
    }


    private int[] readColors(){
        int[] selectedColors = new int[Constants.colors.length/2];
        for(int i=0;i<Constants.colors.length/2;i++){
            Spinner colorsSpinner = (Spinner) findViewById(spinnerIds[i]);
            int selectedItemId = (int)colorsSpinner.getSelectedItemId();
            selectedColors[i] = selectedItemId;
        }
        return selectedColors;

    }
    private void setPlayerDetailsText(int playerID){
        String playerName = players[playerID];
        TextView textView = (TextView)findViewById(R.id.playername);
        textView.setText("Current Player :" + playerName);
    }

    private void createSpinner(String[] colors,int i) {

        final Spinner colorsSpinner = (Spinner) findViewById(spinnerIds[i]);
        ArrayAdapter<String> colorsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colors);
        colorsSpinner.setAdapter(colorsAdaptor);
        colorsSpinner.setSelection(2);
        spinners[i]=colorsSpinner;
    }

    public Play scoreThePlay(String playerName, int[] colors, int[] answer){
        int colorOnlyCount = colorOnlyMatch(colors, answer);
        int colorAndPosMatchCount = colorAndPosMatch(colors, answer);
        Play play = new Play.Builder().playerName(playerName).colorOnlyMatch(colorOnlyCount)
                .colorAndPosMatch(colorAndPosMatchCount)
                .selectedColors(colors)
                .build();
        return play;
    }

    private int colorAndPosMatch(int[] colors, int[] answer) {
        int colorAndPosMatchCount = 0;

        for (int i = 0; i < answer.length; i++) {
            if(colors[i]==answer[i]){
                colorAndPosMatchCount++;
            }
        }
        return colorAndPosMatchCount;
    }

    private int colorOnlyMatch(int[] colors, int[] answer) {
        int colorOnlyCount = 0;
        for (int i = 0; i < colors.length; i++) {
            if(colorExists(answer,colors[i])){
                colorOnlyCount++;
            }
        }
        return colorOnlyCount;
    }

    private boolean colorExists(int[] answer, int color) {
        for (int colorId : answer) {
            if(colorId == color){
                return true;
            }
        }
        return false;
    }

    public void setPriorPlayDetails(List<Play> priorPlayDetails) {
        StringBuilder sb = new StringBuilder();
        for (Play play : priorPlayDetails) {
            sb.append(play.toString()).append("\n");

        }
        TextView textView = (TextView)findViewById(R.id.scores);
        textView.setText(sb.toString());

    }


    public void setColoredSpinner(){
        ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData("",R.drawable.r));
        list.add(new ItemData("",R.drawable.g));
        list.add(new ItemData("",R.drawable.b));

        Spinner sp=(Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter=new SpinnerAdapter(this,
                R.layout.spinner_layout,R.id.txt,list);
        sp.setAdapter(adapter);
    }
}
