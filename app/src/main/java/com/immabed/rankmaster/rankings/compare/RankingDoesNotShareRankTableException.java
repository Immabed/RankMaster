package com.immabed.rankmaster.rankings.compare;

/**
 * Exception describes a situation where a Ranking object has a different RankTable member than
 * expected.
 * @author Brady Coles
 */
public class RankingDoesNotShareRankTableException extends Exception {
    public RankingDoesNotShareRankTableException() {
        super("Ranking does not have correct rank table.");
    }
    public RankingDoesNotShareRankTableException(String message) {
        super(message);
    }
}
