package com.immabed.rankmaster.rankings.compare;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;

import java.io.Serializable;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class SumRanking extends Ranking {

    private RankCriteria rankType;
    private double proximityValue;
    private GamesPlayedComparison gamesPlayedComparison;


    public enum RankCriteria {
        MAXIMUM, MINIMUM, PROXIMITY
    }

    public enum GamesPlayedComparison {
        AVERAGE_PER_GAME, OVERALL_TOTAL
    }


    public SumRanking(StatisticSpec spec, RankTable rankTable, RankCriteria rankCriteria,
                      double proximityValue, GamesPlayedComparison useGamesPlayed) {
        super(spec, rankTable);
        rankType = rankCriteria;
        this.proximityValue = proximityValue;
        gamesPlayedComparison = useGamesPlayed;
    }

    @Override
    protected int rankCompare(Player player1, Player player2) {
        double player1sum = getSpec().getValue(getRankTable().getStatisticsBySpec(player1, getSpec()));
        double player2sum = getSpec().getValue(getRankTable().getStatisticsBySpec(player2, getSpec()));

        // If based on average rather than total, divide by number of games.
        if (gamesPlayedComparison == GamesPlayedComparison.AVERAGE_PER_GAME) {
            player1sum /= getRankTable().getGamesPlayed(player1);
            player2sum /= getRankTable().getGamesPlayed(player2);
        }

        switch (rankType) {
            case MAXIMUM:
                if (player1sum > player2sum)
                    return 1;
                else if (player1sum < player2sum)
                    return -1;
                else
                    return 0;
            case MINIMUM:
                if (player1sum < player2sum)
                    return 1;
                else if (player1sum > player2sum)
                    return -1;
                else
                    return 0;
            case PROXIMITY:
                if (Math.abs(player1sum - proximityValue) < Math.abs(player2sum - proximityValue))
                    return 1;
                else if (Math.abs(player1sum - proximityValue) < Math.abs(player2sum - proximityValue))
                    return -1;
                else
                    return 0;
            default:
                System.out.printf("Unknown error in SumRanking.rankCompare()/n" +
                        "player1 total %f. player2 total %f", player1sum, player2sum);
                return 0;
        }
    }
}
