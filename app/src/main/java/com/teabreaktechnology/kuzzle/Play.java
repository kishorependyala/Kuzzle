package com.teabreaktechnology.kuzzle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekpendyala on 7/24/16.
 */
public class Play implements Serializable {

    private String playerName;
    private int colorOnlyMatch;
    private int colorAndPosMatch;
    private List<Integer> selectedColors;

    public Play(Builder builder) {
        this.playerName = builder.playerName;
        this.colorOnlyMatch = builder.colorOnlyMatch;
        this.colorAndPosMatch = builder.colorAndPosMatch;
        this.selectedColors = builder.selectedColors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(playerName).append(" ");
        sb.append(colorOnlyMatch).append(" ");
        for (Integer selectedColor : selectedColors) {
            sb.append(GameState.mockColors().get(selectedColor)).append(" ");

        }
        sb.append(colorAndPosMatch).append(" ");
        sb.append("\n");
        return sb.toString();

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getColorOnlyMatch() {
        return colorOnlyMatch;
    }

    public int getColorAndPosMatch() {
        return colorAndPosMatch;
    }

    public List<Integer> getSelectedColors() {
        return selectedColors;
    }

    public static class Builder {

        private String playerName;
        private int colorOnlyMatch;
        private int colorAndPosMatch;
        private List<Integer> selectedColors = new ArrayList<>();

        public Builder playerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Builder colorOnlyMatch(int colorOnlyMatch) {
            this.colorOnlyMatch = colorOnlyMatch;
            return this;
        }

        public Builder colorAndPosMatch(int colorAndPosMatch) {
            this.colorAndPosMatch = colorAndPosMatch;
            return this;
        }

        public Builder selectedColors(int[] selectedColors) {
            for(int color: selectedColors){
                this.selectedColors.add(color);
            }
            return this;
        }

        public Play build() {
            return new Play(this);
        }

        public Builder selectedColorsFromObj(List selectedColorsObj) {
            for(Object selectedColorObj : selectedColorsObj){
                Long selectedColor = (Long) selectedColorObj;
                this.selectedColors.add(selectedColor.intValue());
            }
            return this;
        }
    }
}
