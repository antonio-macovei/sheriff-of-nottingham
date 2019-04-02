package strategies;

import goods.Apple;
import goods.Good;
import main.Deck;
import main.Game;
import players.Merchant;
import players.Player;
import players.Sheriff;

import java.util.List;

public final class Bribe extends Strategy {
    private static final int SMALL_BRIBE = 5;
    private static final int BIG_BRIBE = 10;
    private static final int MAX_ITEMS_SMALL = 2;
    private static final int MAX_ITEMS_BIG = 5;

    @Override
    public boolean fillBag(final Merchant player, final Deck deck) {
        List<Good> cardsInHand = player.getCardsInHand();
        List<Good> cardsInBag = player.getCardsInBag();
        int countIllegal = 0;

        for (Good good : cardsInHand) {
            if (!good.isLegal()) {
                countIllegal++;
            }
        }

        // If there are no illegal items in his bag
        // or he doesn't have enough money to pay the bribe,
        // play Base Strategy
        boolean noIllegalItems = false;
        boolean notEnoughCoinsBig = false;
        boolean notEnoughCoinsSmall = false;

        if (countIllegal == 0) {
            noIllegalItems = true;
        } else if (countIllegal > 2 && player.getCoins() < BIG_BRIBE) {
            notEnoughCoinsBig = (player.getCoins() < SMALL_BRIBE);
        } else if (countIllegal <= 2 && player.getCoins() < SMALL_BRIBE) {
            notEnoughCoinsSmall = true;
        }

        if (noIllegalItems || notEnoughCoinsBig || notEnoughCoinsSmall) {
            player.changeStrategy(new Base(), deck);
            // Return true because we changed the strategy
            return true;
        }

        int maxAllowedGoods = MAX_ITEMS_SMALL;
        player.setBribe(SMALL_BRIBE);
        if (countIllegal > MAX_ITEMS_SMALL && player.getCoins() >= BIG_BRIBE) {
            maxAllowedGoods = MAX_ITEMS_BIG;
            player.setBribe(BIG_BRIBE);
        }

        int maxProfit = 0;
        Good selectedGood = null;

        // Select first 'maxAllowedGoods' items from the cards in hand
        while (maxAllowedGoods > 0 && countIllegal > 0) {
            // Find the card with biggest profit
            maxProfit = 0;
            for (Good good : cardsInHand) {
                if (!good.isLegal() && good.getProfit() > maxProfit) {
                    maxProfit = good.getProfit();
                    selectedGood = good;
                }
            }

            // Update the local cards and bag
            cardsInHand.remove(selectedGood);
            cardsInBag.add(selectedGood);

            // Update the counters
            maxAllowedGoods--;
            countIllegal--;
        }


        // Update player's cards
        player.setCardsInHand(cardsInHand);
        player.setCardsInBag(cardsInBag);

        // Return false if the strategy was kept
        return false;
    }

    @Override
    public Good declareGoods(final Player player) {
        return new Apple();
    }

    @Override
    public void inspectBags(final Deck deck, final Sheriff sheriff, final List<Player> players) {
        int i = 0;
        int sheriffPos = -1;
        Player merchant;

        // Find the position of the sheriff
        for (Player p : players) {
            if (p.equals(sheriff)) {
                sheriffPos = i;
                break;
            }
            i++;
        }

        // If there are only 2 players, check the other one
        if (players.size() < Game.MAX_PLAYERS) {
            int pos = 1 - sheriffPos;
            merchant = players.get(pos);
            sheriff.inspectMerchantBag(deck, merchant);
        } else {
            int leftPos = sheriffPos - 1;
            int rightPos = sheriffPos + 1;

            // If the sheriff is on the last position,
            // right neighbour is at the beginning of the list
            if (sheriffPos == players.size() - 1) {
                leftPos = sheriffPos - 1;
                rightPos = 0;
            }

            // If the sheriff is on the first position,
            // left neighbour is at the end of the list
            if (sheriffPos == 0) {
                leftPos = players.size() - 1;
                rightPos = sheriffPos + 1;
            }

            // Inspect both left and right neighbours
            merchant = players.get(leftPos);
            sheriff.inspectMerchantBag(deck, merchant);

            merchant = players.get(rightPos);
            sheriff.inspectMerchantBag(deck, merchant);
        }

    }

    @Override
    public String toString() {
        return "BRIBED";
    }
}
