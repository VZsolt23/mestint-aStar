package hu.nye.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Settlement {
    private String name;
    private double lat;
    private double lon;
    private double heuristics;
    private List<Settlement> neighbours;

    public Settlement(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.neighbours = new ArrayList<>();
    }

    public void addNeighbour(Settlement neighbour) {
        neighbours.add(neighbour);
    }
}