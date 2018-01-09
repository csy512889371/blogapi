package com.cto.edu.common.model.search.filter;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * or 条件
 * Version:1.0
 */
public class OrCondition implements SearchFilter,Serializable {

	private static final long serialVersionUID = 1L;
	private List<SearchFilter> orFilters = Lists.newArrayList();

    OrCondition() {
    }

    public OrCondition add(SearchFilter filter) {
        this.orFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getOrFilters() {
        return orFilters;
    }

    @Override
    public String toString() {
        return "OrCondition{" + orFilters + '}';
    }
}
