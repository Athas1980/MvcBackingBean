//   Copyright 2012 Wesley Acheson
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.wesley_acheson.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves method parameters which are annotated with {@link BackingBean}.
 * 
 * <b>Note:</b> Only works for Http requests.
 * 
 * @author Wesley Acheson
 * 
 */
public class BackingBeanValueResolver implements HandlerMethodArgumentResolver {

	/**
	 * Constructor.
	 */
	public BackingBeanValueResolver() {
	}

	/**
	 * Implementation of
	 * {@link HandlerMethodArgumentResolver#supportsParameter(MethodParameter)}
	 * that returns true if the method parameter is annotatated with
	 * {@link BackingBean}.
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(BackingBean.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		return webRequest.getNativeRequest(HttpServletRequest.class)
				.getAttribute(
						BackingBeanUrlHandlerMapper.BACKING_BEAN_ATTRIBUTE);
	}

}
