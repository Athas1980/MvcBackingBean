/**
 * 
 */
package com.wesley_acheson.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;


/**
 * @author Wesley Acheson
 *
 */
public class BackingBeanArgumentResolver implements WebArgumentResolver {

	/* (non-Javadoc)
	 * @see org.springframework.web.bind.support.WebArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.context.request.NativeWebRequest)
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		if (methodParameter.hasParameterAnnotation(BackingBean.class))
		{
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			Object parameter = request.getAttribute(BackingBeanUrlHandlerMapper.BACKING_BEAN_ATTRIBUTE);
			if (parameter == null)
			{
				return UNRESOLVED;
			}
			if (methodParameter.getParameterType().isAssignableFrom(parameter.getClass()))
			{
				return parameter;
			}
		}
		
			
		return UNRESOLVED;
	}

}
