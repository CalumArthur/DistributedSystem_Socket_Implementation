import java.net.*;
import java.io.*;

public class Client {
	public Client() throws InterruptedException {
		try {
			Socket s = new Socket("127.0.0.1", 7000);
			InputStream in = s.getInputStream();
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			for (int i =0; i <3;i++)
			{
			System.out.println(bin.readLine());
			}
			s.close();
		} catch (java.io.IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static void main(String args[]) throws InterruptedException {
		Client client = new Client();
	}
}


//public static void main(String argv[]) throws InterruptedException {
//if ((argv.length < 1) || (argv.length > 2)) {
//	System.out.println("Usage: [host] <port>");
//	System.exit(1);
//}
//String server_host = argv[0] ;
//int server_port = 5156 ;
//if(argv.length == 2)
//	server_port = Integer.parseInt (argv[1]) ;
//Client client = new Client(server_host, server_port);} 
//}