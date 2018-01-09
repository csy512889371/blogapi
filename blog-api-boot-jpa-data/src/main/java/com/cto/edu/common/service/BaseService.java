package com.cto.edu.common.service;

import com.cto.edu.common.entity.AbstractEntity;
import com.cto.edu.common.model.search.Searchable;
import com.cto.edu.common.repository.CustomRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象service层基类 提供一些简便方法
 * 泛型 ： M 表示实体类型；ID表示主键类型
 * Version:1.0
 */
@Transactional
public abstract class BaseService<M extends AbstractEntity<ID>, ID extends Serializable> {

    @Autowired
    protected CustomRepository<M, ID> customRepository;


  /*  public void setCustomRepository(CustomRepository<M, ID> customRepository) {
        this.customRepository = customRepository;
    }*/

    /**
     * 保存单个实体
     *
     * @param m 实体
     * @return 返回保存的实体
     */
    public M save(M m) {
        return customRepository.save(m);
    }

    public M saveAndFlush(M m) {
        m = save(m);
        customRepository.flush();
        return m;
    }

    /**
     * 更新单个实体
     *
     * @param m 实体
     * @return 返回更新的实体
     */
    public M update(M m) {
        return customRepository.save(m);
    }


    /**
     * 删除实体
     *
     * @param m 实体
     */
    public void delete(M m) {
        customRepository.delete(m);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    public void delete(ID[] ids) {
        customRepository.delete(ids);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 实体
     */
    public void delete(ID id) {
        customRepository.delete(id);
    }


    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public M getOne(ID id) {
        return customRepository.getOne(id);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() {
        return customRepository.count();
    }

    /**
     * 查询所有实体
     *
     * @return
     */
    public List<M> findAll() {
        return customRepository.findAll();
    }

    /**
     * 按照顺序查询所有实体
     *
     * @param sort
     * @return
     */
    public List<M> findAll(Sort sort) {
        return customRepository.findAll(sort);
    }

    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return
     */
    public Page<M> findAll(Pageable pageable) {
        return customRepository.findAll(pageable);
    }

    /**
     * 按条件分页并排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public Page<M> findAll(Searchable searchable) {
        return customRepository.findAll(searchable);
    }

    /**
     * 按条件不分页不排序查询实体
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllWithNoPageNoSort(Searchable searchable) {
        searchable.removePageable();
        searchable.removeSort();
        return Lists.newArrayList(customRepository.findAll(searchable).getContent());
    }

    /**
     * 按条件排序查询实体(不分页)
     *
     * @param searchable 条件
     * @return
     */
    public List<M> findAllWithSort(Searchable searchable) {
        searchable.removePageable();
        return Lists.newArrayList(customRepository.findAll(searchable).getContent());
    }


    /**
     * 按条件分页并排序统计实体数量
     *
     * @param searchable 条件
     * @return
     */
    public Long count(Searchable searchable) {
        return customRepository.count(searchable);
    }


}
