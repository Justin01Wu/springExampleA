package test.spring.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyResponseWrapper extends HttpServletResponseWrapper {

	public MyResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	public ServletOutputStream getOutputStream() throws IOException {
		//return new MyTeeOutputStream(super.getOutputStream(), loggingOutputStream());
		return new TeeServletOutputStream(super.getOutputStream(), loggingOutputStream());
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
	
	// retrieve http status in filter is a little trick, 
	// the idea comes from 
	//   http://stackoverflow.com/questions/1302072/how-can-i-get-the-http-status-code-out-of-a-servletresponse-in-a-servletfilter
	private int httpStatus;
    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }
    
    @Override
    public void sendError(int sc) throws IOException {
    	httpStatus = sc;
    	super.sendError(sc);
    }
    @Override
    public void sendError(int sc, String msg) throws IOException {
    	httpStatus = sc;
    	super.sendError(sc, msg);
    }
    
    @Override
    public void setStatus(int status, String string) {
        super.setStatus(status, string);
        this.httpStatus = status;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.httpStatus = SC_OK;
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        httpStatus = SC_MOVED_TEMPORARILY;
        super.sendRedirect(location);
    }
    
    public int getStatus() {
        return httpStatus;
    }

}