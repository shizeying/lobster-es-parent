package com.lobster.es.annotation.enums;

/**
 * @author w7410
 * @date 09 7月 2024 01:43
 */
public enum DocValuesOption {
	/**
	 * true
	 */
	TRUE,
	
	/**
	 * false
	 */
	FALSE,
	
	/**
	 * None
	 */
	NONE;
	
	
	public static Boolean getDocValuesOption(DocValuesOption docValuesOption) {
		switch (docValuesOption) {
			
			case TRUE:
				return Boolean.TRUE;
			case FALSE:
				return Boolean.FALSE;
			default:
				return null;
		}
	}
}