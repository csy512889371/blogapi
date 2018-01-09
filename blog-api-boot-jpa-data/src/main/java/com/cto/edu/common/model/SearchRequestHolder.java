package com.cto.edu.common.model;


import com.cto.edu.common.model.search.SearchRequest;

public class SearchRequestHolder {
	private final static ThreadLocal<SearchRequest> searchRequestThreadLocal = new ThreadLocal<SearchRequest>();
	
	public static void initRequestHolder(SearchRequest searchRequest) {
		searchRequestThreadLocal.set(searchRequest);
	}
	
	public static SearchRequest getSearchRequest() {
		return searchRequestThreadLocal.get();
	}
	
	public static void remove() {
		searchRequestThreadLocal.remove();
	}
}
