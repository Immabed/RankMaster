package com.immabed.rankmaster.rankings.compare;

import com.immabed.rankmaster.rankings.Player;
import com.immabed.rankmaster.rankings.RankTable;
import com.immabed.rankmaster.rankings.StatisticSpec;

import java.io.Serializable;

/**
 * Abstract parent class of all Ranking rules that are used to compare two players in a RankTable.
 * Players are compared based on their statistics of a particular StatisticSpec and the rules of the
 * particular Ranking class being used.
 * @author Brady Coles
 */
public abstract class Ranking implements Serializable {

    /**
     * The specification of the statistic to use to compare to players.
     */
    private StatisticSpec spec;
    /**
     * The fallback ranking system if the players are considered equal, if null then players are
     * considered equal.
     */
    private Ranking secondaryRanking;
    /**
     * The rank table that the ranking is operating on.
     */
    private RankTable rankTable;


    /**
     * Initialize the specification and the rankTable.
     * @param spec The StatisticSpec object of the statistics to use to compare players with.
     * @param rankTable The rank table that the ranking is operating on/for.
     */
    public Ranking(StatisticSpec spec, RankTable rankTable) {
        this.spec = spec;
        this.rankTable = rankTable;
    }

    /**
     * Accessor for children for rankTable.
     * @return A reference to the object in rankTable.
     */
    protected RankTable getRankTable() {
        return rankTable;
    }

    /**
     * Returns the id of the associated RankTable.
     * @return the id of the associated RankTable.
     */
    public String getRankTableId() {
        return rankTable.getId();
    }

    /**
     * Accessor for chilren for spec.
     * @return A reference to the object in spec, the StatisticSpec of the ranking.
     */
    protected StatisticSpec getSpec() {
        return spec;
    }

    /**
     * Add a new fallback ranking, will not replace a current secondary ranking, but becomes the
     * fallback for the current last fallback ranking (or for the main ranking, if it has no fallback).
     * @param ranking A ranking to use as a fallback. The rankTable of the ranking should be the
     *                same as the calling ranking.
     */
    public void addFallbackRanking(Ranking ranking)
            throws RankingDoesNotShareRankTableException {
        if (ranking != null && ranking.rankTable != rankTable) {
            throw new RankingDoesNotShareRankTableException(
                    "Cannot use fallback ranking with different rank table.");
        }
        if (secondaryRanking == null) {
            secondaryRanking = ranking;
        }
        else {
            secondaryRanking.addFallbackRanking(ranking);
        }
    }

    /**
     * Compare two players based on internal ranking rules. Compares players based on the combination
     * of statistics for all matches in a rank table.
     * @param player1 The first player to compare. Should exist in the rank table.
     * @param player2 The second player to compare. Should exist in the rank table.
     * @return If player1 is ranked higher than player2, returns a positive number. If player2 is
     * ranked higher than player1, a negative number is returned. If the players are deemed equally
     * ranked, zero is returned.
     */
    public final int compare(Player player1, Player player2) {
        int comparison = rankCompare(player1, player2);
        if (comparison == 0 && secondaryRanking != null) {
            return secondaryRanking.compare(player1, player2);
        }
        else {
            return comparison;
        }
    }

    /**
     * Internal comparison implementation. The comparison should be based on the statisticSpec of the
     * ranking object.
     * @param player1 The first player to compare. Should exist in the rank table.
     * @param player2 The second player to compare. Should exist in the rank table.
     * @return The return should be as follows: if player1 is ranked higher than player2, returns a
     * positive number. If player2 is ranked higher than player1, a negative number is returned. If
     * the players are deemed equally ranked, zero is returned.
     */
    protected abstract int rankCompare(Player player1, Player player2);
}
