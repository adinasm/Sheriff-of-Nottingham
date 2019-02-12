/**
 * @author Adina Smeu
 *
 */

package goods;

import players.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MerchantStand {
    private static final String KING = "king";
    private static final String QUEEN = "queen";
    private List<Goods> onStand = new LinkedList<>();
    private Map<Goods, Integer> countGoods = new HashMap<>();

    // Getter
    private Map<Goods, Integer> getCountGoods() {
        return countGoods;
    }

    /**
     * Adds goods on the merchant stand.
     * @param goods - the list of goods that has to be added
     */
    public final void add(final List<Goods> goods) {
        onStand = Stream.concat(onStand.stream(), goods.stream()).
                collect(Collectors.toList());
    }

    /**
     *
     * @return - the profit obtained from the goods placed on the stand
     */
    public final int getScore() {
        int score = 0;

        for (Goods good : onStand) {
            score += good.getProfit();
        }

        return score;
    }

    /**
     *
     * @param searchedGood -
     * @return the number of occurences of a certain good on the stand
     */
    public final int getStandCount(final Goods searchedGood) {
        int count = 0;

        for (Goods good : onStand) {
            if (searchedGood.getType().equals(good.getType())) {
                count++;
            }
        }

        return count;
    }

    /**
     *
     * @param goods - the array of goods that have to be counted
     * @return - the number of occurences of the goods from the stand
     */
    private Map<Goods, Integer> getOccurences(final Goods[] goods) {
        for (Goods good : goods) {
            countGoods.put(good, 0);

            for (Goods goodOnStand : onStand) {
                if (goodOnStand.getType().equals(good.getType())) {
                    countGoods.put(good, countGoods.getOrDefault(good,
                            0) + 1);
                }
            }
        }

        return countGoods;
    }

    /**
     * If there are illegal goods on the stand, the bonus obtained from them
     * is also placed on the stand.
     */
    private void addIllegalBonus() {
        LinkedList<Goods> toAdd = new LinkedList<>();

        for (Goods good : onStand) {
            if (!good.getLegal()) {
                if (good.getProfit() == Goods.PROFIT_SILK) {
                    for (int j = 0; j < Goods.PROFIT_CHEESE; j++) {
                        toAdd.addLast(new Goods(Goods.CHEESE));
                    }
                } else if (good.getProfit() == Goods.PROFIT_PEPPER) {
                    for (int j = 0; j < 2; j++) {
                        toAdd.addLast(new Goods(Goods.CHICKEN));
                    }
                } else if (good.getProfit() == Goods.PROFIT_BARREL) {
                    for (int j = 0; j < 2; j++) {
                        toAdd.addLast(new Goods(Goods.BREAD));
                    }
                }
            }
        }

        if (toAdd.size() != 0) {
            add(toAdd);
        }
    }

    /**
     * Adds king's/queen's bonus.
     * @param players - the list of players
     * @param bonusGoods - the number of occurences of goods eligible for
     *                   bonus
     * @param type - the type of bonus (king/queen)
     */
    private static void addBonus(final List<Player> players,
                                final Map<Goods, Integer> bonusGoods,
                                 final String type) {
        for (Player player : players) {
            Map<Goods, Integer> playerGoods = player.getStand().
                    getCountGoods();

            for (Goods key : bonusGoods.keySet()) {
                if (bonusGoods.get(key).equals(playerGoods.get(key))) {
                    if (type.equals(QUEEN)) {
                        player.setBonus(key.getQueenBonus());
                    } else {
                        player.setBonus(key.getKingBonus());
                    }
                }
            }
        }
    }

    /**
     * Discovers the players that have the second highest number of goods of a
     * given kind and adds queen's bonus.
     * @param players - list of players
     * @param queenGoods - number of occurences of goods eligible for queen
     *                   bonus
     * @param kingGoods - number of occurences of goods eligible for king
     *      *                   bonus
     */
    private static void addQueenBonus(final List<Player> players,
                                  final Map<Goods, Integer> queenGoods,
                                  final Map<Goods, Integer> kingGoods) {
        for (Player player : players) {
            Map<Goods, Integer> playerGoods = player.getStand().
                    getCountGoods();

            for (Goods key : queenGoods.keySet()) {
                if ((!kingGoods.get(key).equals(playerGoods.get(key)))
                        && (queenGoods.get(key) < playerGoods.get(key))) {
                    queenGoods.put(key, playerGoods.get(key));
                }
            }
        }

        addBonus(players, queenGoods, QUEEN);
    }

    /**
     * Discovers the players that have the most goods of a given kind and adds
     * king's bonus.
     * @param players - list of players
     * @param kingGoods - number of occurences of goods eligible for king
     *      *      *                   bonus
     * @param goods - array of goods that have to be counted
     */
    private static void addKingBonus(final List<Player> players,
                                 final Map<Goods, Integer> kingGoods,
                                     final Goods[] goods) {
        for (Player player : players) {
            player.getStand().addIllegalBonus();
            Map<Goods, Integer> playerGoods = player.getStand().
                    getOccurences(goods);

            for (Goods key : kingGoods.keySet()) {
                if (kingGoods.get(key) < playerGoods.get(key)) {
                    kingGoods.put(key, playerGoods.get(key));
                }
            }
        }

        addBonus(players, kingGoods, KING);
    }

    /**
     * Discovers the kings and queens.
     * @param players - list of players
     */
    public static void getBonus(final List<Player> players) {
        Goods[] goods = {new Goods(Goods.APPLE), new Goods(Goods.CHEESE),
                new Goods(Goods.BREAD), new Goods(Goods.CHICKEN)};
        Map<Goods, Integer> kingGoods = new HashMap<>();
        Map<Goods, Integer> queenGoods = new HashMap<>();

        for (Goods good : goods) {
            kingGoods.put(good, 0);
        }

        for (Goods good : goods) {
            queenGoods.put(good, -1);
        }

        // Adds king's and queen's bonus.
        addKingBonus(players, kingGoods, goods);
        addQueenBonus(players, queenGoods, kingGoods);
    }
}
