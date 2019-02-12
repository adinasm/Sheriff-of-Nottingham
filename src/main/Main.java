/**
 * @author Adina Smeu
 *
 */

package main;

import players.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public final class Main {

    private Main() { };

    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        // TODO Implement the game logic.
        if (gameInput.isValidInput()) {
            List<Player> players = new LinkedList<>();
            List<String> goods = new LinkedList<>();
            Map<String, Integer> leaderboard = new HashMap<>();
            Game game = Game.getInstance();

            game.createDeck(gameInput, goods);

            game.createPlayers(gameInput, players, goods);

            game.play(players, goods);

            game.getScore(players, leaderboard);

            Map<String, Integer> sortedLeaderboard = game.sortLeaderboard(players, leaderboard);

            game.printLeaderboard(sortedLeaderboard);
        }
    }
}
