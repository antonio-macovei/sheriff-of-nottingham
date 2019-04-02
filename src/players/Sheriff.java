package players;

import goods.Good;
import main.Deck;

import java.util.ArrayList;
import java.util.List;

public final class Sheriff extends Player {

    @Override
    public void playTurn(final List<Player> players, final Deck deck) {
        this.getStrategy().inspectBags(deck, this, players);
    }

    public void inspectMerchantBag(final Deck deck, final Player merchant) {
        Player sheriff = this;
        List<Good> inspectedBag;
        Good declaredGood;
        int confiscatedGoods;

        inspectedBag = merchant.getCardsInBag();
        declaredGood = merchant.getDeclaredGood();
        confiscatedGoods = 0;

        List<Good> toBeConfiscated = new ArrayList<>();

        // Check merchant's bag for undeclared or illegal goods
        for (Good good : inspectedBag) {
            if (good != declaredGood || !good.isLegal()) {
                // The merchant pays the penalty for every undeclared or illegal good
                int penalty = good.getPenalty();
                merchant.setCoins(merchant.getCoins() - penalty);
                sheriff.setCoins(sheriff.getCoins() + penalty);
                confiscatedGoods++;

                toBeConfiscated.add(good);
            }
        }
        // If there are no confiscated goods, the sheriff pays the penalty
        if (confiscatedGoods == 0) {
            int penalty = merchant.getCardsInBag().size() * declaredGood.getPenalty();
            sheriff.setCoins(sheriff.getCoins() - penalty);
            merchant.setCoins(merchant.getCoins() + penalty);
        } else {
            // Else confiscate the goods and put them back in the deck
            confiscateGoods(deck, merchant, toBeConfiscated);
        }
    }

    public void acceptBribe(final Player merchant) {
        // Update sheriff's and merchant's coins
        int bribe = merchant.getBribe();
        this.setCoins(this.getCoins() + bribe);
        merchant.setCoins(merchant.getCoins() - bribe);

        // Reinitialize merchant bribe to 0
        merchant.setBribe(0);
    }

    private void confiscateGoods(final Deck deck, final Player merchant, final List<Good> goods) {
        for (Good good : goods) {
            merchant.getCardsInBag().remove(good);
            deck.add(good);
        }
    }

    public void addBonusCards() {

    }

}
