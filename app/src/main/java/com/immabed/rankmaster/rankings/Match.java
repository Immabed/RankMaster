package com.immabed.rankmaster.rankings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Brady Coles on 2016-03-26.
 */
public class Match implements Comparable<Match> {

    private ArrayList<PlayerAndStatistics> players;
    private Date date;

    public Match() {
        date = new Date();
        players = new ArrayList<>();
    }

    /**
     * Add a player and statistics to the match. Player cannot already have been added to the match.
     * @param player A player that has not been already added to the match.
     * @param statistics Any number of statistics to add to the match, should all have different id's.
     * @throws PlayerAlreadyExistsException If the player has already been added to the match, a
     * PlayerAlreadyExistsException is thrown.
     */
    public void addPlayer(Player player, Statistic ... statistics)
            throws PlayerAlreadyExistsException {
        for (PlayerAndStatistics matchPlayer : players) {
            if (matchPlayer.player.getId().equals(player.getId())) {
                throw new PlayerAlreadyExistsException(
                        String.format("Player %s is already entered into this match", player)
                );
            }

        }
        players.add (new PlayerAndStatistics(player, statistics));
    }

    /**
     * Remove a player from the match.
     * @param player The player to be removed, must be a player in the match.
     * @throws PlayerNotFoundException If the player argument is not a player in the match, a
     * PlayerNotFoundException will be thrown.
     */
    public void removePlayer(Player player)
            throws PlayerNotFoundException {
        for (int i = 0; i < players.size(); i++) {
            if (player.getId().equals(players.get(i).player.getId())) {
                players.remove(i);
            }

        }
        throw new PlayerNotFoundException(String.format("Player %s not found.", player));
    }

    /**
     * Remove a statistic from a player in the match.
     * @param player The player to remove the statistic from, should already be a player in the match.
     * @param statisticId The id of the statistic to remove, the same as the id of the StatisticSpec
     *                    of the statistic to remove.
     * @throws PlayerNotFoundException If the player is not a player in the match, a PlayerNotFoundException
     * is thrown.
     * @throws StatisticDoesNotExistException If a statistic with the id statisticId is not found for
     * the player in the match, a StatisticDoesNotExistException is thrown.
     */
    public void removeStatistic(Player player, String statisticId)
            throws PlayerNotFoundException, StatisticDoesNotExistException {
        for (PlayerAndStatistics playerAndStatistics : players) {
            if (player.getId().equals(playerAndStatistics.player.getId())) {
                playerAndStatistics.removeStatistic(statisticId);
            }
        }
        throw new PlayerNotFoundException();
    }

    /**
     * Remove a statistic from a player in the match.
     * @param player The player to remove the statistic from, should already be a player in the match.
     * @param spec The StatisticSpec of the statistic.
     * @throws PlayerNotFoundException If the player is not a player in the match, a PlayerNotFoundException
     * is thrown.
     * @throws StatisticDoesNotExistException If a statistic with the same id as the StatisticSpec
     * argument is not found for the player in the match, a StatisticDoesNotExistException is thrown.
     */
    public void removeStatistic(Player player, StatisticSpec spec)
            throws PlayerNotFoundException, StatisticDoesNotExistException {
        removeStatistic(player, spec.getId());
    }

    /**
     * Remove a statistic from a player in the match.
     * @param player The player to remove the statistic from, should already be a player in the match.
     * @param statistic A statistic with the same id as the statistic to be removed.
     * @throws PlayerNotFoundException If the player is not a player in the match, a PlayerNotFoundException
     * is thrown.
     * @throws StatisticDoesNotExistException If a statistic with the same id as the statistic argument
     * is not found for the player in the match, a StatisticDoesNotExistException is thrown.
     */
    public void removeStatistic(Player player, Statistic statistic)
            throws PlayerNotFoundException, StatisticDoesNotExistException {
        removeStatistic(player, statistic.getStatisticSpecId());
    }

    /**
     * Changes a statistic for a player for the match.
     * @param player A player who has already been added to the match.
     * @param statistic A statistic to replace an existing statistic with.
     * @throws PlayerNotFoundException If no player with the id of the player argument is found, a
     * PlayerNotFoundException is thrown.
     * @throws StatisticDoesNotExistException If not statistic with the same id as the statistic
     * argument is found, a StatisticDoesNotExistException is thrown.
     */
    public void changeStatistic(Player player, Statistic statistic)
            throws PlayerNotFoundException, StatisticDoesNotExistException {
        for (PlayerAndStatistics playerAndStatistics : players) {
            if (player.getId().equals(playerAndStatistics.player.getId())) {
                playerAndStatistics.replaceStatistic(statistic);
            }
        }
        throw new PlayerNotFoundException();
    }

