/**
 * @author Adina Smeu
 *
 */

package main;

import goods.IDConverter;
import goods.MerchantStand;
import players.BasePlayer;
import players.BribePlayer;
import players.GreedyPlayer;
import players.Player;
import players.WizardPlayer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

final class Game {
    private static final Game INSTANCE = new Game();

    // Constructor
    private Game() {

    }

    /**
     * The class can be instantiated only once.
     * @return - the instance of the class
     */
    public static Game getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a list with the names of the goods from a list that contains their ids.
     * @param gameInput - player strategies and goods ids
     * @param goods - list of names of goods
     */
    void createDeck(final GameInput gameInput, final List<String> goods) {
        for (int i = 0; i < gameInput.getAssetIds().size(); i++) {
            goods.add(IDConverter.convertID(gameInput.getAssetIds().get(i)));
        }
    }


    /**
     * Creates a list of players from one that contained only their strategies.
     * @param gameInput - player strategies and goods ids
     * @param players - list of players
     * @param goods - list of names of goods
     */
    void createPlayers(final GameInput gameInput, final List<Player> players,
                              final List<String> goods) {
        for (int i = 0; i < gameInput.getPlayerNames().size(); i++) {
            if (gameInput.getPlayerNames().get(i).equals(Player.BASIC)) {
                players.add(new BasePlayer());
            } else if (gameInput.getPlayerNames().get(i).equals(Player.BRIBED)) {
                players.add(new BribePlayer());
            } else if (gameInput.getPlayerNames().get(i).equals(Player.GREEDY)) {
                players.add(new GreedyPlayer());
            } else if (gameInput.getPlayerNames().get(i).equals(Player.WIZARD)) {
                players.add(new WizardPlayer());
            }

            // The newly created player extracts 6 cards(goods) from the deck.
            players.get(i).createHand(goods);
        }
    }

    /**
     *The game is played until every player gets to be sheriff 2 times.
     * @param players - list of players
     * @param goods - list of names of goods
     */
    void play(final List<Player> players, final List<String> goods) {
        for (int i = 1; i <= 2 * players.size(); i++) {
            round(i, players, goods);
        }

        MerchantStand.getBonus(players);
    }

    /**
     *
     * @param round - the current round
     * @param players - list of players
     * @param goods - list of names of goods
     */
    private void round(final int round, final List<Player> players,
                              final List<String> goods) {
        // Every player that isn't the current sheriff gets to be a merchant.
        for (Player player : players) {
            if (!players.get((round - 1) % players.size()).getStrategy().
                    equals(player.getStrategy())) {
                player.merchant();
            }
        }

        // The current sheriff inspects the other players.
        players.get((round - 1) % players.size()).sheriff(players, goods);

        // The players recomplete their hand.
        for (Player player : players) {
            player.recompleteHand(goods);
        }
    }

    /**
     *Creates an unsorted leaderboard with the final scores.
     * @param players - list of players
     * @param leaderboard -  the leaderboard (players and their scores)
     */
    void getScore(final List<Player> players, final Map<String, Integer> leaderboard) {
        for (Player player : players) {
            leaderboard.put(player.getStrategy().toUpperCase(),
                    player.calculateScore());
        }
    }



    /**
     * Sorts and returns the leaderboard.
     * @param players - list of players
     * @param leaderboard -  the leaderboard (players and their scores)
     * @return - the sorted leaerboard
     */
    Map<String, Integer> sortLeaderboard(final List<Player> players,
                                                final Map<String, Integer> leaderboard) {
        // Sorts the leadeboard in decreasing order of value.
        Map<String, Integer> sortedLeaderboardByValue = leaderboard.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        Map<String, Integer> sortedLeaderboard = new LinkedHashMap<>();

        Object[] keys = sortedLeaderboardByValue.keySet().toArray();

        // If two players have the same score, they are sorted alphabetically.
        for (int i = 0; i < players.size() - 1; i++) {
            if (sortedLeaderboardByValue.get(keys[i]).equals(
                    sortedLeaderboardByValue.get(keys[i + 1]))) {
                if (((String) keys[i]).compareTo(((String) keys[i + 1])) > 0) {
                    sortedLeaderboard.put((String) keys[i + 1],
                            sortedLeaderboardByValue.get(keys[i + 1]));
                }
            }

            sortedLeaderboard.put((String) keys[i], sortedLeaderboardByValue.get(keys[i]));
        }

        if (sortedLeaderboardByValue.size() != sortedLeaderboard.size()) {
            sortedLeaderboard.put((String) keys[players.size() - 1],
                    sortedLeaderboardByValue.get(keys[players.size() - 1]));
        }

        return sortedLeaderboard;
    }

    /**
     * Prints the leaderboard.
     * @param sortedLeaderboard - the sorted leaderboard (players and their scores)
     */
    void printLeaderboard(final Map<String, Integer> sortedLeaderboard) {
        for (String key : sortedLeaderboard.keySet()) {
            System.out.println(key + ": " + sortedLeaderboard.get(key));
        }
    }
}
