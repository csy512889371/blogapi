package com.cto.edu.blog.facade.log.service.impl;

import com.cto.edu.blog.core.log.service.UmsLogService;
import com.cto.edu.blog.entity.log.UmsLog;
import com.cto.edu.blog.facade.log.service.UmsLogFacade;
import com.cto.edu.common.model.search.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("umsLogFacadeImpl")
public class UmsLogFacadeImpl implements UmsLogFacade {
    @Autowired
    private UmsLogService umsLogService;
    @Override
    public UmsLog save(UmsLog umsLog) {
        return umsLogService.save(umsLog);
    }

    @Override
    public Page<UmsLog> listPage(Searchable searchable) {
        return umsLogService.findAll(searchable);
    }

    @Override
    public List<UmsLog> list(Searchable searchable) {
        return umsLogService.findAllWithNoPageNoSort(searchable);
    }
}
