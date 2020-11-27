package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial extends Show{
    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    public Serial(SerialInputData serialInput) {
        super(serialInput);
        numberOfSeasons = serialInput.getNumberSeason();
        seasons = serialInput.getSeasons();
    }

    public Double getRating() {
        double totalRatingSum = 0.0;
        boolean rated = false;
        for (Season season : seasons) {
            if (!season.getRatings().isEmpty()){
                rated = true;
            }
            double seasonRatingSum = 0.0;
            for (Double rating : season.getRatings()) {
                seasonRatingSum += rating / season.getRatings().size();
            }
            totalRatingSum += seasonRatingSum / numberOfSeasons;
        }
        if (rated)
            return totalRatingSum;
        else
            return null;
    }

    public int getDuration() {
        int duration = 0;
        for (Season season : seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
}
