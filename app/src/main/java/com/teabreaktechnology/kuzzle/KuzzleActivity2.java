package com.teabreaktechnology.kuzzle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KuzzleActivity2 extends AppCompatActivity {

    int[] answer = new int[]{1, 2, 3};
    GameState gameState;
    int myPlayerId = 0;

    DatabaseReference game1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        gameState = new GameState(myPlayerId);
        game1 = FirebaseDatabase.getInstance().getReference()
                .child("game1");
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final TableLayout mainView = (TableLayout) findViewById(R.id.mainView);

        game1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                Map<String, Object> value1 = (Map<String, Object>) value;
                gameState.set(value1);
                System.out.println(value);
                setNextPlay(inflater, mainView, gameState);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setContentView(R.layout.activity_kuzzle2);

        addHeader(inflater, mainView, gameState);
        fillPlays(inflater, mainView, gameState);
        createColoredSpinner(inflater, mainView, gameState);
        setOnClickListner(inflater, mainView, gameState);
        game1.setValue(gameState.getFullState());
    }


    private void createColoredSpinner(LayoutInflater inflater, TableLayout mainView, GameState gameState) {
        View rowView = inflater.inflate(R.layout.kuzzle_spinner_3, null);
        Integer index = gameState.getAndIncrement();
        gameState.setSpinnerIndex(index);
        mainView.addView(rowView, gameState.getSpinnerIndex());
        ArrayList<ItemData> list = new ArrayList<>();
        List colors = gameState.getColors();
        List colorCodes = gameState.getColorCodes();
        for (int i = 0; i < colors.size(); i++) {
            list.add(new ItemData((String) colors.get(i), Integer.valueOf((Integer) colorCodes.get(i))));
        }
        Play previousPlay = gameState.getLastPlay();
        for (int i = 0; i < colors.size() / 2; i++) {
            Spinner[] spinners = new Spinner[3];
            int[] spinnerIds = gameState.spinners();
            final Spinner colorsSpinner = (Spinner) rowView.findViewById(spinnerIds[i]);
            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list);
            colorsSpinner.setAdapter(adapter);
            colorsSpinner.setSelection(previousPlay.getSelectedColors().get(i), true);
            spinners[i] = colorsSpinner;
        }
    }



    private void fillPlays(LayoutInflater inflater, TableLayout mainView, GameState gameState) {
        for (Play play : gameState.getPlays()) {
            View rowView = inflater.inflate(R.layout.kuzzle_row2, null);
            mainView.addView(rowView, gameState.getAndIncrement());
            fill(rowView, play, gameState.imageIds(), gameState.getColorCodes(), gameState.getColors());
        }
    }

    private void addHeader(LayoutInflater inflater, TableLayout mainView, GameState gameState) {
        mainView.addView(inflater.inflate(R.layout.kuzzle_header, null), gameState.getAndIncrement());
    }

    private void fill(View rowView, Play play, int[] imageIds, List<Integer> colorCodes, List<String> colorCharacters) {
        List<Integer> colors = play.getSelectedColors();
        TextView playerTextView = (TextView) rowView.findViewById(R.id.pn);
        playerTextView.setText(play.getPlayerName());

        TextView colorOnlytv = (TextView) rowView.findViewById(R.id.colorOnly);
        colorOnlytv.setText("" + play.getColorOnlyMatch());
        TextView colorNPostv = (TextView) rowView.findViewById(R.id.colorAndPos);
        colorNPostv.setText("" + play.getColorAndPosMatch());

        for (int i = 0; i < colors.size(); i++) {
            int id = colors.get(i);
            ImageView imageView = (ImageView) rowView.findViewById(imageIds[i]);
            CharacterDrawable drawable = new CharacterDrawable(colorCharacters.get(id).charAt(0), colorCodes.get(id));
            imageView.setImageDrawable(drawable);
        }
    }


    private void setOnClickListner(final LayoutInflater inflater, final TableLayout mainView, final GameState gameState) {
        Button button = (Button) findViewById(R.id.next);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int[] selectedColors = readColors(gameState);
                Play play = scoreThePlay(gameState.getCurrentPlayerName(), selectedColors, answer);
                gameState.addPlay(play);
                Integer spinnerIndex = gameState.getSpinnerIndex();
                mainView.removeViewAt(spinnerIndex);
                gameState.setNextPlayer();

                game1.setValue(gameState.getFullState());

                setNextPlay(inflater, mainView, gameState);

            }
        });

    }

    private void setNextPlay(LayoutInflater inflater, TableLayout mainView, GameState gameState) {
        Play previousPlay = gameState.getLastPlay();
        Integer spinnerIndex = gameState.getSpinnerIndex();
        View rowView = inflater.inflate(R.layout.kuzzle_row2, null);
        mainView.addView(rowView, spinnerIndex);
        fill(rowView, previousPlay, gameState.imageIds(), gameState.getColorCodes(), gameState.getColors());

        if(gameState.getCurrentPlayer()==myPlayerId) {
            createColoredSpinner(inflater, mainView, gameState);
            setPlayerDetailsText(gameState);
        }


    }

    private void setPlayerDetailsText(GameState gameState) {
        String playerName = gameState.getCurrentPlayerName();
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


    private int[] readColors(GameState gameState) {

        int[] selectedColors = new int[gameState.getColors().size() / 2];
        for (int i = 0; i < gameState.getColors().size() / 2; i++) {
            Spinner colorsSpinner = (Spinner) findViewById(gameState.spinners()[i]);
            int selectedItemId = (int) colorsSpinner.getSelectedItemId();
            selectedColors[i] = selectedItemId;
        }
        return selectedColors;

    }
}
