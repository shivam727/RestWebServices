package org.sathyatech.app;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Base64;

public class SecurityFilter
implements ContainerRequestFilter{

	/*** Details of Service class and methods*/
	@Context
	private ResourceInfo resource;

	/**To read Header Parameters from request**/
	@Context
	private HttpHeaders headers;

	public void filter(ContainerRequestContext req) throws IOException {
		//called method
		Method m=resource.getResourceMethod();
		if(!m.isAnnotationPresent(PermitAll.class)) {
			if(m.isAnnotationPresent(DenyAll.class)) {
				/*** if method has DenyAll annotation abort process**/
				req.abortWith(
						Response
						.ok("NO ACCESS PROVIDED")
						.status(Status.FORBIDDEN)
						.build());
				return;
			}
		}
		//do security check -un,pwd,role
		if(m.isAnnotationPresent(RolesAllowed.class)) {

			//empty or null un/pwd
			List<String> auth=headers.getRequestHeader("Authorization");
			if(auth==null || auth.isEmpty()) {
				req.abortWith(Response.ok("EMPTY DETAILS FOUND").status(Status.BAD_REQUEST).build());
				return;
			}
			List<String> users=getUserandPwd(auth.get(0));
			List<String> roles=Arrays.asList(m.getAnnotation(RolesAllowed.class).value());


			//verify user with DB
			if(!isValidUser(users, roles)) {
				//if invaid user then stop process
				req.abortWith(Response.ok("Invalid User found").status(Status.UNAUTHORIZED).build());
				return;
			}


		} //roles allowed end

	}//filter end

	/***
	 * This method is used to read un,pwd
	 * as List from Authorization Header
	 * using Base64,Tokenize concept
	 */
	public List<String> getUserandPwd(String auth) {
		//remove basic space
		auth=auth.replaceAll("Basic ", "");
		//decode
		auth=Base64.decodeAsString(auth.getBytes());
		//tokenize
		StringTokenizer str=new StringTokenizer(auth, ":");  
		return Arrays.asList(
				str.nextToken(),//un
				str.nextToken() //pwd
				);

	}

	private boolean isValidUser(List<String> users,List<String> roles) {
		if("sam".equals(users.get(0)) && "sam".equals(users.get(1)) 
				&& roles.contains("ADMIN")) {
			return true;
		}else
			if("khan".equals(users.get(0)) && "khan".equals(users.get(1)) 
					&& roles.contains("EMPLOYEE")) {
				return true;
			}
		return false;
	}

}// class end






