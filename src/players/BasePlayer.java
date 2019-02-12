/**
 * @author Adina Smeu
 *
 */

package players;

import java.util.List;

public class BasePlayer extends Player {
    public BasePlayer() {
        super();
        this.strategy = Player.BASIC;
    }

    /**
     * Inspects all the players.
     *  @param players - list of players
     *  @param goods - list of names of goods
     */
    public void sheriff(final List<Player> players, final List<String> goods) {
        for (Player player : players) {
            if (!(player.strategy.equals(strategy))) {
                inspect(player, goods);
            }
        }
    }
}
