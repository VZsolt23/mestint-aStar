package hu.nye.data;

import hu.nye.model.Settlement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProgramDatabase {
    // Városok létrehozása
    private final Settlement settlementA = new Settlement("A", 47.4979, 19.0402);
    private final Settlement settlementB = new Settlement("B", 47.5316, 21.6273);
    private final Settlement settlementC = new Settlement("C", 46.2530, 20.1414);
    private final Settlement settlementD = new Settlement("D", 46.0727, 18.2323);
    private final Settlement settlementE = new Settlement("E", 47.6875, 17.6509);
    private final Settlement settlementF = new Settlement("F", 47.4979, 19.0402);
    private final Settlement settlementG = new Settlement("G", 47.5316, 21.6273);
    private final Settlement settlementH = new Settlement("H", 46.2530, 20.1414);
    private final Settlement settlementI = new Settlement("I", 46.0727, 18.2323);
    private final Settlement settlementJ = new Settlement("J", 47.6875, 17.6509);
    private final Settlement settlementK = new Settlement("K", 46.0727, 18.2323);
    private final Settlement settlementL = new Settlement("L", 47.6875, 17.6509);

    public List<Settlement> getAllSettlements() {
        return new ArrayList<>(List.of(
                settlementA, settlementB, settlementC, settlementD, settlementE,
                settlementF, settlementG, settlementH, settlementI, settlementJ,
                settlementK, settlementL
        ));
    }

    public Settlement findSettlementByName(String name) {
        Settlement[] settlements = {
                settlementA, settlementB, settlementC, settlementD, settlementE,
                settlementF, settlementG, settlementH, settlementI, settlementJ,
                settlementK, settlementL
        };

        for (Settlement settlement : settlements) {
            if (settlement.getName().equals(name)) {
                return settlement;
            }
        }

        return null;
    }

    public void setNeighbours() {
        // Városok közötti szomszédsági kapcsolatok definiálása
        settlementA.addNeighbour(settlementB);
        settlementA.addNeighbour(settlementC);
        settlementA.addNeighbour(settlementG);
        settlementB.addNeighbour(settlementA);
        settlementB.addNeighbour(settlementF);
        settlementB.addNeighbour(settlementJ);
        settlementC.addNeighbour(settlementA);
        settlementC.addNeighbour(settlementD);
        settlementC.addNeighbour(settlementE);
        settlementD.addNeighbour(settlementC);
        settlementD.addNeighbour(settlementE);
        settlementD.addNeighbour(settlementH);
        settlementE.addNeighbour(settlementC);
        settlementE.addNeighbour(settlementD);
        settlementE.addNeighbour(settlementF);
        settlementF.addNeighbour(settlementB);
        settlementF.addNeighbour(settlementE);
        settlementF.addNeighbour(settlementJ);
        settlementF.addNeighbour(settlementK);
        settlementG.addNeighbour(settlementA);
        settlementG.addNeighbour(settlementH);
        settlementG.addNeighbour(settlementL);
        settlementH.addNeighbour(settlementD);
        settlementH.addNeighbour(settlementG);
        settlementH.addNeighbour(settlementI);
        settlementI.addNeighbour(settlementH);
        settlementI.addNeighbour(settlementJ);
        settlementI.addNeighbour(settlementL);
        settlementJ.addNeighbour(settlementB);
        settlementJ.addNeighbour(settlementF);
        settlementJ.addNeighbour(settlementI);
        settlementK.addNeighbour(settlementF);
        settlementK.addNeighbour(settlementL);
        settlementL.addNeighbour(settlementG);
        settlementL.addNeighbour(settlementI);
        settlementL.addNeighbour(settlementK);
    }
}
