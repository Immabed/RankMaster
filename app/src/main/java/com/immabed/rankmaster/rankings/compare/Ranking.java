package com.immabed.rankmaster.rankings.compare;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public abstract class Ranking {

    private StatisticSpec spec;
    private Ranking secondaryRanking;


    public Ranking(StatisticSpec spec) {
        this.spec = spec;
    }

    public void addSecondaryRanking(Ranking ranking) {
        if (secondaryRanking == null) {
            secondaryRanking = ranking;
        }
        else {
            secondaryRanking.addSecondaryRanking(ranking);
        }
    }

    public int compare(Player player1, Player player2) {
        int comparison = rankCompare(player1, player2);
        if (comparison == 0 && secondaryRanking != null) {
            return secondaryRanking.compare(player1, player2);
        }
        else {
            return comparison;
        }
    }

    protected abstract int rankCompare(Player player1, Player player2);
}
