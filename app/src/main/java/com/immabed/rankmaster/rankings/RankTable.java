package com.immabed.rankmaster.rankings;

import com.immabed.rankmaster.rankings.compare.Ranking;
import com.immabed.rankmaster.rankings.compare.SumRanking;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Brady Coles on 2016-04-02.
 */
public class RankTable {
    private String name;
    private ArrayList<Match> matches;
    private ArrayList<StatisticSpec> statisticSpecs;
    private ArrayList<Player> players;
    private Ranking mainRanking;


    public RankTable(String name/*, Ranking ranking, StatisticSpec ... statisticSpecs*/) {
        this.name = name;
        //mainRanking = ranking;
        //this.statisticSpecs = new ArrayList<>(Arrays.asList(statisticSpecs));
        ValueStatisticSpec newSpec = new ValueStatisticSpec("Points", true, false, true, 0, 0, 0);
        statisticSpecs = new ArrayList<>();
        statisticSpecs.add(newSpec);
        mainRanking = new SumRanking(newSpec, this, SumRanking.RankCriteria.MAXIMUM, 0);
        Player player1 = new Player("Brady");
        Player player2 = new Player("Steve");
        Player player3 = new Player("Gord");
        Match match1 = new Match();
        Match match2 = new Match();
        Match match3 = new Match();
        try {
            match1.addPlayer(player1, new ValueStatistic(newSpec, 13));
            match1.addPlayer(player2, new ValueStatistic(newSpec, 9));
            match2.addPlayer(player3, new ValueStatistic(newSpec, 41));
            match2.addPlayer(player1, new ValueStatistic(newSpec, 21));
            match3.addPlayer(player2, new ValueStatistic(newSpec, 18));
        }
        catch (PlayerAlreadyExistsException e) {
            // Do nothing
        }
        matches = new ArrayList<Match>();
        matches.add(match1);
        matches.add(match2);
        matches.add(match3);
        sortPlayers();
    }

    public Player[] getPlayers() {
        return players.toArray(new Player[players.size()]);
    }

    public StatisticSpec[] getStatisticSpecs () {
        return statisticSpecs.toArray(new StatisticSpec[statisticSpecs.size()]);
    }

    public Statistic[] getStatisticsBySpec(Player player, StatisticSpec statisticSpec) {
        ArrayList<Statistic> statistics = new ArrayList<>();
        for (Match match : matches) {
            for(Statistic statistic: match.getPlayerStatisticsOfType(player, statisticSpec)) {
                statistics.add(statistic);
            }
        }
        return statistics.toArray(new Statistic[statistics.size()]);
    }

    /**
     * Get the string value for a statistic for a player.
     * @param player A player in the rankTable.
     * @param statisticSpec A statisticSpec in the rankTable.
     * @return The string value of the statistics for the player.
     */
    public String getStatisticStringByPlayerBySpec(Player player, StatisticSpec statisticSpec) {
        return statisticSpec.getStatisticString(getStatisticsBySpec(player, statisticSpec));
    }

    /**
     * Populate the players array based on the players in the matches. Voids the sort.
     */
    private void populatePlayersArray() {
        players = new ArrayList<>();
        for (Match match : matches) {
            for (Player player : match.getPlayers()) {
                if (!players.contains(player)) {
                    players.add(player);
                }
            }
        }
    }

    /**
     * Sort the players array based on the ranking system.
     */
    private void sortPlayers() {
        populatePlayersArray();
        Player[] players = this.players.toArray(new Player[this.players.size()]);
        players = mergeSort(players);
        this.players = new ArrayList<>(Arrays.asList(players));
    }

    /**
     * Performs a merge sort on an array and returns a copy of the array sorted
     * in increasing order.
     * @param startingArray An int array to be sorted.
     * @return A copy of startingArray sorted in increasing order.
     */
    public Player[] mergeSort(Player[] startingArray) {
        // Check if array has any elements.
        if (startingArray == null || startingArray.length == 0) {
            System.out.println("Error has occurred, tried to sort empty array.");
            return new Player[0];
        }
        // Base case, if array has one element, it is sorted, so return it.
        else if (startingArray.length == 1) {
            return startingArray;
        }
        // Recursive case, splits the array into two, and calls itself on both.
        else {
            // Create and populate first half array
            Player[] array1 = new Player[startingArray.length / 2];
            for (int i = 0; i < array1.length; i++) {
                array1[i] = startingArray[i];
            }
            // Create and populate second half array
            Player[] array2 = new Player[startingArray.length - startingArray.length / 2];
            for (int i = 0; i < array2.length; i++) {
                array2[i] = startingArray[i + array1.length];
            }
            // Recursion, sort both half arrays.
            array1 = mergeSort(array1);
            array2 = mergeSort(array2);
            // Return the merged array of both half arrays.
            return mergeSortMerge(array1, array2);
        }
    }

    /**
     * Merges two sorted arrays into a new sorted array, for use by {@link #mergeSort}.
     * Precondition: Both arrays are sorted in increasing order.
     * @param array1 A sorted array of integers (in increasing order).
     * @param array2 A sorted array of integers (in increasing order).
     * @return A new sorted array of the elements in array1 and array2 (in
     * increasing order).
     */
    private Player[] mergeSortMerge(Player[] array1, Player[] array2) {
        Player[] returnArray = new Player[array1.length + array2.length];
        int array1index = 0;
        int array2index = 0;
        int returnArrayIndex = 0;
        while (array1index < array1.length && array2index < array2.length) {
            if (mainRanking.compare(array1[array1index], array2[array2index]) > 0) {
                returnArray[returnArrayIndex] = array1[array1index];
                array1index ++;
            }
            else {
                returnArray[returnArrayIndex] = array2[array2index];
                array2index ++;
            }
            returnArrayIndex ++;
        }
        if (array1index < array1.length) {
            for (int i = array1index; i < array1.length; i++) {
                returnArray[returnArrayIndex] = array1[i];
                returnArrayIndex++;
            }
        }
        if (array2index < array2.length) {
            for (int i = array2index; i < array2.length; i++) {
                returnArray[returnArrayIndex] = array2[i];
                returnArrayIndex++;
            }
        }
        return returnArray;
    }



}
