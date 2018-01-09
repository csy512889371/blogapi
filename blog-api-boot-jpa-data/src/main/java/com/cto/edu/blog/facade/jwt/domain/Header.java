package com.cto.edu.blog.facade.jwt.domain;

/**
 * JWT头部
 */
public class Header {
	
	private String typ;
	
	private String alg;

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}
	
	
}
