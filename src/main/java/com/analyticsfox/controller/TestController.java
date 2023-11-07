package com.analyticsfox.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/Apis")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {
	
	HttpHeaders headers = new HttpHeaders();
/*   */
	@GetMapping("/test")
	public String getTest()  {
		
		
		return "welcome Test";
	}
	
	
}
