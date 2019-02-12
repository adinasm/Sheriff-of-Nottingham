/**
 * @author Adina Smeu
 *
 */

package players;

import goods.Goods;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

final class InHand {
    private List<Goods> goodsInHand;

    // Constructor
    InHand() {
        goodsInHand = new LinkedList<>();
    }

    // Getters
    int getCount() {
        return goodsInHand.size();
    }

    List<Goods> getGoodsInHand() {
        return goodsInHand;
    }

    /**
     *
     * @param good - good that has to be added
     */
    void addGood(final Goods good) {
        goodsInHand.add(good);
    }

    /**
     *
     * @param searchedGood - good that has to be searched
     * @return - number of occurences of a certain good
     */
    int getHandCount(final Goods searchedGood) {
        int count = 0;

        for (Goods good : goodsInHand) {
            if (searchedGood.getType().equals(good.getType())) {
                count++;
            }
        }

        return count;
    }

    /**
     * Adds the legal good with the most occurences and the highest profit. If there
     * are no legal goods, then adds the illegal good with the highest profit.
     * @return - list of goods that have to be added
     */
    List<Goods> getGoodsToAdd() {
        int legalGoods = 0;
        Map<String, Integer> countGoods = new HashMap<>();

        // Computes the number of occurences of every good.
        for (Goods good : goodsInHand) {
            if (good.getLegal()) {
                countGoods.put(good.getType(), countGoods.getOrDefault(good.getType(), 0) + 1);
                legalGoods++;
            }
        }

        // If there are no legal goods, the player adds the illegal good with the highest profit.
        if (legalGoods == 0) {
            List<Goods> illegalGood = new LinkedList<>();
            illegalGood.add(getProfitableIllegal());
            return illegalGood;
        }

        List<Goods> mostCommonGoods = getMostCommonGoods(countGoods);

        return getMostProfitableGoods(mostCommonGoods, countGoods);
    }

    /**
     *
     * @return - the most profitable illegal good
     */
    Goods getProfitableIllegal() {
        int maxProfit = 0;
        Goods illegal = new Goods();
        for (Goods good : goodsInHand) {
            if ((!good.getLegal()) && (good.getProfit() > maxProfit)) {
                maxProfit = good.getProfit();
                illegal = good;
            }
        }

        for (int i = 0; i < goodsInHand.size(); i++) {
            if ((goodsInHand.get(i).getProfit() == maxProfit)) {
                goodsInHand.remove(i);
                i = goodsInHand.size();
            }
        }

        return illegal;
    }

    /**
     *
     * @param mostCommonGoods - goods that have the most occurences and their
     *                        number of occurences
     * @return - the most profitable legal good
     */
    private Goods getProfitableLegal(final List<Goods> mostCommonGoods) {
        int maxProfit = 0;
        Goods legal = new Goods();

        for (Goods good : mostCommonGoods) {
            if (good.getLegal()) {
                if (good.getProfit() > maxProfit) {
                    maxProfit = good.getProfit();
                    legal = good;
                }
            }
        }
        return legal;
    }

    /**
     * Checks if the player has illegal goods.
     * @return - true if the good is legal, false otherwise
     */
    boolean illegalGoods() {
        for (Goods good : goodsInHand) {
            if (!good.getLegal()) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param countGoods - goods and their number of occurences
     * @return - the most common goods
     */
    private List<Goods> getMostCommonGoods(final Map<String, Integer> countGoods) {
        int maxOccurences = 0;
        List<Goods> mostCommonGoods = new LinkedList<>();

        // Computes the number of occurences of every good.
        for (String key : countGoods.keySet()) {
            if (countGoods.get(key) > maxOccurences) {
                maxOccurences = countGoods.get(key);
            }
        }

        // Stores the most common goods.
        for (String key : countGoods.keySet()) {
            if (countGoods.get(key) == maxOccurences) {
                mostCommonGoods.add(new Goods(key));
            }
        }

        return mostCommonGoods;
    }

    /**
     *
     * @param mostCommonGoods - goods that have the most occurences and their
     *      *                        number of occurences
     * @param countGoods - goods and their number of occurences
     * @return - the most common goods with the highest profit
     */
    private List<Goods> getMostProfitableGoods(final List<Goods> mostCommonGoods,
                                               final Map<String, Integer> countGoods) {
        List<Goods> profitableGoods = new LinkedList<>();

        Goods profitableGood = getProfitableLegal(mostCommonGoods);

        for (int i = 0; i < goodsInHand.size(); i++) {
            if (goodsInHand.get(i).getType().equals(profitableGood.getType())) {
                goodsInHand.remove(i);
                i--;
            }
        }

        for (int i = 0; i < countGoods.get(profitableGood.getType()); i++) {
            profitableGoods.add(profitableGood);
        }

        return profitableGoods;
    }
}
