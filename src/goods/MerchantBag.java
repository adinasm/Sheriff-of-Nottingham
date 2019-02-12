/**
 * @author Adina Smeu
 *
 */

package goods;

import java.util.List;

public class MerchantBag {
    // Contains max size of the bag, whether the player has also put bribe in
    //  the bag, the goods added and the declared type.
    private static final int BAG_SIZE = 5;
    private int bribe;
    private List<Goods> goods;
    private String declaredType;

    // Constructor
    public MerchantBag(final int bribe, final List<Goods> goods,
                       final String declaredType) {
        this.bribe = bribe;
        this.goods = goods;
        this.declaredType = declaredType;
    }

    // Getters
    public final List<Goods> getGoods() {
        return goods;
    }

    public final String getDeclaredType() {
        return declaredType;
    }

    public final int getBribe() {
        return bribe;
    }

    public final int getSize() {
        return goods.size();
    }

    public static int getBagSize() {
        return BAG_SIZE;
    }

    public final void addGood(final Goods good) {
        goods.add(good);
    }
}
