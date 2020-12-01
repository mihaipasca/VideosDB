package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;

public final class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public Serial(final SerialInputData serialInput) {
        super(serialInput);
        numberOfSeasons = serialInput.getNumberSeason();
        seasons = serialInput.getSeasons();
    }

    /**
     * Method that computes the average rating of the serial
     * @return Double
     */
    public Double getRating() {
        double totalRatingSum = 0.0;
        boolean rated = false;
        for (Season season : seasons) {
            if (!season.getRatings().isEmpty()) {
                rated = true;
            }
            double seasonRatingSum = 0.0;
            for (Double rating : season.getRatings()) {
                seasonRatingSum += rating / season.getRatings().size();
            }
            totalRatingSum += seasonRatingSum / numberOfSeasons;
        }
        if (rated) {
            return totalRatingSum;
        } else {
            return null;
        }
    }

    /**
     * Method that computes the duration of the show
     * @return int
     */
    public int getDuration() {
        int duration = 0;
        for (Season season : seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
