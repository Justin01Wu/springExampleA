package test.spring.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletOutputStream;


/** The TeeOutputStream is similar, but it has no backing writer of itself so you'll need to redirect each call to both output streams: 
 * 
 * @author justin.wu
 * @deprecated, it is replaced with TeeServletOutputStream because it used Apache TeeOutputStream, reduce the code 
 *
 */
@Deprecated
public class MyTeeOutputStream extends ServletOutputStream {
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