package org.sathyatech.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/***
 * This file is equals to web.xml
 * Here Provide URL(Path) and 
 * packages where our classes exist
 */
@ApplicationPath("/rest")//URL Pattern
public class AppConfig extends ResourceConfig{

	public AppConfig() {
		//write classes under this package only
		packages("org.sathyatech.app");
		//Link Filter with FC
		register(new SecurityFilter());
	}
}




