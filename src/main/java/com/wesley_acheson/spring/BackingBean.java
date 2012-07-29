package com.wesley_acheson.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Annotation which indicates that a method parameter should be bound to a given 
 * variable. Supported for {@link RequestMapping} annotated handler methods in Servlet
 * environments.
 *
 * @author Wesley Acheson
 * @see RequestMapping
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BackingBean {

}
