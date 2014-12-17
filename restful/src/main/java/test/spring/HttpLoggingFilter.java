package test.spring;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * it comes from :
 * http://stackoverflow.com/questions/2158647/logging-response-body
 * -html-from-httpservletresponse-using-spring-mvc-handlerin
 * 
 * @author justin.wu
 *
 */
public class HttpLoggingFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {
		// HttpServletResponse responseWrapper =
		// loggingResponseWrapper((HttpServletResponse) response);
		// filterChain.doFilter(request, responseWrapper);
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;			

		
		final StringBuilder logMessage = new StringBuilder("   ===>> HTTP Request - ");
		logMessage.append("[HTTP METHOD:").append(httpRequest.getMethod());
		String fullURL = getFullURL(httpRequest);
		logMessage.append("] [URL:").append(fullURL);
		
		System.out.println(logMessage);
		
		filterChain.doFilter(request, response);
	}

	// private HttpServletResponse loggingResponseWrapper(HttpServletResponse
	// response) {
	// return new HttpServletResponseWrapper(response) {
	// @Override
	// public ServletOutputStream getOutputStream() throws IOException {
	// return new DelegatingServletOutputStream(
	// new TeeOutputStream(super.getOutputStream(), loggingOutputStream())
	// );
	// }
	// };
	// }
	
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
