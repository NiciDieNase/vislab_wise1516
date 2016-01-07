package tcc;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

public class TestServer {

	public static final String urlSrvr = "http://localhost:4434";
	private static SelectorThread srv;

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		srv = GrizzlyServerFactory.create(urlSrvr);
		JOptionPane.showMessageDialog(null, "TestRestServer beenden");
		srv.stopEndpoint();
	}

}
