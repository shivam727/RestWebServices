package org.sathyatech.app;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * This is service Provider class
 * 
 */
@Path("/product")
public class ProductService {
	@GET
	@Path("/all")
	@PermitAll
	public String showCode() {
		return "CODE-AB";
	}
	
	@GET
	@Path("/none")
	@DenyAll
	public String showMode() {
		return "MODE-NONE";
	}
	
	@GET
	@Path("/few")
	@RolesAllowed({"ADMIN","EMPLOYEE"})
	public String showDetails() {
		return "DETAILS";
	}
}




