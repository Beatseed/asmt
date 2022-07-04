package asmt.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Flight {
    @Getter
    @Setter
        String airline;
    @Getter
    @Setter
        String sourceAirport;
    @Getter
    @Setter
        String destinationAirport;
    @Getter
    @Setter
        String codeShare;
    @Getter
    @Setter
        Integer stops;
    @Getter
    @Setter
        String equipment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return airline.equals(flight.airline) && sourceAirport.equals(flight.sourceAirport) && destinationAirport.equals(flight.destinationAirport) && Objects.equals(codeShare, flight.codeShare) && Objects.equals(stops, flight.stops) && Objects.equals(equipment, flight.equipment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airline, sourceAirport, destinationAirport, codeShare, stops, equipment);
    }
}
