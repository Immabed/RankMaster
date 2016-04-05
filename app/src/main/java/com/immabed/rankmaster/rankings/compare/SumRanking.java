package com.immabed.rankmaster.rankings.compare;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class SumRanking extends Ranking {

    private RankCriteria rankType;
    private double proximityValue;


    public enum RankCriteria {
        MAXIMUM, MINIMUM, PROXIMITY
    }

    public SumRanking(StatisticSpec spec, RankCriteria rankCriteria, double proximityValue) {
        super(spec);
        rankType = rankCriteria;
        this.proximityValue = proximityValue;
    }

    @Override
    protected int rankCompare(Player player1, Player player2) {
        return 0;
    }
}
