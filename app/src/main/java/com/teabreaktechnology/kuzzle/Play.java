package com.teabreaktechnology.kuzzle;

import java.util.Arrays;

/**
 * Created by kishorekpendyala on 7/24/16.
 */
public class Play {

    private String playerName;
    private int colorOnlyMatch;
    private int colorAndPosMatch;
    private int selectedColors[];

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
        for (int selectedColor : selectedColors) {
            sb.append(Constants.colors[selectedColor]).append(" ");

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

    public int[] getSelectedColors() {
        return selectedColors;
    }

    public static class Builder {

        private String playerName;
        private int colorOnlyMatch;
        private int colorAndPosMatch;
        private int selectedColors[];

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
            this.selectedColors = Arrays.copyOf(selectedColors, selectedColors.length);
            return this;
        }

        public Play build() {
            return new Play(this);
        }
    }
}
