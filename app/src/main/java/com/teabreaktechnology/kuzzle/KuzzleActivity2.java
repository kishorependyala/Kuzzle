package com.teabreaktechnology.kuzzle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KuzzleActivity2 extends AppCompatActivity {

    String[] players = new String[]{"Kishore", "Karthik"};
    int[] scores = new int[]{0, 0};
    int currentPlayerId = 0;
    int[] answer = new int[]{1, 2, 3};
    int viewIndex = 0;
    int spinnerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuzzle2);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableLayout mainView = (TableLayout) findViewById(R.id.mainView);
        addHeader(inflater, mainView);
        List<Play> plays = mockPlays();
        fillPlays(inflater, mainView, plays);

        //createSpinner(inflater, mainView);
        createColoredSpinner(inflater, mainView, new int[]{1,1,1});
        setOnClickListner(inflater, mainView, plays);
    }

    private void createSpinner(LayoutInflater inflater, TableLayout mainView) {
        View rowView = inflater.inflate(R.layout.kuzzle_spinner_3, null);
        mainView.addView(rowView, 4);
        for (int i = 0; i < Constants.colors.length / 2; i++) {
            Spinner[] spinners = new Spinner[3];
            int[] spinnerIds = spinners();
            final Spinner colorsSpinner = (Spinner) rowView.findViewById(spinnerIds[i]);
            ArrayAdapter<String> colorsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constants.colors);
            colorsSpinner.setAdapter(colorsAdaptor);
            colorsSpinner.setSelection(2);
            spinners[i] = colorsSpinner;
        }
    }

    private int[] spinners() {
        return new int[]{R.id.color1, R.id.color2, R.id.color3};
    }

    private void createColoredSpinner(LayoutInflater inflater, TableLayout mainView, int[] prevPlay) {
        View rowView = inflater.inflate(R.layout.kuzzle_spinner_3, null);
        spinnerIndex = viewIndex++;
        mainView.addView(rowView, spinnerIndex);
        ArrayList<ItemData> list = new ArrayList<>();
        for (int i = 0; i < Constants.colors.length; i++) {
            list.add(new ItemData(Constants.colors[i], Constants.colorCodes[i]));
        }
        for (int i = 0; i < Constants.colors.length / 2; i++) {
            Spinner[] spinners = new Spinner[3];
            int[] spinnerIds = spinners();
            final Spinner colorsSpinner = (Spinner) rowView.findViewById(spinnerIds[i]);
            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list);
            colorsSpinner.setAdapter(adapter);
            colorsSpinner.setSelection(prevPlay[i],true);
            spinners[i] = colorsSpinner;
        }
    }

    private void fillPlays(LayoutInflater inflater, TableLayout mainView, List<Play> plays) {
        for (Play play : plays)
            fill(inflater, mainView, play);
    }

    private void fill(LayoutInflater inflater, TableLayout mainView, Play play) {
        int[] imageIds = new int[]{R.id.img1, R.id.img2, R.id.img3};
        View rowView = inflater.inflate(R.layout.kuzzle_row2, null);
        mainView.addView(rowView, viewIndex++);
        fill(rowView, play, imageIds);
    }

    private void addHeader(LayoutInflater inflater, TableLayout mainView) {
        mainView.addView(inflater.inflate(R.layout.kuzzle_header, null), viewIndex++);
    }

    @NonNull
    private List<Play> mockPlays() {
        List<Play> plays = new ArrayList<>();
        //plays.add(new Play.Builder().playerName("Player Name").colorOnlyMatch(1).selectedColors(new int[]{1,2,3}).colorAndPosMatch(1).build());
        plays.add(new Play.Builder().playerName("Kishore").colorOnlyMatch(0).selectedColors(new int[]{1, 2, 3}).colorAndPosMatch(0).build());
        //plays.add(new Play.Builder().playerName("Karthik").colorOnlyMatch(2).selectedColors(new int[]{4, 0, 5}).colorAndPosMatch(2).build());
        //plays.add(new Play.Builder().playerName("Kishore").colorOnlyMatch(2).selectedColors(new int[]{1, 4, 5}).colorAndPosMatch(1).build());
        return plays;
    }

    private void fill(View rowView, Play play, int[] imageIds) {
        int[] colors = play.getSelectedColors();


        TextView playerTextView = (TextView) rowView.findViewById(R.id.pn);
        playerTextView.setText(play.getPlayerName());

        TextView colorOnlytv = (TextView) rowView.findViewById(R.id.colorOnly);

        colorOnlytv.setText("" + play.getColorOnlyMatch());
        TextView colorNPostv = (TextView) rowView.findViewById(R.id.colorAndPos);
        colorNPostv.setText("" + play.getColorAndPosMatch());

        for (int i = 0; i < colors.length; i++) {
            int id = colors[i];
            ImageView imageView = (ImageView) rowView.findViewById(imageIds[i]);
            CharacterDrawable drawable = new CharacterDrawable(Constants.color_char[id], Constants.colorCodes[id]);
            imageView.setImageDrawable(drawable);
        }
    }


    private void setOnClickListner(final LayoutInflater inflater, final TableLayout mainView, final List<Play> priorPlays) {
        Button button = (Button) findViewById(R.id.next);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int[] selectedColors = readColors();
                Play play = scoreThePlay(players[currentPlayerId], selectedColors, answer);
                priorPlays.add(play);
                mainView.removeViewAt(spinnerIndex);
                viewIndex--;
                fill(inflater, mainView, play);
                createColoredSpinner(inflater, mainView,play.getSelectedColors());
                setNextPlayer();
                setPlayerDetailsText(currentPlayerId);

            }
        });

    }

    private void setPlayerDetailsText(int playerID) {
        String playerName = players[playerID];
        TextView textView = (TextView) findViewById(R.id.currentPlayer);
        textView.setText("" + playerName);
    }

    public Play scoreThePlay(String playerName, int[] colors, int[] answer) {
        int colorOnlyCount = colorOnlyMatch(colors, answer);
        int colorAndPosMatchCount = colorAndPosMatch(colors, answer);
        Play play = new Play.Builder().playerName(playerName).colorOnlyMatch(colorOnlyCount)
                .colorAndPosMatch(colorAndPosMatchCount)
                .selectedColors(colors)
                .build();
        return play;
    }

    private void setNextPlayer() {
        if (currentPlayerId == 0) {
            currentPlayerId = 1;
        } else {
            currentPlayerId = 0;
        }
    }

    private int colorAndPosMatch(int[] colors, int[] answer) {
        int colorAndPosMatchCount = 0;

        for (int i = 0; i < answer.length; i++) {
            if (colors[i] == answer[i]) {
                colorAndPosMatchCount++;
            }
        }
        return colorAndPosMatchCount;
    }

    private int colorOnlyMatch(int[] colors, int[] answer) {
        int colorOnlyCount = 0;
        for (int i = 0; i < colors.length; i++) {
            if (colorExists(answer, colors[i]) && colors[i] != answer[i]) {
                colorOnlyCount++;
            }
        }
        return colorOnlyCount;
    }

    private boolean colorExists(int[] answer, int color) {
        for (int colorId : answer) {
            if (colorId == color) {
                return true;
            }
        }
        return false;
    }


    private int[] readColors() {
        int[] selectedColors = new int[Constants.colors.length / 2];
        for (int i = 0; i < Constants.colors.length / 2; i++) {
            Spinner colorsSpinner = (Spinner) findViewById(spinners()[i]);
            int selectedItemId = (int) colorsSpinner.getSelectedItemId();
            selectedColors[i] = selectedItemId;
        }
        return selectedColors;

    }
}
