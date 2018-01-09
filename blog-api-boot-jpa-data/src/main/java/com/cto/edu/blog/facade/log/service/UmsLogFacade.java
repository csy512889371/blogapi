package com.cto.edu.blog.facade.log.service;

import com.cto.edu.blog.entity.log.UmsLog;
import com.cto.edu.common.model.search.Searchable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UmsLogFacade {
    /**
     * 日志记录
     * @param umsLog
     * @return
     */
    public UmsLog save(UmsLog umsLog);

    /**
     * 分页查询
     * @param searchable
     * @return
     */
    public Page<UmsLog> listPage(Searchable searchable);

    /**
     * 条件查询
     * @param searchable
     * @return
     */
    public List<UmsLog> list(Searchable searchable);
}
