package asmt.controller;

import asmt.domain.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

@Component
public class LevelDB {
    private DB db;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @PostConstruct
    public void init() throws IOException {
        Options options = new Options();
        db = factory.open(new File("levelDBStore"), options);
    }

    public byte[] getByteKey(Flight flight) {
        return ByteBuffer.allocate(4).putInt(Integer.valueOf(flight.hashCode())).array();
    }

    public DB getDb() {
        return db;
    }

    public void putFlight(Flight flight) {
        try {
            db.put(getByteKey(flight), MAPPER.writeValueAsBytes(flight));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Flight> listAllFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        try (DBIterator it = db.iterator()) {
            for (it.seekToFirst(); it.hasNext(); it.next()) {
                Map.Entry<byte[], byte[]> e = it.peekNext();
                flights.add(MAPPER.readValue(e.getValue(), Flight.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
