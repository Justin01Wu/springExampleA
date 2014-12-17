package test.spring.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.output.TeeOutputStream;

public class TeeServletOutputStream extends ServletOutputStream {

	private final TeeOutputStream targetStream;

	public TeeServletOutputStream(OutputStream one, OutputStream two) {
		targetStream = new TeeOutputStream(one, two);
	}

	@Override
	public void write(int arg0) throws IOException {
		this.targetStream.write(arg0);
	}

	@Override
	public void flush() throws IOException {
		super.flush();
		this.targetStream.flush();
	}

	@Override
	public void close() throws IOException {
		super.close();
		this.targetStream.close();
	}
}