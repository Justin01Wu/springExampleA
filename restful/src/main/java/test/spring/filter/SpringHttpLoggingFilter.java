package test.spring.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.web.filter.GenericFilterBean;

/**
 * it comes from :
 * http://stackoverflow.com/questions/2158647/logging-response-body-html-from-httpservletresponse-using-spring-mvc-handlerin
 * 
 *  dump ServletRequest and ServletResponse body into log
 * ServletResponse and ServletRequest can not be relocated its position, so this code will use TeeOutputStream to fork it
 * 
 * the performance is low, so please set url-pattern in web.xml carefully only to those URLs we care 
 * @author justin.wu
 *
 */
public class SpringHttpLoggingFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		final StringBuilder logMessage = new StringBuilder("   ===>> HTTP Request - ");
		logMessage.append("[HTTP METHOD:").append(httpRequest.getMethod());
		String fullURL = getFullURL(httpRequest);
		logMessage.append("] [URL:").append(fullURL);

		System.out.println(logMessage);

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// filterChain.doFilter(request, response);
		HttpServletResponse responseWrapper = loggingResponseWrapper(httpResponse);
		filterChain.doFilter(request, responseWrapper);

	}

	private HttpServletResponse loggingResponseWrapper(HttpServletResponse response) {
		return new HttpServletResponseWrapper(response) {
			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return new DelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), loggingOutputStream()));
			}
		};
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

	private OutputStream loggingOutputStream() {
		return System.out;
	}

}
