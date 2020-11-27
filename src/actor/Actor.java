package actor;

import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;
    public Actor(ActorInputData actorInput) {
        name = actorInput.getName();
        careerDescription = actorInput.getCareerDescription();
        filmography = actorInput.getFilmography();
        awards = actorInput.getAwards();
    }

    public int getAwardsNumber() {
        int count = 0;
        for(Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
            count += award.getValue();
        }
        return count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }
}
