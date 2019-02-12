/**
 * @author Adina Smeu
 *
 */

package players;

import goods.Goods;
import goods.MerchantBag;

import java.util.List;

public class GreedyPlayer extends Player {
    private int round;

    // Constructor
    public GreedyPlayer() {
        super();
        this.strategy = Player.GREEDY;
        round = 0;
    }

    /**
     * Counts the rounds. Calls the method from the parent class, threfore adding
     * the most common good with the highest profit. If the round is even and the player
     * has illegal goods in his hand, he also adds the most profitable illegal good.
     */
    @Override
    public void merchant() {
        round++;
        super.merchant();

        if ((round % 2) == 0) {
            if (bag.getGoods().size() != MerchantBag.getBagSize()) {
                if (goodsInHand.illegalGoods()) {
                    Goods illegal = goodsInHand.getProfitableIllegal();
                    bag.addGood(illegal);
                }
            }
        }
    }

    /**
     * Inspects players that don't give bribe.
     * If there's bribe in the merchant bag, the player is allowed to place all the goods
     * on his stand.
     * @param players - list of players
     * @param goods - list of names of goods
     */
    public void sheriff(final List<Player> players, final List<String> goods) {
        for (Player player : players) {
            if (!(player.strategy.equals(strategy))) {
                if (player.getBag().getBribe() != 0) {
                    receiveCoins(player.getBag().getBribe());
                    player.addToStand(player.getBag().getGoods());
                } else {
                    inspect(player, goods);
                }
            }
        }
    }
}
