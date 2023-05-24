package hu.nye.service;

import hu.nye.model.Route;
import hu.nye.model.Settlement;

import java.util.*;

public class AStar {
    public static List<Settlement> aStar(Settlement start, Settlement destination) {
        PriorityQueue<Route> openList = new PriorityQueue<>((a, b) -> Double.compare(a.getCost() + a.getRoute().get(a.getRoute().size() - 1).getHeuristics(),
                b.getCost() + b.getRoute().get(b.getRoute().size() - 1).getHeuristics()));
        Set<Settlement> locked = new HashSet<>();

        Route startRoute = new Route(Collections.singletonList(start), 0);
        openList.add(startRoute);

        while (!openList.isEmpty()) {
            Route actualRoute = openList.poll();
            Settlement actualSettlement = actualRoute.getRoute().get(actualRoute.getRoute().size() - 1);

            if (actualSettlement.equals(destination)) {
                return actualRoute.getRoute();
            }

            if (locked.contains(actualSettlement)) {
                continue;
            }

            locked.add(actualSettlement);

            for (Settlement neighbour : actualSettlement.getNeighbours()) {
                if (locked.contains(neighbour)) {
                    continue;
                }

                double cost = actualRoute.getCost() + distance(actualSettlement, neighbour);
                List<Settlement> ut = new ArrayList<>(actualRoute.getRoute());
                ut.add(neighbour);
                double heuristics = distance(neighbour, destination);
                Route newRoute = new Route(ut, cost);
                newRoute.getRoute().get(newRoute.getRoute().size() - 1).setHeuristics(heuristics);

                openList.add(newRoute);
            }
        }

        return null; // Ha nem található útvonal
    }

    public static double distance(Settlement settlement1, Settlement settlement2) {

        // Euklideszi távolság használata
        double latDiff = settlement1.getLat() - settlement2.getLat();
        double lonDiff = settlement1.getLon() - settlement2.getLon();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

}
