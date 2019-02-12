/**
 * @author Adina Smeu
 *
 */

package goods;

public class Goods {
    // Constants that describe different types of goods.
    private static final int PROFIT_APPLE = 2;
    private static final int PROFIT_BREAD = 4;
    private static final int PROFIT_CHICKEN = 4;
    private static final int PENALTY_LEGAL = 2;
    private static final int PENALTY_ILLEGAL = 4;
    private static final int KING_BONUS_APPLE = 20;
    private static final int KING_BONUS_CHEESE = 15;
    private static final int KING_BONUS_BREAD = 15;
    private static final int KING_BONUS_CHICKEN = 10;
    private static final int QUEEN_BONUS_APPLE = 10;
    private static final int QUEEN_BONUS_CHEESE = 10;
    private static final int QUEEN_BONUS_BREAD = 10;
    private static final int QUEEN_BONUS_CHICKEN = 5;
    static final int PROFIT_CHEESE = 3;
    static final int PROFIT_SILK = 9;
    static final int PROFIT_PEPPER = 8;
    static final int PROFIT_BARREL = 7;
    public static final String APPLE = "apple";
    public static final String BREAD = "bread";
    public static final String CHICKEN = "chicken";
    public static final String CHEESE = "cheese";
    public static final String SILK = "silk";
    public static final String PEPPER = "pepper";
    public static final String BARREL = "barrel";


    private int profit;
    private int penalty;
    private boolean legal;
    private String type;
    private int kingBonus;
    private int queenBonus;

    // Constructor
    public Goods() {

    }

    /**
     * Creates a good based on its type, adding not only its profit, penalty,
     *  king's and queen's bonus, but also whether it's legal or not.
     * @param type - the type of the newly created good (silk, apple etc)
     */
    public Goods(final String type) {
        this.type = type;

        switch (type) {
            case APPLE:
                profit = PROFIT_APPLE;
                penalty = PENALTY_LEGAL;
                legal = true;
                kingBonus = KING_BONUS_APPLE;
                queenBonus = QUEEN_BONUS_APPLE;
                break;

            case CHEESE:
                profit = PROFIT_CHEESE;
                penalty = PENALTY_LEGAL;
                legal = true;
                kingBonus = KING_BONUS_CHEESE;
                queenBonus = QUEEN_BONUS_CHEESE;
                break;

            case BREAD:
                profit = PROFIT_BREAD;
                penalty = PENALTY_LEGAL;
                legal = true;
                kingBonus = KING_BONUS_BREAD;
                queenBonus = QUEEN_BONUS_BREAD;
                break;

            case CHICKEN:
                profit = PROFIT_CHICKEN;
                penalty = PENALTY_LEGAL;
                legal = true;
                kingBonus = KING_BONUS_CHICKEN;
                queenBonus = QUEEN_BONUS_CHICKEN;
                break;

            case SILK:
                profit = PROFIT_SILK;
                penalty = PENALTY_ILLEGAL;
                legal = false;
                break;

            case PEPPER:
                profit = PROFIT_PEPPER;
                penalty = PENALTY_ILLEGAL;
                legal = false;
                break;

            case BARREL:
                profit = PROFIT_BARREL;
                penalty = PENALTY_ILLEGAL;
                legal = false;
                break;
            default:
                break;
        }
    }

    // Getters
    public final int getProfit() {
        return profit;
    }

    public final int getPenalty() {
        return penalty;
    }

    public final boolean getLegal() {
        return legal;
    }

    public final String getType() {
        return type;
    }

    final int getKingBonus() {
        return kingBonus;
    }

    final int getQueenBonus() {
        return queenBonus;
    }
}
