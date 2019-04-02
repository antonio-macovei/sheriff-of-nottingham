package players;

import goods.Good;
import main.Deck;
import strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public final class Merchant extends Player {

    public void playTurn(final List<Player> players, final Deck deck) {
        // Keep the count of rounds when a greedy player is merchant
        this.incrementRound();

        // Update cards in hand
        this.fillCardsInHand(deck);

        // Fill bag for current round
        boolean changedStrategy = this.getStrategy().fillBag(this, deck);

        if (changedStrategy) {
            return;
        }

        // Declare goods for inspection
        Good declaredGood = this.getStrategy().declareGoods(this);
        this.setDeclaredGood(declaredGood);
    }

    public void changeStrategy(final Strategy newStrategy, final Deck deck) {
        Strategy initialStrategy = this.getStrategy();

        // If the player changes his strategy,
        // perform actions based on the new one
        this.setStrategy(newStrategy);

        // Fill bag for current round
        this.getStrategy().fillBag(this, deck);

        // Declare goods for inspection
        Good declaredGood = this.getStrategy().declareGoods(this);
        this.setDeclaredGood(declaredGood);

        // Set the initial strategy back for the next round
        this.setStrategy(initialStrategy);
    }

    public void addBonusCards() {
        List<Good> cardsInBag = this.getCardsInBag();
        List<Good> bonusCards = new ArrayList<>();
        for (Good good : cardsInBag) {
            if (!good.isLegal()) {
                for (int i = 0; i < good.getBonusMultiplier(); i++) {
                    bonusCards.add(good.getBonus());
                }
            }
        }

        cardsInBag.addAll(bonusCards);
        this.setCardsInBag(cardsInBag);
    }
}
