/**
 * @author Adina Smeu
 *
 */

package players;

import goods.Goods;
import goods.MerchantBag;

import java.util.LinkedList;
import java.util.List;

public class WizardPlayer extends Player {
    private int merchantRound;

    // Constructor
    public WizardPlayer() {
        super();
        this.strategy = Player.WIZARD;
        merchantRound = 0;
    }

    /**
     * Counts the number of rounds. If the round is odd, the player uses the
     * base strategy, otherwise he uses the updated royal strategy.
     */
    @Override
    public void merchant() {
        merchantRound++;

        if ((merchantRound % 2) == 1) {
            super.merchant();
        } else {
            updatedRoyal();
        }
    }

    // The player adds in the bag the good that has the biggest score, computed
    // using the getRoyalGood method.
    private void updatedRoyal() {
        List<Goods> inHand = goodsInHand.getGoodsInHand();
        List<Goods> toAdd = new LinkedList<>();

        String maxType = getRoyalGood();

        for (int i = 0; i < inHand.size(); i++) {
            if (inHand.get(i).getType().equals(maxType)) {
                toAdd.add(inHand.get(i));
                inHand.remove(i);
                i--;
            }
        }

        bag = new MerchantBag(0, toAdd, maxType);
    }

    /**
     *
     * @return - the type of good that has the maximum score (number of occurences in
     *          the player's hand + number of occurences on the player's stand + profit)
     */
    private String getRoyalGood() {
        List<Goods> inHand = goodsInHand.getGoodsInHand();
        String maxType = null;
        int maxScore = 0;

        for (Goods good : inHand) {
            int score = goodsInHand.getHandCount(good) + stand.getStandCount(good)
                    + good.getProfit();

            if (score > maxScore) {
                maxScore = score;
                maxType = good.getType();
            }
        }

        return maxType;
    }

    /**
     * Inspects a player if he gives bribe or if he has more than 2 goods in the bag.
     * @param players - list of players
     * @param goods - list of names of goods
     */
    public void sheriff(final List<Player> players, final List<String> goods) {
        for (Player player : players) {
            if (!(player.strategy.equals(strategy))) {
                if ((player.getBag().getBribe() != 0) || (player.getBag().getSize() > 2)) {
                    inspect(player, goods);
                } else {
                    player.addToStand(player.getBag().getGoods());
                }
            }
        }
    }
}
