package com.cto.edu.blog.facade.user.service.impl;

import com.cto.edu.blog.core.user.service.UmsPersonService;
import com.cto.edu.blog.entity.user.UmsPerson;
import com.cto.edu.blog.facade.user.service.UmsPersonFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("umsPersonFacadeImpl")
public class UmsPersonFacadeImpl implements UmsPersonFacade {
	
	@Autowired
	private UmsPersonService umsPersonService;

	@Override
	public UmsPerson updatePerson(UmsPerson person) {
		return umsPersonService.updatePerson(person);
	}

	@Override
	public UmsPerson findById(String id) {
		return umsPersonService.getOne(id);
	}
}
