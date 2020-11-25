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
        System.out.println(seasons.toString());
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
