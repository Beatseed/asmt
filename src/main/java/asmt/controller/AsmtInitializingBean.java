package asmt.controller;

import asmt.domain.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

@Component
public class AsmtInitializingBean implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(AsmtInitializingBean.class);
    private RestTemplate restTemplate = new RestTemplate();
    private List<String> endpoints;
@Autowired
LevelDB levelDB;

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("uri.txt");
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            endpoints=reader.lines().collect(Collectors.toList());
            endpoints.stream().map(uri-> loadFlights(uri)).flatMap(List::stream).forEach(levelDB::putFlight);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private List<Flight> loadFlights(String uri) {
        ResponseEntity<List<Flight>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Flight>>() {
                });
        return response.getBody();
    }

    public List<String> getEndpoints() {
        return endpoints;
    }
}