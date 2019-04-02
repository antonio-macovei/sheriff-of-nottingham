package strategies;

import goods.Apple;
import goods.Good;
import main.Deck;
import players.Merchant;
import players.Player;
import players.Sheriff;

import java.util.List;

public final class Greedy extends Strategy {

    @Override
    public boolean fillBag(final Merchant player, final Deck deck) {
        List<Good> cardsInHand = player.getCardsInHand();
        List<Good> cardsInBag = player.getCardsInBag();
        Good selectedGood = null;

        // Apply Base strategy each turn
        player.changeStrategy(new Base(), deck);
        // Check if current round is even
        // and add one more illegal item if he has space
        if (player.getRound() % 2 == 0) {
            if (cardsInBag.size() < Player.MAX_CARDS_IN_BAG) {
                int maxProfit = 0;
                for (Good good : cardsInHand) {
                    if (!good.isLegal() && good.getProfit() > maxProfit) {
                        maxProfit = good.getProfit();
                        selectedGood = good;
                    }
                }
                if (selectedGood != null) {
                    cardsInHand.remove(selectedGood);
                    cardsInBag.add(selectedGood);
                }

            }
        }

        // Update player's cards
        player.setCardsInHand(cardsInHand);
        player.setCardsInBag(cardsInBag);

        return false;
    }

    @Override
    public Good declareGoods(final Player player) {
        if (player.getCardsInBag().get(0).isLegal()) {
            return player.getCardsInBag().get(0);
        }
        return new Apple();
    }

    @Override
    public void inspectBags(final Deck deck, final Sheriff sheriff, final List<Player> players) {

        for (Player merchant : players) {
            // If current player is sheriff, skip
            if (merchant.isSheriff()) {
                continue;
            }
            // If the current player offers bribe, accept it
            if (merchant.givesBribe()) {
                sheriff.acceptBribe(merchant);
            } else {
                // Inspect the player if he doesn't offer bribe
                sheriff.inspectMerchantBag(deck, merchant);
            }
        }

    }

    @Override
    public String toString() {
        return "GREEDY";
    }
}
