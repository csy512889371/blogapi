package com.cto.edu.blog.core.log.repository;

import com.cto.edu.blog.entity.log.UmsLog;
import com.cto.edu.common.repository.CustomRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmsLogRepository extends CustomRepository<UmsLog, String> {
}
