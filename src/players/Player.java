package players;

import goods.Good;
import main.Deck;
import strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private static final int MAX_CARDS_IN_HAND = 6;
    private static final int INITIAL_COINS = 50;
    public static final int MAX_CARDS_IN_BAG = 5;

    private List<Good> cardsInHand = new ArrayList<>();
    private List<Good> cardsInBag = new ArrayList<>();
    private List<Good> stall = new ArrayList<>();
    private Good declaredGood;

    private int coins = INITIAL_COINS;
    private int bribe = 0;
    private int round = 0;

    private boolean isSheriff = false;
    private Strategy strategy;

    /*
     * Implements the logic of a player's turn
     * according to the player's role (merchant or sheriff)
     */
    public abstract void playTurn(List<Player> players, Deck deck);

    /*
     * Adds bonus cards to the stall for every illegal item in bag
     */
    public abstract void addBonusCards();

    public final int computeScore() {
        List<Good> finalStall = this.getStall();
        int collectedCoins = 0;
        for (Good good : finalStall) {
            collectedCoins += good.getProfit();
        }
        return collectedCoins;
    }

    public final void fillCardsInHand(final Deck deck) {
        int toBeAdded = MAX_CARDS_IN_HAND - cardsInHand.size();
        cardsInHand.addAll(deck.getFirstN(toBeAdded));
        deck.removeFirstN(toBeAdded);
    }

    public final void initializeNewPlayerFromOld(final Player p) {
        cardsInHand = p.getCardsInHand();
        cardsInBag = p.getCardsInBag();
        stall = p.getStall();
        this.setCoins(p.getCoins());
        this.setStrategy(p.getStrategy());
        this.setRound(p.getRound());
    }

    public final void emptyBag() {
        cardsInBag.clear();
    }

    public final List<Good> getStall() {
        return stall;
    }

    public final List<Good> getCardsInHand() {
        return cardsInHand;
    }

    public final void setCardsInHand(final List<Good> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public final List<Good> getCardsInBag() {
        return cardsInBag;
    }

    public final void setCardsInBag(final List<Good> cardsInBag) {
        this.cardsInBag = cardsInBag;
    }

    public final Good getDeclaredGood() {
        return declaredGood;
    }

    public final void setDeclaredGood(final Good declaredGood) {
        this.declaredGood = declaredGood;
    }

    public final Strategy getStrategy() {
        return strategy;
    }

    public final void setStrategy(final Strategy strategy) {
        this.strategy = strategy;
    }

    public final int getCoins() {
        return coins;
    }

    public final void setCoins(final int coins) {
        this.coins = coins;
    }

    public final boolean isSheriff() {
        return isSheriff;
    }

    public final void setSheriff(final boolean sheriff) {
        isSheriff = sheriff;
    }

    public final int getBribe() {
        return bribe;
    }

    public final void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    public final boolean givesBribe() {
        return (bribe != 0);
    }

    public final int getRound() {
        return round;
    }

    public final void setRound(final int round) {
        this.round = round;
    }

    public final void incrementRound() {
        this.round++;
    }

    @Override
    public final String toString() {
        return this.getStrategy().toString() + ": "
                + coins;
    }
}
