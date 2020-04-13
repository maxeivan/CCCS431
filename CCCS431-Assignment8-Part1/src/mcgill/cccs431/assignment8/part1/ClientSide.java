package mcgill.cccs431.assignment8.part1;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientSide {
	// initialize socket and input/output streams
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private BufferedReader input1 = null;

	// constructor to put IP address and port
	@SuppressWarnings("deprecation")
	public ClientSide(String address, int port) {
		
		try {
			// establish a connection
			socket = new Socket(address, port);
			
			//  get "HELLO CCCS 431 DTI Server READY" from the server
			input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(input1.readLine());

		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}


		String line = "";
		try {
			// send "HELLO 431" to the server and receive "ALOHA" back from the server
			input = new DataInputStream(System.in);
			out = new DataOutputStream(socket.getOutputStream());
			line = input.readLine();
			out.writeUTF(line);
			input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(input1.readLine());
			/*
			if (line.equals("HELLO 431")) {
				input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				System.out.println(input1.readLine());
			}
			*/
		} catch (IOException i) {
			System.out.println(i);
		}
		
		
		// keep reading input until "BYE" is input
		while (!line.equals("BYE")) {
			try {
				input = new DataInputStream(System.in);
				out = new DataOutputStream(socket.getOutputStream());
				
				line = input.readLine();
				out.writeUTF(line);

				if (line.equals("HELP")) {
					input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//System.out.println(input1.readLine());
					String line2 = null;
					 while ((line2 = input1.readLine()) != null) { System.out.println(line2); }	 
				}
				
				if (line.equals("DOW")) {
					input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(input1.readLine());
				}
				
				if (line.equals("TIME")) {
					input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(input1.readLine());
				}
				
				if (line.equals("DATE")) {
					input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(input1.readLine());
				}
				
				if (line.equals("DATETIME")) {
					input1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(input1.readLine());
				}
				
			} catch (IOException i) {
				System.out.println(i);
			}
		}

		// close the connection
		try {
			input.close();
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		ClientSide client = new ClientSide("127.0.0.1", 431);
	}
}
