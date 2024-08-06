package com.techelevator.services;

import com.techelevator.model.CatPic;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatFact;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatFactService implements CatFactService {
	private final String API_URL= "https://teapi.netlify.app/api/cats/facts/random";
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatFact getFact() {
		CatFact catFact = new CatFact();
		try {
			catFact = restTemplate.getForObject(API_URL, CatFact.class);
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return catFact;
	}

}
