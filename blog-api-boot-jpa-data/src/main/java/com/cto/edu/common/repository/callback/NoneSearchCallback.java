package com.cto.edu.common.repository.callback;


import com.cto.edu.common.model.search.Searchable;

import javax.persistence.Query;

public final class NoneSearchCallback implements SearchCallback {

    public void prepareQL(StringBuilder ql, Searchable search) {
    }

    public void prepareOrder(StringBuilder ql, Searchable search) {
    }

    public void setValues(Query query, Searchable search) {
    }

    public void setPageable(Query query, Searchable search) {
    }
}
