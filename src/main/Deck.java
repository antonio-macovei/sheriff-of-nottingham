package main;

import goods.Good;
import goods.Apple;
import goods.Bread;
import goods.Cheese;
import goods.Chicken;
import goods.Silk;
import goods.Barrel;
import goods.Pepper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public final class Deck {
    private List<Good> cards = new ArrayList<>();

    public Deck(final List<Integer> assetIds) {
        LinkedHashMap<Integer, Good> cardsMapper = getCardsMapper();
        for (int key : assetIds) {
            cards.add(cardsMapper.get(key));
        }
    }

    public List<Good> getFirstN(final int n) {
        return cards.subList(0, n);
    }

    public void removeFirstN(final int n) {
        cards.subList(0, n).clear();
    }

    public void add(final Good good) {
        cards.add(good);
    }

    private LinkedHashMap<Integer, Good> getCardsMapper() {
        LinkedHashMap<Integer, Good> cardsMapper = new LinkedHashMap<>();

        cardsMapper.put(Apple.ID, new Apple());
        cardsMapper.put(Cheese.ID, new Cheese());
        cardsMapper.put(Bread.ID, new Bread());
        cardsMapper.put(Chicken.ID, new Chicken());
        cardsMapper.put(Silk.ID, new Silk());
        cardsMapper.put(Pepper.ID, new Pepper());
        cardsMapper.put(Barrel.ID, new Barrel());

        return cardsMapper;
    }
}
