package com.lobster.es.annotation;

/**
 * @author w7410
 * @date 06 7月 2024 01:02
 */
public @interface JoinTypeRelations {
	String parent();
	
	String[] child();
}