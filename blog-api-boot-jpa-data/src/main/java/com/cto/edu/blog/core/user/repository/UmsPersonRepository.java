package com.cto.edu.blog.core.user.repository;


import com.cto.edu.blog.entity.user.UmsPerson;
import com.cto.edu.common.repository.CustomRepository;

import java.util.List;

public interface UmsPersonRepository extends CustomRepository<UmsPerson, String> {

    List<UmsPerson> findBySn(String sn);

    List<UmsPerson> findByName(String name);
}
