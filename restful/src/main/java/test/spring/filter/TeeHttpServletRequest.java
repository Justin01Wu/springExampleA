package test.spring.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * this code comes from lobBack
 * http://logback.qos.ch/xref/ch/qos/logback/access/servlet/TeeHttpServletRequest.html 
 *
 */
class TeeHttpServletRequest extends HttpServletRequestWrapper {

	public static final String LB_INPUT_BUFFER = "LB_INPUT_BUFFER";
	public static final String X_WWW_FORM_URLECODED = "application/x-www-form-urlencoded";

	private TeeServletInputStream inStream;
	private BufferedReader reader;
	private boolean postedParametersMode = false;

	private static boolean isFormUrlEncoded(HttpServletRequest request) {

		if ("POST".equals(request.getMethod()) && X_WWW_FORM_URLECODED.equals(request.getContentType())) {
			return true;
		} else {
			return false;
		}
	}

	TeeHttpServletRequest(HttpServletRequest request) {
		super(request);
		// we can't access the input stream and access the request parameters
		// at the same time
		if (isFormUrlEncoded(request)) {
			postedParametersMode = true;
		} else {
			inStream = new TeeServletInputStream(request);
			// add the contents of the input buffer as an attribute of the request
			request.setAttribute(LB_INPUT_BUFFER, inStream.getInputBuffer());
			reader = new BufferedReader(new InputStreamReader(inStream));
		}

	}

	byte[] getInputBuffer() {
		if (postedParametersMode) {
			throw new IllegalStateException("Call disallowed in postedParametersMode");
		}
		return inStream.getInputBuffer();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (!postedParametersMode) {
			return inStream;
		} else {
			return super.getInputStream();
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (!postedParametersMode) {
			return reader;
		} else {
			return super.getReader();
		}
	}

	public boolean isPostedParametersMode() {
		return postedParametersMode;
	}

}