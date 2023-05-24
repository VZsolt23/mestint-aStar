package hu.nye.data;

import hu.nye.model.Settlement;

import java.util.ArrayList;
import java.util.List;

public class SettlementSearcher {
    public List<Settlement> settlementList(char start, char destination) {
        ProgramDatabase programDatabase = new ProgramDatabase();
        programDatabase.setNeighbours();

        List<Settlement> startAndDestination = new ArrayList<>();

        startAndDestination.add(programDatabase.findSettlementByName(String.valueOf(start).toUpperCase()));
        startAndDestination.add(programDatabase.findSettlementByName(String.valueOf(destination).toUpperCase()));

        return startAndDestination;
    }
}
