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
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.PriorityOrdered;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * A Handler mapper that delegates to a {@link UrlBackingBeanMapper} to know
 * whether it should match a url. If it does match a url then it adds the bean
 * which matches the url to the request.
 * 
 * @author Wesley Acheson
 * 
 */
public class BackingBeanUrlHandlerMapper extends AbstractUrlHandlerMapping
		implements PriorityOrdered {

	private UrlBackingBeanMapper<?> urlMapper;

	/**
	 * 
	 * @param urlMapper
	 *            The bean which matches urls with other beans.
	 */
	public void setUrlMapper(UrlBackingBeanMapper<?> urlMapper) {
		this.urlMapper = urlMapper;
	}

	protected UrlBackingBeanMapper<?> getUrlMapper() {
		return urlMapper;
	}

	public static final String BACKING_BEAN_ATTRIBUTE = BackingBeanUrlHandlerMapper.class
			.getName() + ".backingBean";

	/**
	 * The controller which control will be passed to if there is any beans
	 * matching in @{link {@link #setUrlMapper(UrlBackingBeanMapper)}.
	 */
	public Object controller;

	/**
	 * @param controller
	 *            <p>
	 *            The controller which control will be passed to if there is any
	 *            beans matching in @{link
	 *            {@link #setUrlMapper(UrlBackingBeanMapper)}.
	 */
	public void setController(Object controller) {
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.AbstractUrlHandlerMapping#
	 * lookupHandler(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object lookupHandler(String urlPath, HttpServletRequest request)
			throws Exception {

		if (urlMapper.isPathMapped(urlPath)) {
			Object bean = urlMapper.retrieveBackingBean(urlPath);
			return buildChain(bean, urlPath);
		}

		return super.lookupHandler(urlPath, request);
	}

	/**
	 * Builds a handler execution chain that contains both a path exposing
	 * handler and a backing bean exposing handler.
	 * 
	 * @param bean
	 *            The object to be wrapped in the handler execution chain.
	 * @param urlPath
	 *            The path which matched. In this case the full path.
	 * @return The handler execution chain that contains the backing bean.
	 * 
	 * @see {@link AbstractUrlHandlerMapping#buildPathExposingHandler(Object, String, String, java.util.Map)}
	 *    
	 */
	protected HandlerExecutionChain buildChain(Object bean, String urlPath) {
		// I don't know why but the super class declares object but actually
		// returns handlerExecution chain.
		HandlerExecutionChain chain = (HandlerExecutionChain) buildPathExposingHandler(
				controller, urlPath, urlPath, null);
		addBackingBeanInteceptor(chain, bean);
		return chain;
	}

	/**
	 * Adds an inteceptor which adds the backing bean into the request to an
	 * existing HandlerExecutionChain.
	 * 
	 * @param chain
	 *            The chain which the backing bean is being added to.
	 * @param bean
	 *            The object to pass through to the controller.
	 */
	protected void addBackingBeanInteceptor(HandlerExecutionChain chain,
			Object bean) {
		chain.addInterceptor(new BackingBeanExposingInteceptor(bean));

	}

	/**
	 * An Interceptor which adds a bean to a request for later consumption by a
	 * controller.
	 * 
	 * @author Wesley Acheson
	 * 
	 */
	protected class BackingBeanExposingInteceptor extends
			HandlerInterceptorAdapter {
		private Object backingBean;

		/**
		 * @param backingBean
		 *            the bean which is passed through to the controller.
		 */
		public BackingBeanExposingInteceptor(Object backingBean) {
			this.backingBean = backingBean;
		}

		@Override
		public boolean preHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler) throws Exception {
			request.setAttribute(BACKING_BEAN_ATTRIBUTE, backingBean);
			return true;
		}
	}

}
