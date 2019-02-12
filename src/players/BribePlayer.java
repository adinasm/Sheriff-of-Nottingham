/**
 * @author Adina Smeu
 *
 */

package players;

import goods.Goods;
import goods.MerchantBag;

import java.util.LinkedList;
import java.util.List;

public class BribePlayer extends Player {
    private static final int BIG_BRIBE = 10;
    private static final int SMALL_BRIBE = 5;

    public BribePlayer() {
        super();
        this.strategy = Player.BRIBED;
    }

    /**
     * Adds illegal goods to his bag and gives bribe based on the number of added
     * illegal goods.
     */
    @Override
    public void merchant() {
        int bribe = 0;
        List<Goods> goodsToAdd = new LinkedList<>();

        // Checks if he can add more than two illegal goods to his bag.
        if ((coins < BIG_BRIBE) && (coins >= SMALL_BRIBE) && goodsInHand.illegalGoods()) {
            goodsToAdd = getIllegalGoods(2);
            bribe = SMALL_BRIBE;
        } else if ((coins >= BIG_BRIBE) && goodsInHand.illegalGoods()) {
            // Checks if he can add maximum two illegal goods to his bag.
            goodsToAdd = getIllegalGoods(MerchantBag.getBagSize());
            if (goodsToAdd.size() <= 2) {
                bribe = SMALL_BRIBE;
            } else {
                bribe = BIG_BRIBE;
            }
        }

        // If the player doesn't have enough coins to give bribe or if he doesn't have
        // any illegal goods, he uses the base strategy.
        if (goodsToAdd.size() == 0) {
            super.merchant();
            return;
        }

        // Creates bag and declares that it's filled with apples.
        bag = new MerchantBag(bribe, goodsToAdd, Goods.APPLE);

        // Gives bribe.
        giveCoins(bribe);
    }

    /**
     * Inspects the players situated on his left and his right.
     * @param players - list of players
     * @param goods - list of names of goods
     */
    public void sheriff(final List<Player> players, final List<String> goods) {
        for (int i = 0; i < players.size(); i++) {
            if (!(players.get(i).strategy.equals(strategy))) {
                if (players.get((i + 1) % players.size()).strategy.equals(strategy)
                        || players.get((i + players.size() - 1) % players.size()).
                        strategy.equals(strategy)) {
                    inspect(players.get(i), goods);
                } else {
                    players.get(i).addToStand(players.get(i).getBag().getGoods());
                }
            }
        }
    }

    /**
     * Adds a number of maximum size illegal goods in the bag, starting with the most profitable
     * ones.
     * @param size - number of illegal goods to add
     * @return - a list of illegal goods
     */
    private List<Goods> getIllegalGoods(final int size) {
        int found = 0;
        List<Goods> returnedGoods = new LinkedList<>();

        while ((found < size) && goodsInHand.illegalGoods()) {
            returnedGoods.add(goodsInHand.getProfitableIllegal());
        }

        return returnedGoods;
    }
}
