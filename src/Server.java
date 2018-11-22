import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket s;
	private Socket client;
	private Connection c;

	public Server(int this_port, String next_host, int next_port) { // create the socket the server will listen to
		try {
			s = new ServerSocket(this_port); // port
		} catch (java.io.IOException e) {
			System.out.println(e);
			System.exit(1);
		} // OK, now listen for connections
		System.out.println("Server is listening ....");// server will listen all the time
		try {
			InetAddress server_inet_address = InetAddress.getLocalHost(); // Data
			String server_host_name = server_inet_address.getHostName();// Data
			System.out.println("Server hostname is " + server_host_name);// Data
			System.out.println("Server Address is " + server_inet_address);// Data
			System.out.println("Server port is " + this_port );// Data
			while (true) {
				client = s.accept();// create a separate thread to service the client
				System.out.println("Server has started a connection");// Connection starts
				c = new Connection( next_host, next_port, this_port, next_host);
				System.out.println("Connection " + c.getName() + "started");
				c.start();
			}
		} catch (java.io.IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String argv[]) {

		if ((argv.length < 3) || (argv.length > 3)) {
			System.out.println("Usage: [this port][next host][next port]");
			System.out.println("Only " + argv.length + " parametersentered");
			System.exit(1);
		}

		int this_port = Integer.parseInt(argv[0]);
		String next_host = argv[1];
		int next_port = Integer.parseInt(argv[2]);

		Server ring_host = new Server(this_port, next_host, next_port);
	}
}
