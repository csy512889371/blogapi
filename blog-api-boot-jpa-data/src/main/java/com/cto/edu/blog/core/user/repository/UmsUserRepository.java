package com.cto.edu.blog.core.user.repository;


import com.cto.edu.blog.entity.user.UmsUser;
import com.cto.edu.common.repository.CustomRepository;

import java.util.List;

public interface UmsUserRepository extends CustomRepository<UmsUser, String> {

    UmsUser findByUsername(String username);

    List<UmsUser> findByNickname(String nickname);
}
