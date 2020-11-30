package actor;

import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    /**
     *
     * @param actorInput TODO
     */
    public Actor(final ActorInputData actorInput) {
        name = actorInput.getName();
        careerDescription = actorInput.getCareerDescription();
        filmography = actorInput.getFilmography();
        awards = actorInput.getAwards();
    }

    /**
     *
     * @return int with the total number of awards
     */
    public int getAwardsNumber() {
        int count = 0;
        for (Map.Entry<ActorsAwards, Integer> award : awards.entrySet()) {
            count += award.getValue();
        }
        return count;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

}
