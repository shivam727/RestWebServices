package com.app;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/generate")
public class Generateotp {
	
	
	@Path("/otp")
	@GET
	public String generateotp() {
		
		return UUID.randomUUID().toString().substring(2, 4).toUpperCase();
		
		
	}

}
