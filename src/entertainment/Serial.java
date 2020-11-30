package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial extends Show {
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    /**
     *
     * @param serialInput todo
     */
    public Serial(final SerialInputData serialInput) {
        super(serialInput);
        numberOfSeasons = serialInput.getNumberSeason();
        seasons = serialInput.getSeasons();
    }

    /**
     *
     * @return todo
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
     *
     * @return todo
     */
    public int getDuration() {
        int duration = 0;
        for (Season season : seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

    /**
     *
     * @return todo
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
