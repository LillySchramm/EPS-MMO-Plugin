package de.epsdev.plugins.MMO.scoreboards;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Empty {
    private int index = 1;
    private Objective objective;
    public Empty(Objective objective){
        this.objective = objective;
    }

    public Score next(){
        String s = "";
        for (int i = 0; i < index; i++) {
            s += " ";
        }

        Score score = objective.getScore(s);

        index++;
        return score;
    }
}
