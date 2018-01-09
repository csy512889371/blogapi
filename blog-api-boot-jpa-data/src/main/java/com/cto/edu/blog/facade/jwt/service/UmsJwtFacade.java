package com.cto.edu.blog.facade.jwt.service;


import com.cto.edu.blog.facade.jwt.domain.Payload;

/**
 * JWT
 */
public interface UmsJwtFacade {
	
	String createJwt(String username, boolean remember) throws Exception;
	
	void removeJwt(String username, String jwt) throws Exception;
	
	Payload getPayload(String jwt);
}
