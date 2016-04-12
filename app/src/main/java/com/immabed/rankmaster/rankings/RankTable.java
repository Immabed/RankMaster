package com.immabed.rankmaster.rankings;

import com.immabed.rankmaster.rankings.compare.Ranking;
import com.immabed.rankmaster.rankings.compare.RankingDoesNotShareRankTableException;
import com.immabed.rankmaster.rankings.compare.SumRanking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A RankTable with the ability to hold Matches and sort Players based on a Ranking rule.
 * @author Brady Coles
 */
public class RankTable implements Serializable{
    /**
     * Display name of this RankTable
     */
    private String name;
    /**
     * List of matches belonging to this RankTable
     */
    private ArrayList<Match> matches = new ArrayList<>();
    /**
     * List of StatisticSpec objects used by this RankTable
     */
    private ArrayList<StatisticSpec> statisticSpecs;
    /**
     * List of players, does not necessarily reflect current players unless populatePlayersArray or
     * sortPlayers is called.
     */
    private ArrayList<Player> players;
    /**
     * Ranking system to use to compare players.
     */
    private Ranking mainRanking;
    /**
     * Unique identifier
     */
    private String id;


    /**
     * Create a new RankTable with a name and StatisticSpec's
     * @param name The display name of the RankTable.
     * @param statisticSpecs The StatisticSpec's to use in the RankTable.
     */
    public RankTable(String name, StatisticSpec ... statisticSpecs) {
        this.name = name;
        this.statisticSpecs = new ArrayList<>(Arrays.asList(statisticSpecs));

        // Seed ID
        Random seed = new Random();
        id = this.getClass().toString();
        id = id.length() > 20 ? id.substring(id.length() - 20) : id;
        id += Long.toString(seed.nextLong(), 36);
    }

    /**
     * Returns the 'unique' id of this RankTable.
     * @return the 'unique' id of this RankTable.
     */
    public String getId() {
        return id;
    }

    /**
     * Return the name of the RankTable.
     * @return The name of the RankTable.
     */
    public String toString() {
        return name;
    }

    /**
     * Adds a Ranking object as a rule. If a mainRanking already exists, becomes a fallback ranking.
     * @param ranking A ranking object whose RankTable is the calling object.
     */
    public void addRanking(Ranking ranking) throws RankingDoesNotShareRankTableException {
        //TODO: check if ranktable is this
        if (mainRanking == null) {
            if (ranking.getRankTableId().equals(id))
                mainRanking = ranking;
            else
                throw new RankingDoesNotShareRankTableException(
                        "Tried to add main ranking without proper RankTable.");
        }
        else {
            mainRanking.addFallbackRanking(ranking);
        }
    }

    /**
     * Add a match to the rank table.
     * @param match A match with the statistics being from this rank table. If the statistics are not
     *              from this rank table, then they will not be displayed or used for ranking.
     */
    public void addMatch(Match match) {
        if (match != null)
            matches.add(match);
    }

    /**
     * Returns the number of games a player has played.
     * @param player A player who has played a game in this rank table. If a player is not in the
     *               rank table, returns zero.
     * @return The number of games the player argument has played in the calling rank table.
     */
    public int getGamesPlayed(Player player) {
        int gamesPlayed = 0;
        for (Match match : matches) {
            if (match.hasPlayer(player)) {
                gamesPlayed++;
            }
        }
        return gamesPlayed;
    }

    /**
     * Gets the players in the rank table.
     * @return The list of players in the rank table, sorted according to the main ranking.
     */
    public Player[] getPlayers() {
        sortPlayers();
        return players.toArray(new Player[players.size()]);
    }

    /**
     * Gets an array of the statisticSpecs used by the rank table.
     * @return An array of the statisticSpecs used by the rank table.
     */
    public StatisticSpec[] getStatisticSpecs () {
        return statisticSpecs.toArray(new StatisticSpec[statisticSpecs.size()]);
    }

    /**
     * Gets all statistics of a certain spec for a specific player from all matches in the rank table.
     * @param player The player to get statistics for.
     * @param statisticSpec The spec of the statistics to get.
     * @return An array of all statistics of the id of argument statisticSpec that belong to the
     * player, taken from all matches in the rank table.
     */
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
    private Player[] mergeSort(Player[] startingArray) {
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
