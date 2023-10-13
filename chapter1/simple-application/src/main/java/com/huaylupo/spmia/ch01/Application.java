package com.huaylupo.spmia.ch01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RequestMapping(value = "hello")
//@EnableEurikaClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


	//организовать обнару-
	//жение служб и балансировку нагрузки на стороне клиента
    public String helloRemoteServiceCall(String firstName, String lastName) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> restExchange =
				restTemplate.exchange("http://logical-service-id/name/" + "{firstName}/" + "{lastName}",
						HttpMethod.GET, null, String.class, firstName, lastName);
		return restExchange.getBody();
    }

	@RequestMapping(value = "{firstName}/{lastName}", method = RequestMethod.GET)
	public String hello(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName) {
		return helloRemoteServiceCall(firstName, lastName);
	}

    @GetMapping(value = "/{firstName}")
    public String helloGET(
            @PathVariable("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        return String.format("{\"message\":\"Hello %s %s\"}", firstName, lastName);
    }

    @PostMapping
    public String helloPOST(@RequestBody HelloRequest request) {
        return String.format("{\"message\":\"Hello %s %s\"}", request.getFirstName(), request.getLastName());
    }
}

class HelloRequest {

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

