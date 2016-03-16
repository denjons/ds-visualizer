package com.dennisjonsson.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.dennisjonsson.markup.AbstractType;

@Retention(RetentionPolicy.SOURCE)
public @interface Visualize {
	AbstractType type();
}