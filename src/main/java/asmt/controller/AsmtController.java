package asmt.controller;

import asmt.domain.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class AsmtController {
    private static Logger logger = LoggerFactory.getLogger(AsmtController.class);
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    LevelDB levelDB;
    @Autowired
    AsmtInitializingBean initBean;

    @RequestMapping(path = "/routes", method = RequestMethod.GET, produces = "application/json")
    private @ResponseBody
    List<Flight> getRoutes() {
        return levelDB.listAllFlights();

    }

    @GetMapping(path = "/routes-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<Flight> getRoutes2() {
        Flux<Flight> flightAll = initBean.getEndpoints().stream().map(endpoint -> WebClient.create()
                .get()
                .uri(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Flight.class)).reduce(Flux::mergeWith).get();

        return flightAll;
    }

    private List<Flight> getFlights(String uri) {
        ResponseEntity<List<Flight>> response = restTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Flight>>() {
                });
        return response.getBody();
    }
}
