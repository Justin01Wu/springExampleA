package test.spring.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;

/**
 * it comes from
 * http://www.coderanch.com/t/458391/java-io/java/HttpServletResponse-Output-Stream
 * 
 * dump ServletRequest and ServletResponse bodyinto log
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
		MyRequestWrapper responseWrapper = new MyRequestWrapper(httpResponse);
		chain.doFilter(request, responseWrapper);
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

	class MyRequestWrapper extends HttpServletResponseWrapper {

		public MyRequestWrapper(HttpServletResponse response) {
			super(response);
		}

		public ServletOutputStream getOutputStream() throws IOException {
			return new MyTeeOutputStream(super.getOutputStream(), loggingOutputStream());
			//return new TeeServletOutputStream(super.getOutputStream(), loggingOutputStream());
		}

		public PrintWriter getWriter() throws IOException {
			return new TeeWriter(super.getWriter(), loggingWriter());
		}
		
		protected OutputStream loggingOutputStream() {
			//return new FileOutputStream("some file")
			return System.out;
		}
		
		protected Writer loggingWriter() {
			//return new FileWriter("some file");
			return new PrintWriter(System.out);			
		}

	}

	class TeeWriter extends PrintWriter {
		private PrintWriter writer;;

		public TeeWriter(PrintWriter writer1, Writer writer2) {
			super(writer2);
			writer = writer1;
		}

		@Override
		public void write(int c) {
			super.write(c);
			writer.write(c);
		}

		@Override
		public void write(char[] buf, int offset, int len) {
			super.write(buf, offset, len);
			writer.write(buf, offset, len);
		}

		@Override
		public void write(String str, int offset, int len) {
			super.write(str, offset, len);
			writer.write(str, offset, len);
		}

		@Override
		public PrintWriter format(String format, Object... args) {
			super.format(format, args);
			writer.format(format, args);
			return this;
		}

		@Override
		public PrintWriter format(Locale locale, String format, Object... args) {
			super.format(locale, format, args);
			writer.format(locale, format, args);
			return this;
		}
	}
	
	
	public class TeeServletOutputStream extends ServletOutputStream {

		private final TeeOutputStream targetStream;

		public TeeServletOutputStream(OutputStream one, OutputStream two) {
			targetStream = new TeeOutputStream(one, two);
		}

		@Override
		public void write(int arg0) throws IOException {
			this.targetStream.write(arg0);
		}

		public void flush() throws IOException {
			super.flush();
			this.targetStream.flush();
		}

		public void close() throws IOException {
			super.close();
			this.targetStream.close();
		}
	}

	/** The TeeOutputStream is similar, but it has no backing writer of itself so you'll need to redirect each call to both output streams: 
	 * 
	 * @author justin.wu
	 * @deprecated, it is replaced with TeeServletOutputStream because it used Apache TeeOutputStream, reduce the code 
	 *
	 */
	@Deprecated
	class MyTeeOutputStream extends ServletOutputStream {
		private ServletOutputStream output1;
		private PrintStream output2;

		/**
		 * @deprecated, it is replaced with TeeServletOutputStream because it used Apache TeeOutputStream, reduce the code
		 */
		@Deprecated
		public MyTeeOutputStream(ServletOutputStream output1, OutputStream output2) {
			this.output1 = output1;
			this.output2 = new PrintStream(output2);
		}

		@Override
		public void write(int b) throws IOException {
			output1.write(b);
			output2.write(b);
		}
		
		@Override
		public void print(char c) throws IOException {
			output1.print(c);
			output2.print(c);
		}
		
		@Override
		public void print(String c) throws IOException {
			output1.print(c);
			output2.print(c);
		}
		
		@Override
		public void print(boolean c) throws IOException {
			output1.print(c);
			output2.print(c);
		}
		
		@Override
		public void println() throws IOException{
			output1.println();
			output2.println();
		}
		
		@Override
		public void println(String c) throws IOException{
			output1.println(c);
			output2.println(c);
		}
		
		@Override
		public void println(char c) throws IOException{
			output1.println(c);
			output2.println(c);
		}

	}
	
	
	
}
