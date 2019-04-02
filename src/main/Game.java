package main;

import goods.Good;
import players.Player;
import players.Merchant;
import players.Sheriff;
import strategies.Strategy;
import strategies.Base;
import strategies.Bribe;
import strategies.Greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;


public final class Game {
    public static final int MAX_PLAYERS = 3;
    private static final int MAX_CARDS = 216;

    private Deck deck;
    private List<String> strategies = new ArrayList<>();
    private int nPlayers;
    private int rounds;
    private List<Player> players = new ArrayList<>();

    public Game(final GameInput gameInput) {
        if (gameInput.getPlayerNames().size() > MAX_PLAYERS) {
            System.out.println("ERROR: Exceeded max players number [3]!");
            return;
        }
        if (gameInput.getAssetIds().size() > MAX_CARDS) {
            System.out.println("ERROR: Exceeded max cards number [216]!");
            return;
        }

        deck = new Deck(gameInput.getAssetIds());
        strategies.addAll(gameInput.getPlayerNames());
        nPlayers = gameInput.getPlayerNames().size();
        rounds = nPlayers * 2;
    }

    public void play() {
        for (int i = 0; i < rounds; i++) {
            playRound(i, i % nPlayers);
        }

        for (Player p : players) {
            p.setCoins(p.getCoins() + p.computeScore());
        }

        Good.computeBonus(players);

        // Sort the players to form the top
        players.sort(new Comparator<Player>() {
            public int compare(final Player p1, final Player p2) {
                if (p1.getCoins() == p2.getCoins()) {
                    return 0;
                }
                return p1.getCoins() > p2.getCoins() ? -1 : 1;
            }
        });

        // Print game results
        for (Player p : players) {
            System.out.println(p);
        }
    }

    private void playRound(final int round, final int sheriffPos) {

        LinkedHashMap<String, Strategy> strategyMapper = getStrategyMapper();

        for (int i = 0; i < nPlayers; i++) {
            Player player;

            if (sheriffPos == i) {
                player = new Sheriff();
                player.setSheriff(true);
            } else {
                player = new Merchant();
            }
            player.setStrategy(strategyMapper.get(strategies.get(i)));

            // If it is the first round, add the players to the list
            // otherwise replace the current player with the new one
            if (round == 0) {
                players.add(player);
            } else {
                player.initializeNewPlayerFromOld(players.get(i));
                players.set(i, player);
            }

            // Prepare every player for the inspection
            // except for the sheriff, who plays at the end
            if (!player.isSheriff()) {
                player.playTurn(players, deck);
            } else {
                // Fill sheriff's cards in hand too
                player.fillCardsInHand(deck);
            }
        }
        // Find the sheriff and play his turn
        for (Player p : players) {
            if (p.isSheriff()) {
                p.playTurn(players, deck);
                break;
            }
        }

        // Move items from bag to the stalls
        // and give the player's the bonus for illegal items
        for (Player p : players) {
            p.addBonusCards();
            p.getStall().addAll(p.getCardsInBag());
            p.emptyBag();
        }
    }

    private LinkedHashMap<String, Strategy> getStrategyMapper() {
        LinkedHashMap<String, Strategy> strategyMapper = new LinkedHashMap<>();

        strategyMapper.put("basic", new Base());
        strategyMapper.put("greedy", new Greedy());
        strategyMapper.put("bribed", new Bribe());

        return strategyMapper;
    }
}
