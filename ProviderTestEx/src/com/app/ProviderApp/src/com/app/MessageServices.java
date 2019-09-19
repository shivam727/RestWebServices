package com.app;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/message")
public class MessageServices {
	
	@GET
	public String showA()
	{
		return "AAA";
	}
	
	@POST
	public String showB()
	{
		return "BBB";
	}
	
	@PUT
	public String showC()
	{
		return "ccc";
	}
	
	@DELETE
	public String showD()
	{
		return "DDD";
	}
}
