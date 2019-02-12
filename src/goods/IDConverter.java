/**
 * @author Adina Smeu
 *
 */

package goods;

public final class IDConverter {
    // Constructor
    private IDConverter() { }

    // Constant IDs
    private static final int ID_APPLE = 0;
    private static final int ID_CHEESE = 1;
    private static final int ID_BREAD = 2;
    private static final int ID_CHICKEN = 3;
    private static final int ID_SILK = 10;
    private static final int ID_PEPPER = 11;
    private static final int ID_BARREL = 12;

    /**
     * Converts an ID to the name of the good.
     * @param id - id of a good
     * @return - name of a good
     */
    public static String convertID(final int id) {
        if (id == ID_APPLE) {
            return Goods.APPLE;
        }

        if (id == ID_CHEESE) {
            return Goods.CHEESE;
        }

        if (id == ID_BREAD) {
            return Goods.BREAD;
        }

        if (id == ID_CHICKEN) {
            return Goods.CHICKEN;
        }

        if (id == ID_SILK) {
            return Goods.SILK;
        }

        if (id == ID_PEPPER) {
            return Goods.PEPPER;
        }

        if (id == ID_BARREL) {
            return Goods.BARREL;
        }

        return null;
    }
}
