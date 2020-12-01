package actor;

import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    /**
     * actor name
     */
    private final String name;
    /**
     * description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private final ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final ActorInputData actorInput) {
        name = actorInput.getName();
        careerDescription = actorInput.getCareerDescription();
        filmography = actorInput.getFilmography();
        awards = actorInput.getAwards();
    }

    /**
     * Method that computes the number of awards an actor has
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
