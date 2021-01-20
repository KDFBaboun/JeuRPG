import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class ChatServeur {

	private static String affichage = "Vous etes bien connecte au tchat !";
	private static ArrayList<String> userNames = new ArrayList<String>();
	private static ArrayList<ThreadConnexion> userThreads = new ArrayList<ThreadConnexion>();


	public static void main(String[] args){
		try {
			ServerSocket sS = new ServerSocket(1026);
			System.out.println("En attente de connexions clients");
			//sS.setSoTimeout(10000);
			while(true){
				Socket client = sS.accept();
				System.out.println("Connexion etablie");

				ThreadConnexion thCxion = new ThreadConnexion(client);
				userThreads.add(thCxion);
				thCxion.start();

			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	static class ThreadConnexion extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		private Socket client;
		private static Timer timer;

		ThreadConnexion(Socket client) throws IOException {
			this.in =  new DataInputStream(client.getInputStream());
			this.out = new DataOutputStream(client.getOutputStream());
			this.client = client; 

		}
		// la méthode run est appelée par start()
		public void run(){

			timer = createTimer(out);
			timer.start();
			System.out.println("Done");

			try {

				String pseudo = in.readUTF(); 
				userNames.add(pseudo);
				System.out.println(userNames);
				System.out.println(userNames.size());
				out.writeInt(userNames.size());
				affichage += "\n" + pseudo + " a rejoint le tchat";
				boolean fenOpen;

				while (true){
					fenOpen = in.readBoolean();
					System.out.println(fenOpen);
					if (fenOpen) {
						int code = in.readInt();
						if (code == 1){
							String message = in.readUTF();
							Calendar now = Calendar.getInstance();
							affichage += "\n" + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + " " + pseudo + " : " + message;
		                    refresh(out);
		                }
		                else if (code == 2)
		                	refresh(out);
	                }
	                else 
	                	break;
				}
				out.writeChar('c');
				removeUser(pseudo, this);
				System.out.println(userNames);
				timer.stop();

			} 
			catch(IOException e) {

			}
			
		}
	}

	private static Timer createTimer (DataOutputStream out){
	    // Création d'une instance de listener 
	    // associée au timer
	    ActionListener action = new ActionListener ()
	    {
	        // Méthode appelée à chaque tic du timer
	        public void actionPerformed (ActionEvent event)
	        {
	        	try{
	        		if (userThreads.size() > 0){
	        			refresh(out);
	        		}
	        	} catch(IOException e) {
					e.printStackTrace();
				}
	     	}
	  	};
	      
	    // Création d'un timer qui génère un tic
	    // chaque 500 millième de seconde
	    //return new Timer (600000-(pJ.getInitiative().calculScore()*100000), action);
	    return new Timer(500, action);
	}

	public static void refresh(DataOutputStream out) throws IOException{
		out.writeInt(userNames.size());
		out.writeUTF(affichage);
	}

	public static void removeUser(String pseudo, ThreadConnexion aUser) {
        boolean removed = userNames.remove(pseudo);
        if (removed) {
            userThreads.remove(aUser);
            affichage += "\n" + pseudo + " a quitter le tchat";
        }
    }
}