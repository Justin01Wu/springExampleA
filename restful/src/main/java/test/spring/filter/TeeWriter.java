package test.spring.filter;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;

public class TeeWriter extends PrintWriter {
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
