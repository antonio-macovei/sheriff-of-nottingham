package goods;

import players.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class Good {
    public abstract int getId();

    public abstract String getName();

    public abstract boolean isLegal();

    public abstract int getProfit();

    public abstract int getPenalty();

    public abstract Good getBonus();

    public abstract int getBonusMultiplier();

    public abstract int getKingBonus();

    public abstract int getQueenBonus();

    public static void computeBonus(final List<Player> players) {
        List<Good> legalGoods = new ArrayList<>();
        legalGoods.add(new Apple());
        legalGoods.add(new Cheese());
        legalGoods.add(new Bread());
        legalGoods.add(new Chicken());

        for (Good good : legalGoods) {
            Good.addBonusForGood(good, players);
        }
    }

    private static void addBonusForGood(final Good good, final List<Player> players) {
        Map<Player, Integer> occurrences = new LinkedHashMap<>();
        for (Player p : players) {
            List<Good> stall = p.getStall();
            occurrences.put(p, 0);
            for (Good stallGood : stall) {
                if (good.getId() == stallGood.getId()) {
                    occurrences.replace(p, occurrences.get(p) + 1);
                }
            }
        }

        int kingOccurrences = 0;
        int queenOccurrences = 0;

        for (Player p : players) {
            if (occurrences.get(p) > kingOccurrences) {
                kingOccurrences = occurrences.get(p);
            }
        }
        for (Player p : players) {
            if (occurrences.get(p) > queenOccurrences && occurrences.get(p) != kingOccurrences) {
                queenOccurrences = occurrences.get(p);
            }
        }
        for (Player p : players) {
            if (occurrences.get(p) == kingOccurrences) {
                p.setCoins(p.getCoins() + good.getKingBonus());
            } else if (occurrences.get(p) == queenOccurrences) {
                p.setCoins(p.getCoins() + good.getQueenBonus());
            }
        }
    }
}
