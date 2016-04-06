package com.immabed.rankmaster.rankings.compare;

/**
 * Created by Immabed on 2016-04-05.
 */
public class RankingDoesNotShareRankTableException extends Exception {
    public RankingDoesNotShareRankTableException() {
        super("Ranking does not have correct rank table.");
    }
    public RankingDoesNotShareRankTableException(String message) {
        super(message);
    }
}
