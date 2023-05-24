package hu.nye;

import hu.nye.data.SettlementSearcher;
import hu.nye.model.Settlement;
import hu.nye.utility.InputHandler;

import java.util.List;

import static hu.nye.service.AStar.aStar;

public class Main {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        SettlementSearcher settlementSearcher = new SettlementSearcher();

        String input;
        boolean exit = false;
        while (!exit) {
            System.out.print("Kérem a kezdő és a végcélt, pl.:(L E): ");
            input = inputHandler.inputReader();
            if (!input.equalsIgnoreCase("exit")) {
                char[] letters = inputHandler.handleInput(input);

                if (letters == null) {
                    continue;
                }

                List<Settlement> settlements = settlementSearcher.settlementList(letters[0], letters[1]);
                List<Settlement> route = aStar(settlements.get(0), settlements.get(1));

                if (route != null) {
                    System.out.println("Optimális útvonal: ");
                    int routeSize = route.size();
                    int currentIndex = 0;
                    for (Settlement settlement : route) {
                        System.out.print(settlement.getName());
                        if (currentIndex < routeSize - 1) {
                            System.out.print(" -> ");
                        }
                        currentIndex++;
                    }
                    System.out.println();
                } else {
                    System.out.println("Nem található útvonal.");
                }
            } else {
                exit = true;
            }
        }
    }
}