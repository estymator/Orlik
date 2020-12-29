package com.example.orlik.ui.organizeGames.formsState;

public class AddGameFormState {
    private Boolean descriptionError, durationError, dataValid;

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

    public AddGameFormState(Boolean descriptionError, Boolean durationError, Boolean dataValid) {
        this.descriptionError = descriptionError;
        this.durationError = durationError;
        this.dataValid = dataValid;
    }
}
