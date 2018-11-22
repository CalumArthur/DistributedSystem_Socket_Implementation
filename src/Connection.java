import java.net.*;
import java.util.Date;
import java.io.*;

public class Connection extends Thread {
	private Socket outputLine;
	private String next_host;
	private String this_host;
	private int next_port;
	private int this_port;
	//private Socket this_port;
	
	public Connection( String next_host, int next_port,int server_port, String next_host2) {
		this.next_host = next_host;
		this.next_port = next_port;
		this.this_host = next_host2;
		this.this_port = server_port;
		
		//this.outputLine = this_port;
	}

	public synchronized void run() {
		try {
			System.out.println("Writing to file: record.txt");
			Date timestmp = new Date();
			String timestamp = timestmp.toString();
			// Next create fileWriter -true means writer *appends*
			FileWriter fw_id = new FileWriter("record.txt", true);
			// Create PrintWriter -true = flushbuffer on each println
			PrintWriter pw_id = new PrintWriter(fw_id, true);
			// println means adds a newline (as distinct from print)
			pw_id.println(
					"Record from ring node on host" + this_host + ", port number " + this_port + ", is " + timestamp);
			System.out.println("Thread: " +Thread.currentThread().getName() +" wrote to file: record.txt");
			pw_id.close();
			fw_id.close();
		} catch (java.io.IOException e) {
			System.out.println("Error writing to file: " + e);
		}
		try {
			sleep(3000);
		} catch (java.lang.InterruptedException e) {
			System.out.println("sleep failed: " + e);
		}
		try{// connect to next node in the ring -signals passing the token.
			Socket s= new Socket(next_host, next_port);
			if(s.isConnected())// Did it connect OK?
				{
					System.out.println("Socket to next node(" + next_host + ": "+ next_port + ")connected OK");
				}
				else System.out.println("** Socket to next ring node ("+next_host+ ": "+next_port+ ") failed to connect");
				try{sleep(100);}  // a short delay before closing socket.
				catch(java.lang.InterruptedException e){System.out.println("sleep fail: "+e);}
				s.close() ;   // token now passed.
				try{sleep(100);}  // anothershort delay 
				catch(java.lang.InterruptedException e){System.out.println("sleep fail: "+e);}
				if(s.isClosed() )System.out.println("Socket to next ring node ("+next_host+ ": "+next_port+ ") is now closed") ;
				else System.out.println("** Socket to next ring node ("+next_host+ ": "+next_port+ ") is still open!!") ;
		}
		catch(Exception e){}// end of socket try
	}
}
