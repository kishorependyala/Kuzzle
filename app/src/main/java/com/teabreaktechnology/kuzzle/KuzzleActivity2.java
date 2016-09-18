package com.teabreaktechnology.kuzzle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KuzzleActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuzzle2);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableLayout mainView = (TableLayout) findViewById(R.id.mainView);
        mainView.addView(inflater.inflate(R.layout.kuzzle_header, null), 0);
        List<Play> plays = new ArrayList<>();
        //plays.add(new Play.Builder().playerName("Player Name").colorOnlyMatch(1).selectedColors(new int[]{1,2,3}).colorAndPosMatch(1).build());
        plays.add(new Play.Builder().playerName("Kishore").colorOnlyMatch(1).selectedColors(new int[]{1,2,3}).colorAndPosMatch(1).build());
        plays.add(new Play.Builder().playerName("Karthik").colorOnlyMatch(2).selectedColors(new int[]{4,0,5}).colorAndPosMatch(2).build());
        plays.add(new Play.Builder().playerName("Kishore").colorOnlyMatch(2).selectedColors(new int[]{1,4,5}).colorAndPosMatch(1).build());


        int[] imageIds = new int[]{R.id.img1,R.id.img2,R.id.img3};
        for(int i=0;i<plays.size();i++){
            View rowView = inflater.inflate(R.layout.kuzzle_row2, null);
            mainView.addView(rowView, i+1);
            fill(rowView, plays.get(i), imageIds);
        }
    }

    private void fill(View rowView, Play play, int[] imageIds) {
        int[] colors = play.getSelectedColors();


        TextView playerTextView = (TextView) rowView.findViewById(R.id.pn);
        //playerTextView.setText(play.getPlayerName());

        TextView colorOnlytv = (TextView) rowView.findViewById(R.id.colorOnly);

        //colorOnlytv.setText(play.getColorOnlyMatch());
        TextView colorNPostv = (TextView) rowView.findViewById(R.id.colorAndPos);
        //colorNPostv.setText(play.getColorAndPosMatch());

        for(int i=0;i<colors.length;i++){
            int id = colors[i];
            ImageView imageView = (ImageView) rowView.findViewById(imageIds[i]);
            CharacterDrawable drawable = new CharacterDrawable(Constants.color_char[id],Constants.colorCodes[id]);
            imageView.setImageDrawable(drawable);
        }
    }


}
