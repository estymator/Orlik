package com.example.orlik.data.model;

public class Pitch {
    private Integer pitchId;
    private String type, location, adress;
    private Integer rating;
    private boolean valid;

    public Integer getPitchId() {
        return pitchId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Pitch(){};

    public Pitch(String type, String location, String adress, Integer rating,boolean valid) {
        this.type = type;
        this.location = location;
        this.adress = adress;
        this.rating = rating;
        this.valid=valid;
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "pitchId=" + pitchId +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", adress='" + adress + '\'' +
                ", rating=" + rating +
                ", valid=" + valid +
                '}';
    }
}
