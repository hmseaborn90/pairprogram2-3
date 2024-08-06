package com.techelevator.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatPicService implements CatPicService {

	private final String API_URL= "https://teapi.netlify.app/api/cats/pictures/random";
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatPic getPic() {
		CatPic catPic = new CatPic();
		try {
			catPic = restTemplate.getForObject(API_URL, CatPic.class);
		} catch (RestClientResponseException e) {
			System.out.println(e.getRawStatusCode() + " : " + e.getStatusText());
		} catch (ResourceAccessException e) {
			System.out.println(e.getMessage());
		}
		return catPic;
	}

}	
