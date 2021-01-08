package com.example.orlik.ui.organizeGames.formsState;

public class AddGameFormState {
    private Boolean descriptionError, durationError, playersNumberError , dataValid;

    public Boolean getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(Boolean descriptionError) {
        this.descriptionError = descriptionError;
    }

    public Boolean getDurationError() {
        return durationError;
    }

    public void setDurationError(Boolean durationError) {
        this.durationError = durationError;
    }

    public Boolean getDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
    }

    public Boolean getPlayersNumberError() {
        return playersNumberError;
    }

    public void setPlayersNumberError(Boolean playersNumberError) {
        this.playersNumberError = playersNumberError;
    }

    public AddGameFormState(Boolean playersNumberError ,Boolean descriptionError, Boolean durationError, Boolean dataValid) {
        this.playersNumberError=playersNumberError;
        this.descriptionError = descriptionError;
        this.durationError = durationError;
        this.dataValid = dataValid;
    }
}
