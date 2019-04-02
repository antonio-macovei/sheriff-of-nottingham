package goods;

public final class Barrel extends Good {
    public static final int ID = 12;
    private static final String NAME = "Barrel";
    private static final boolean LEGAL = false;
    private static final int PROFIT = 7;
    private static final int PENALTY = 4;
    private static final Good BONUS = new Bread();
    private static final int BONUS_MULTIPLIER = 2;
    private static final int KING_BONUS = 0;
    private static final int QUEEN_BONUS = 0;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isLegal() {
        return LEGAL;
    }

    @Override
    public int getProfit() {
        return PROFIT;
    }

    @Override
    public int getPenalty() {
        return PENALTY;
    }

    @Override
    public Good getBonus() {
        return BONUS;
    }

    @Override
    public int getBonusMultiplier() {
        return BONUS_MULTIPLIER;
    }

    @Override
    public int getKingBonus() {
        return KING_BONUS;
    }

    @Override
    public int getQueenBonus() {
        return QUEEN_BONUS;
    }
}
