package yao.print.wpt630.demo.connection;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.StreamConnection;

import yao.print.api.OWConnection;

public class BTconnection extends OWConnection {

	private OutputStream out;
	private StreamConnection streamConnection;

	public BTconnection(StreamConnection streamConnection) throws IOException {
		this.streamConnection = streamConnection;
		this.out = streamConnection.openOutputStream();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		out.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int start, int length) throws IOException {
		out.write(bytes, start, length);
	}

	@Override
	public void close() {
		try {
			out.close();
			streamConnection.close();
		} catch (IOException e) {
		}
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

}
