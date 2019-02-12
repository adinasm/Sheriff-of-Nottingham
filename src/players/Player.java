/**
 * @author Adina Smeu
 *
 */

package players;

import goods.Goods;
import goods.MerchantStand;
import goods.MerchantBag;

import java.util.List;

public abstract class Player {
    private static final int HAND_CAPACITY = 6;
    private static final int INITIAL_COINS = 50;
    public static final String BASIC = "basic";
    public static final String BRIBED = "bribed";
    public static final String GREEDY = "greedy";
    public static final String WIZARD = "wizard";
    protected final MerchantStand stand;
    protected final InHand goodsInHand;
    protected MerchantBag bag;
    protected String strategy;
    protected int coins;
    private int bonus;



    // Constructor
    public Player() {
        coins = INITIAL_COINS;
        bonus = 0;
        goodsInHand = new InHand();
        stand = new MerchantStand();
    }

    // Getters
    public final String getStrategy() {
        return strategy;
    }

    public final MerchantStand getStand() {
        return stand;
    }

    final MerchantBag getBag()  {
        return bag;
    }

    public final void setBonus(final int value) {
        bonus += value;
    }

    /**
     * The player extracts 6 cards from the deck.
     * @param goods - list of names of goods
     */
    public final void createHand(final List<String> goods) {
        for (int i = 0; i < HAND_CAPACITY; i++) {
            goodsInHand.addGood(new Goods(goods.get(0)));
            goods.remove(0);
        }
    }

    /**
     * The player extracts cards from the deck until he has 6.
     * @param goods - list of names of goods
     */
    public final void recompleteHand(final List<String> goods) {
        while (goodsInHand.getCount() < HAND_CAPACITY) {
            goodsInHand.addGood(new Goods(goods.get(0)));
            goods.remove(0);
        }
    }

    /**
     *
     * @return - the final score
     */
    public final int calculateScore() {
        return stand.getScore() + coins + bonus;
    }

    /**
     * Increments the number of coins.
     * @param newCoins - amount of coins that have to be received
     */
    final void receiveCoins(final int newCoins) {
        coins += newCoins;
    }

    /**
     * Decrements the number of coins.
     * @param newCoins - amount of coins that have to be given
     */
    final void giveCoins(final int newCoins) {
        coins -= newCoins;
    }

    /**
     * Adds goods to the merchant stand.
     * @param goods - list of goods that have to be added
     */
    final void addToStand(final List<Goods> goods) {
        stand.add(goods);
    }

    /**
     * Adds the legal good with the most occurences and the highest profit.
     * If there are no legal goods, then adds the illegal good with the highest profit.
     */
    public void merchant() {
        List<Goods> toAddGoods = goodsInHand.getGoodsToAdd();
        if (toAddGoods.get(0).getLegal()) {
            bag = new MerchantBag(0, toAddGoods, toAddGoods.get(0).getType());
        } else {
            bag = new MerchantBag(0, toAddGoods, Goods.APPLE);
        }
    }

    /**
     * The method is implemented in the derived classes.
     * @param players - list of players
     * @param goods - list of names of goods
     */
    public abstract void sheriff(List<Player> players, List<String> goods);

    /**
     * The sheriff inspects the player's bag.
     * @param player - player that has to be inspected
     * @param goods - list of names of goods
     */
    final void inspect(final Player player, final List<String> goods) {
        MerchantBag playerBag = player.getBag();
        List<Goods> playerGoods = playerBag.getGoods();
        int goodsCount = playerGoods.size();
        String declaredType = playerBag.getDeclaredType();

        // If there are no undeclared goods, the sheriff is forced to pay the player
        // a penalty that is directly proportional to the number of goods in the bag.
        if (countUndeclaredGoods(playerGoods, player, goods, declaredType) == 0) {
            giveCoins(goodsCount * playerGoods.get(0).getPenalty());
            player.receiveCoins(goodsCount * playerGoods.get(0).getPenalty());
        }

        // The inspected player places on the stand the goods from the bag.
        player.addToStand(playerGoods);

        // The bribe is returned to the inspected player.
        if (playerBag.getBribe() != 0) {
            player.receiveCoins(playerBag.getBribe());
        }
    }

    /**
     * Counts the undeclared goods and returns them to the deck. The inspected player
     *     // pays penalty to the sheriff.
     * @param playerGoods - the goods from the player's bag
     * @param player - player that has to be inspected
     * @param goods - list of names of goods
     * @param declaredType - the type declared in the bag
     * @return - number of undeclared goods
     */
    private int countUndeclaredGoods(final List<Goods> playerGoods, final Player player,
                                   final List<String> goods, final String declaredType) {
        int undeclaredCount = 0;

        for (int i = 0; i < playerGoods.size(); i++) {
            if (!(playerGoods.get(i).getType().equals(declaredType))) {
                player.giveCoins(playerGoods.get(i).getPenalty());
                receiveCoins(playerGoods.get(i).getPenalty());
                goods.add(playerGoods.get(i).getType());
                playerGoods.remove(i);
                i--;

                undeclaredCount++;
            }
        }

        return undeclaredCount;
    }
}
