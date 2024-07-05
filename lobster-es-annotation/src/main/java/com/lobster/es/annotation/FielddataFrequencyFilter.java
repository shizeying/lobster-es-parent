package com.lobster.es.annotation;

import javax.swing.text.Segment;

/**
 * @author zeyingshi
 * @date 05 7月 2024 00:56
 */
public @interface FielddataFrequencyFilter {
	int min() default Integer.MIN_VALUE;
	int max() default Integer.MIN_VALUE;
	int minSegmentSize() default Integer.MIN_VALUE;
}