    /**
     * Add statistics to an existing player in the match. Player should already be added to match,
     * and statistics added should not already be added to the match. If a statistic with the same id
     * as a statistic already attributed to the player for the match is added, it will be ignored.
     * @param player The player to add statistics to.
     * @param statistics The statistics to add to the player. If a statistic with a matching id to
     *                   a statistic already given to the player for the match is provided, it will
     *                   be ignored.
     * @throws PlayerNotFoundException If the player is not already in the match, a PlayerNotFoundException
     * is thrown.
     */
    public void addStatistics(Player player, Statistic ... statistics)
            throws PlayerNotFoundException {
        for (PlayerAndStatistics playerAndStatistics : players) {
            if (playerAndStatistics.player.getId().equals(player.getId())) {
                for (Statistic statistic: statistics) {
                    try {
                        playerAndStatistics.addStatistic(statistic);
                    }
                    catch (StatisticAlreadyExistsException e) {
                        // TODO : Deal with exception
                    }

                }
                return;
            }

        }
        throw new PlayerNotFoundException(String.format("Player %s not found in match.", player));
    }

    /**
     * Change the date that the match took place at.
     * @param year The year (eg. 2011)
     * @param month THe month between 0 (January) and 11 (December)
     * @param day The day of the month (1-31, with max determined by month).
     */
    public void setDate(int year, int month, int day) {
        Calendar cal = null;
        cal.set(year, month, day);
        date.setTime(cal.getTimeInMillis());
    }

    /**
     * Matches are compared based on date to determine the order in which the occurred.
     * @param another A match object to compare.
     * @return the value 0 if the argument Match's Date is equal to this Match's Date; a value less
     * than 0 if this Match's Date is before the Match argument's Date; and a value greater than 0
     * if this Match's Date is after the Match argument's Date.
     */
    @Override
    public int compareTo(Match another) {
        return (date.compareTo(another.date));

    }


    /**
     *
     */
    private class PlayerAndStatistics {
        /**
         * The player to whom the statistics belong.
         */
        private Player player;
        /**
         * The statistics for the match for the player.
         */
        private ArrayList<Statistic> statistics;

        /**
         * Adds a player and accompanying statistics to a match.
         * @param player The player to add to the match.
         * @param statistics Any number of statistics objects to add to the player for the match.
         */
        PlayerAndStatistics(Player player, Statistic ... statistics) {
            this.player = player;
            for (Statistic statistic: statistics) {
                try {
                    addStatistic(statistic);
                }
                catch (StatisticAlreadyExistsException e) {
                    //TODO :Catch exception
                }
            }
        }

        /**
         * Adds a statistic to the player for the match if a statistic with the same id does not
         * already exist.
         * @param statistic A statistic of a new spec (new id) from those already entered.
         * @throws StatisticAlreadyExistsException If a statistic with the same id already exists,
         * StatisticAlreadyExistsException is thrown.
         */
        public void addStatistic (Statistic statistic)
                throws StatisticAlreadyExistsException {
            for (Statistic enteredStatistic : statistics) {
                if (statistic.getStatisticSpecId().equals(enteredStatistic.getStatisticSpecId())) {
                    throw new StatisticAlreadyExistsException(); // Add proper Message
                }
            }
            statistics.add(statistic);
        }

        /**
         * Removes a statistic from the statistics for the player for the match.
         * @param statisticId The id of the statistic to remove, same as the id for the spec for the
         *                    statistic to remove.
         * @throws StatisticDoesNotExistException If a statistic with the id statisticId is not found
         * a StatisticDoesNotExistException will be thrown.
         */
        public void removeStatistic (String statisticId)
                throws StatisticDoesNotExistException {
            for (int i = 0; i < statistics.size(); i++) {
                if (statistics.get(i).getStatisticSpecId().equals(statisticId)) {
                    statistics.remove(i);
                    return;
                }
            }
            throw new StatisticDoesNotExistException("Statistic not found");
        }

        /**
         * Replaces a statistic with a new statistic by replacing a statistic with the same id.
         * @param statistic A statistic with the same id as a statistic already entered.
         * @throws StatisticDoesNotExistException If no statistic with the same id already exists,
         * StatisticDoesNotExistException is thrown.
         */
        public void replaceStatistic (Statistic statistic)
                throws StatisticDoesNotExistException {
            for (int i = 0; i < statistics.size(); i++) {
                if (statistics.get(i).getStatisticSpecId().equals(statistic.getStatisticSpecId())) {
                    statistics.set(i, statistic);
                    return;
                }
            }
            throw new StatisticDoesNotExistException("Cannot replace statistic that does not exist.");
        }
    }

}

