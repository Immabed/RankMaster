package com.immabed.rankmaster.rankings.compare;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;

/**
 * A Ranking Rule that takes totals of certain statistics and compares players either based on total
 * value or average value per game. Can compare based on highest value, lowest value, or proximity
 * to a given value.
 * @author Brady Coles
 */
public class SumRanking extends Ranking {

    /**
     * The rule for how to compare values.
     */
    private RankCriteria rankType;
    /**
     * If the rankType is proximity, players compared based on proximity to this value.
     */
    private double proximityValue;
    /**
     * How to create the comparable values, overall or per game.
     */
    private GamesPlayedComparison gamesPlayedComparison;


    /**
     * Ways to compare values.
     */
    public enum RankCriteria {
        MAXIMUM, MINIMUM, PROXIMITY
    }

    /**
     * How to generate comparable values.
     */
    public enum GamesPlayedComparison {
        AVERAGE_PER_GAME, OVERALL_TOTAL
    }


    /**
     * Create a SumRanking object that follow certain rules and compares based on a certain StatisticSpec.
     * @param spec The StatisticSpec of the statistics to use to compare players.
     * @param rankTable The RankTable this Ranking belongs to.
     * @param rankCriteria The method used to compare players.
     * @param proximityValue If the rankCriteria is proximity, use proximity to this value.
     * @param useGamesPlayed The rule for generating values.
     */
    public SumRanking(StatisticSpec spec, RankTable rankTable, RankCriteria rankCriteria,
                      double proximityValue, GamesPlayedComparison useGamesPlayed) {
        super(spec, rankTable);
        rankType = rankCriteria;
        this.proximityValue = proximityValue;
        gamesPlayedComparison = useGamesPlayed;
    }

    /**
     * Compare two players based on the specification in rankType and gamesPlayedComparison using
     * statistics that use the StatisticSpec spec.
     * @param player1 The first player to compare. Should exist in the rank table.
     * @param player2 The second player to compare. Should exist in the rank table.
     * @return A positive number if player1 is first, a negative number if player2 is first, and zero
     * if they are equal.
     */
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
