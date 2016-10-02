package com.teabreaktechnology.kuzzle;

import android.support.annotation.NonNull;

import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameState {


    List<String> players = new ArrayList<>();
    Integer currentPlayer = 0;
    Integer viewIndex = 0;
    List<Integer> colorCodes = new ArrayList<>();
    List<Play> plays = new ArrayList<>();
    Integer playerLimit = 2;
    List<String> colors = new ArrayList<>();
    Integer spinnerIndex = 0;

    public GameState() {

        players.add("Kishore");
        players.add("Karthik");
        playerLimit = 2;
        plays = mockPlays();
        colors = mockColors();
        colorCodes = Arrays.asList(0xff0000, 0x00ff00, 0x0000ff, 0xFFFFFF, 0xFFFF00, 0xFF00FF);


    }

    public Integer getAndIncrement() {
        return viewIndex++;
    }

    public Integer getAndDecrement() {
        return viewIndex--;
    }


    @NonNull
    public static List<String> mockColors() {
        List<String> colors = Arrays.asList("R", "G", "B", "W", "Y", "P");
        return new ArrayList<String>(colors);
    }


    public List<String> getColors() {
        return colors;
    }

    public List<Integer> getColorCodes() {
        return colorCodes;
    }


    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public List<String> getPlayers() {
        return  players;
    }

    public String getCurrentPlayerName() {
        Integer currentPlayerId = getCurrentPlayer();
        return getPlayers().get(currentPlayerId);

    }

    public Map<String, Object> getFullState() {

        Map<String, Object> state = new HashMap<>();
        state.put("playerLimit", playerLimit);
        state.put("players", players);
        state.put("currentPlayer", currentPlayer);
        state.put("plays", plays);
        state.put("colors", colors);
        state.put("colorCodes", colorCodes);
        state.put("viewIndex", viewIndex);
        state.put("spinnerIndex",spinnerIndex);

        return state;
    }

    public List<Play> getPlays() {
        return plays;
    }

    public void addPlay(Play play) {
        getPlays().add(play);
    }

    public List<String> colors() {
        return  colors;
    }

    public Integer getSpinnerIndex() {
        return spinnerIndex;
    }

    public void setSpinnerIndex(Integer spinnerIndex) {
        this.spinnerIndex = spinnerIndex;
    }

    public Integer setNextPlayer() {
        Integer nextPlayerId;
        if (getCurrentPlayer() == 0) {
            nextPlayerId = 1;
        } else {
            nextPlayerId = 0;
        }
        currentPlayer = nextPlayerId;
        return nextPlayerId;
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

    public int[] spinners() {
        return new int[]{R.id.color1, R.id.color2, R.id.color3};
    }

    public int[] imageIds() {
        return new int[]{R.id.img1, R.id.img2, R.id.img3};
    }

    public void set(Map<String, Object> state) {
//        state.put("plays", plays);
        Long playerLimitLong = (Long) state.get("playerLimit");
        Long currentPlayerLong = (Long) state.get("currentPlayer");
        this.playerLimit = playerLimitLong.intValue();
        this.currentPlayer = currentPlayerLong.intValue();
        this.players = new ArrayList<String>((ArrayList)state.get("players"));
        this.colors = new ArrayList<String>((ArrayList)state.get("colors"));
        List<Long> colorCodes = (ArrayList) state.get("colorCodes");
        this.colorCodes=new ArrayList<>();
        for(Long colorCode: colorCodes){
            this.colorCodes.add(colorCode.intValue());
        }

        Long viewIndexLong = (Long) state.get("viewIndex");
        this.viewIndex = viewIndexLong.intValue();
        List<Object> playObjs = (ArrayList)state.get("plays");
        this.plays = new ArrayList<>();
        for(Object playObj: playObjs){
            Map hashMap = (HashMap) playObj;
            String playerName = (String)hashMap.get("playerName");
            Integer colorOnlyMatch = ((Long)hashMap.get("colorOnlyMatch")).intValue();
            Integer colorAndPosMatch = ((Long)hashMap.get("colorAndPosMatch")).intValue();
            List selectedColorsObj = (ArrayList)hashMap.get("selectedColors");
            plays.add(new Play.Builder()
                    .playerName(playerName)
                    .colorOnlyMatch(colorOnlyMatch)
                    .selectedColorsFromObj(selectedColorsObj)
                    .colorAndPosMatch(colorAndPosMatch)
                    .build());


        }

    }

    public Play getLastPlay() {
        return plays.get(plays.size() - 1);
    }
}
