package test.spring.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * it comes from
 * http://www.coderanch.com/t/458391/java-io/java/HttpServletResponse-Output-Stream
 * 
 * dump ServletRequest and ServletResponse body into log
 * ServletResponse and ServletRequest can not be relocated its position, so this code will use TeeOutputStream to fork it
 * 
 * the performance is low, so please set url-pattern in web.xml carefully only to those URLs we care 
 
 * @author justin.wu
 *
 */
public class HttpResponseLogger implements Filter {

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		final StringBuilder logMessage = new StringBuilder("   ===>> HTTP Request - ");
		logMessage.append("[HTTP METHOD:").append(httpRequest.getMethod());
		String fullURL = getFullURL(httpRequest);
		logMessage.append("] [URL:").append(fullURL);

		System.out.println(logMessage);
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;		
		MyResponseWrapper responseWrapper = new MyResponseWrapper(httpResponse);
		chain.doFilter(request, responseWrapper);
		System.out.println(" \r\n  ==> RESPONSE STATUS: " + responseWrapper.getStatus());
	}
	
	public static String getFullURL(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}

	
	
	
}
