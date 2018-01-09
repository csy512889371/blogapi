package com.cto.edu.blog.facade.user.service;


import com.cto.edu.blog.entity.user.UmsPerson;

/**
 * 
 * @author feichongzheng
 *
 */
public interface UmsPersonFacade {
	
	UmsPerson updatePerson(UmsPerson person);
	
	UmsPerson findById(String id);
}
