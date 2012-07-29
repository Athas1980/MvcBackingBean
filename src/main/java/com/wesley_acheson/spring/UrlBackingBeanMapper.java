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

import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

/**
 * <p>
 * Classes which can be used by an instance of a {@link BackingBeanUrlHandler}
 * to find the given instance of a bean which corresponds with the request url
 * need to implement this interface.
 * </p>
 * 
 * <p>
 * All sub classes of this need to be able to state if they match a given url
 * and be able to return the bean which is matched by that url. This is useful
 * wherever the relative paths of the urls cannot be known at design time. For
 * instance is a CMS system that allows manually set paths.
 * </p>
 * 
 * @author Wesley Acheson
 * @see BackingBeanUrlHandler
 * @see AbstractUrlHandlerMapping
 * @param <T>
 *            The the type of backing bean that can be returned {@see
 *            BackingBean}.
 */
public interface UrlBackingBeanMapper<T> {

	/**
	 * 
	 * Used to find out if there is currently any beans which match the given
	 * path.
	 * 
	 * @param relativePath
	 *            <p>
	 *            A path relative to the context root of the war file. For
	 *            example if a war file is deployed to http://example.com/ with
	 *            the name ApplicationName and the relative path is
	 *            "/custom/path" the path will match requests to
	 *            http://example.com/ApplicationName/custom/path.
	 *            </p>
	 *            <p>
	 *            Note the path begins with a / even though its relative in http
	 *            terms.
	 *            </p>
	 * 
	 * @return true If a bean is associated with the given path.
	 */
	boolean isPathMapped(String relativePath);

	/**
	 * Retrieves the bean specified by the relative path.
	 * 
	 * @param relativePath
	 *            <p>
	 *            A path relative to the context root of the war file. For
	 *            example if a war file is deployed to http://example.com/ with
	 *            the name ApplicationName and the relative path is
	 *            "/custom/path" the path will match requests to
	 *            http://example.com/ApplicationName/custom/path.
	 *            </p>
	 *            <p>
	 *            Note the path begins with a / even though its relative in http
	 *            terms.
	 *            </p>
	 * @return The bean associated with the given request path.
	 */
	T retrieveBackingBean(String relativePath);

}
