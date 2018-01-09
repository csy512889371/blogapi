package com.cto.edu.common.repository;

import com.cto.edu.common.model.search.Searchable;
import com.cto.edu.common.repository.callback.SearchCallback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class CustomRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

    public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";

    private SearchCallback searchCallback = SearchCallback.DEFAULT;

    @SuppressWarnings("unused")
    private final EntityManager entityManager;

    private final RepositoryHelper repositoryHelper;

    /**
     * 查询所有的QL
     */
    private String findAllQL;
    /**
     * 统计QL
     */
    private String countAllQL;


    public CustomRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;

        repositoryHelper = new RepositoryHelper(domainClass);

        findAllQL = String.format(FIND_QUERY_STRING, domainClass.getName());
        countAllQL = String.format(COUNT_QUERY_STRING, domainClass.getName());
    }

    @Override
    public List<T> findAll() {
        return repositoryHelper.findAll(findAllQL);
    }

    @Override
    public List<T> findAll(final Sort sort) {
        return repositoryHelper.findAll(findAllQL, sort);
    }

    @Override
    public Page<T> findAll(final Pageable pageable) {
        return new PageImpl<T>(
                repositoryHelper.<T>findAll(findAllQL, pageable),
                pageable,
                repositoryHelper.count(countAllQL)
        );
    }

    @Override
    public long count() {
        return repositoryHelper.count(countAllQL);
    }

    @Override
    public void delete(ID id) {
        T m = getOne(id);
        delete(m);
    }

    @Override
    public void delete(T entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof LogicDeleteable) {
            ((LogicDeleteable) entity).markDeleted();
            save(entity);
        } else {
            super.delete(entity);
        }
    }

    @Override
    public Page<T> findAll(final Searchable searchable) {
        List<T> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
        long total = searchable.hasPageable() ? count(searchable) : list.size();
        if (searchable.hasPageable()) {
            return new PageImpl<T>(
                    list,
                    searchable.getPage(),
                    total
            );
        }

        return new PageImpl<T>(list);
    }

    @Override
    public long count(final Searchable searchable) {
        return repositoryHelper.count(countAllQL, searchable, searchCallback);
    }

    @Override
    public void delete(ID[] ids) {
        for (ID id : ids) {
            this.delete(id);
        }
    }


}
