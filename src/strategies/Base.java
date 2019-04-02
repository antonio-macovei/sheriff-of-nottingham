package strategies;

import goods.Apple;
import goods.Good;
import main.Deck;
import players.Merchant;
import players.Player;
import players.Sheriff;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Base extends Strategy {
    private static final int MAX_ITEMS = 5;

    @Override
    public boolean fillBag(final Merchant player, final Deck deck) {
        List<Good> cardsInHand = player.getCardsInHand();
        List<Good> cardsInBag = player.getCardsInBag();

        // Find the card with most occurrences
        Map<Good, Integer> occurrences = new LinkedHashMap<>();
        int maxOccurrences = 0;
        int maxProfit = 0;
        Good putInBag = null;

        // Count occurrences for each type of good in hand
        for (Good good : cardsInHand) {
            if (occurrences.containsKey(good)) {
                occurrences.replace(good, occurrences.get(good) + 1);
            } else {
                occurrences.put(good, 1);
            }
        }

        // Get the one with most occurrences from the legal ones
        for (Good good : cardsInHand) {
            if (good.isLegal() && occurrences.get(good) > maxOccurrences) {
                maxOccurrences = occurrences.get(good);
                putInBag = good;
            }
        }

        // Get the one with max profit from the legal ones,
        // if there are more than 1
        for (Good good : cardsInHand) {
            if (good.isLegal()
                    && occurrences.get(good) == maxOccurrences
                    && good.getProfit() > maxProfit) {
                maxProfit = good.getProfit();
                putInBag = good;
            }
        }

        // If there are no legal goods, get the one
        // with max profit from the illegal ones
        if (maxOccurrences == 0) {
            for (Good good : cardsInHand) {
                if (!good.isLegal() && good.getProfit() > maxProfit) {
                    maxProfit = good.getProfit();
                    putInBag = good;
                }
            }
        }

        // Put it in the bag and remove from the hand max 5 items
        for (int i = 0; i < maxOccurrences && i <= MAX_ITEMS; i++) {
            cardsInBag.add(putInBag);
            cardsInHand.remove(putInBag);
        }

        // Update player's cards
        player.setCardsInHand(cardsInHand);
        player.setCardsInBag(cardsInBag);

        // Return true because he didn't changed his strategy
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

            // Inspect every merchant
            sheriff.inspectMerchantBag(deck, merchant);
        }

    }

    @Override
    public String toString() {
        return "BASIC";
    }
}
