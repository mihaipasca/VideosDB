package actor;

import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Map;

public class Actor {
    String name;
    String careerDescription;
    ArrayList<String> filmography;
    Map<ActorsAwards, Integer> awards;
    public Actor(ActorInputData actorInput) {
        name = actorInput.getName();
        careerDescription = actorInput.getCareerDescription();
        filmography = actorInput.getFilmography();
        awards = actorInput.getAwards();
    }
}
