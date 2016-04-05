package com.immabed.rankmaster.rankings;

import com.immabed.rankmaster.rankings.compare.Ranking;

import java.util.ArrayList;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class RankTable {
    private String name;
    private ArrayList<Match> matches;
    private ArrayList<StatisticSpec> statisticSpecs;
    private Ranking mainRanking;


    public Statistic[] getStatisticsBySpec(Player player, StatisticSpec statisticSpec) {
        ArrayList<Statistic> statistics = new ArrayList<>();
        for (Match match : matches) {
            for(Statistic statistic: match.getPlayerStatisticsOfType(player, statisticSpec)) {
                statistics.add(statistic);
            }
        }
        return (Statistic[])statistics.toArray();
    }


}
