package strategies;

import goods.Good;
import main.Deck;
import players.Merchant;
import players.Player;
import players.Sheriff;

import java.util.List;

public class Strategy {

    /*
     * Method which fills a merchant's bag with items according to the chosen strategy
     */
    public boolean fillBag(final Merchant player, final Deck deck) {
        return false;
    }

    /*
     * Method which chooses which good to declare according to the chosen strategy
     */
    public Good declareGoods(final Player player) {
        return null;
    }

    /*
     * Method which inspects merchant's bags according to the chosen strategy
     */
    public void inspectBags(final Deck deck, final Sheriff sheriff, final List<Player> players) {

    }
}
