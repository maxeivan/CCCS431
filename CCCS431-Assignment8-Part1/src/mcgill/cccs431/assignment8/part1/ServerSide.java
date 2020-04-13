package mcgill.cccs431.assignment8.part1;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerSide {
	// initialize socket and input stream
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private PrintWriter out1 = null;

	// constructor with port
	public ServerSide(int port) {
		// starts server and waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("HELLO CCCS 431 DTI Server running");
			System.out.println("Hit enter to STOP.");

			// client accepts connection, socket is created
			socket = server.accept();
			
			// sends "HELLO CCCS 431 DTI Server READY" to the client
			String myString = "HELLO CCCS 431 DTI Server READY";
			out1 = new PrintWriter(socket.getOutputStream(), true);
			out1.println(myString);

			// takes input "HELLO 431" from the client socket and send "ALOHA" back to the
			// client
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			String line = "";
			line = in.readUTF();
			//System.out.println(line);
			if (line.equals("HELLO 431")) {
				out1 = new PrintWriter(socket.getOutputStream(), true);
				out1.println("ALOHA");
			} 

			// reads message from client until "BYE" is sent
			while (!line.equals("BYE")) {
				try {
					in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					line = in.readUTF();
					//System.out.println(line);

					if (line.equals("HELP")) {
						out1 = new PrintWriter(socket.getOutputStream(), true);
						String menu = "HELP: displays the help information about the protocol\n"
								+ "DOW: returns the day of week in string.\r\n"
								+ "TIME: returns the current time. The command has an optional format argument, if not specified, default format is used on the server.\r\n"
								+ "DATE: returns the current date. The command has an optional format argument, if not specified, default format is used on the server.\r\n"
								+ "DATETIME: returns the current date and time. The command has an optional format argument, if not specified, default format is used on the server.\r\n"
								+ "BYE: ends the session. ";
						out1.println(menu);
					}

					if (line.equals("DOW")) {
						out1 = new PrintWriter(socket.getOutputStream(), true);
						Date now = new Date();
						SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); 
						out1.println(simpleDateformat.format(now));
					}

					if (line.equals("TIME")) {
						out1 = new PrintWriter(socket.getOutputStream(), true);
						out1.println(java.time.LocalTime.now());
					}
					
					if (line.equals("DATE")) {
						out1 = new PrintWriter(socket.getOutputStream(), true);
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						Date date = new Date();
						out1.println(dateFormat.format(date));
					}

					if (line.equals("DATETIME")) {
						out1 = new PrintWriter(socket.getOutputStream(), true);
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						out1.println(dateFormat.format(date));
					}
				} catch (IOException i) {
					System.out.println(i);
				}
			}
			System.out.println("BYE");

			// close connection
			socket.close();
			in.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		ServerSide server = new ServerSide(431);
	}
}
