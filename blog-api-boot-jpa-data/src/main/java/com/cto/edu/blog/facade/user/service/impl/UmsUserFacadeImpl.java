package com.cto.edu.blog.facade.user.service.impl;

import com.cto.edu.blog.core.user.service.UmsUserService;
import com.cto.edu.blog.entity.user.UmsUser;
import com.cto.edu.blog.facade.user.exception.*;
import com.cto.edu.blog.facade.user.service.UmsUserFacade;
import com.cto.edu.common.model.search.Searchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service("umsUserFacadeImpl")
public class UmsUserFacadeImpl implements UmsUserFacade {
	
	private Logger logger = LoggerFactory.getLogger(UmsUserFacadeImpl.class);
	
	@Autowired
	private UmsUserService umsUserService;


	@Override
	public UmsUser login(String username, String password) throws UserNotExistsException,UserUnAvailableException,UserPasswordNotMatchException {
		return umsUserService.login(username, password);
	}

	@Override
	public UmsUser register(UmsUser user) throws UserUsernameEmptyException,UserPasswordEmptyException,UserNicknameEmptyException,UserUsernameNotValidException,UserPasswordNotValidException,UserNicknameNotValidException,UserUsernameExistsException {
		return umsUserService.register(user);
	}

	@Override
	public UmsUser resetPassword(String username, String newPassword) {
		return umsUserService.changePassword(findByUsername(username), newPassword);
	}

	@Override
	public UmsUser updatePassword(String username, String oldPassword, String newPassword) {
		UmsUser user = umsUserService.login(username, oldPassword);
		if(user == null){
			logger.info(username+"用户不存在");
			throw new UserNotExistsException();
		}
		user = resetPassword(username, newPassword);
		return user;
	}

	@Override
	public UmsUser findByUsername(String username) {
		return umsUserService.findByUsername(username);
	}

	@Override
	public Page<UmsUser> listPage(Searchable searchable) {
		return umsUserService.findAll(searchable);
	}

	@Override
	public UmsUser update(UmsUser user) {
		return umsUserService.update(user);
	}

	@Override
	public UmsUser updAvailable(String id, Short isAvailable) {
		UmsUser user = umsUserService.getOne(id);
		user.setIsAvailable(isAvailable);
		user = umsUserService.update(user);
		return user;
	}

	@Override
	public UmsUser findById(String id) {
		return umsUserService.getOne(id);
	}

}
