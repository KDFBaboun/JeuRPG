import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Serveur {
	private static String host = "127.0.0.1"; 

	private static Plateau plat = new Plateau(40, 60);
	private static int nbClients = 0;
	private static ArrayList<PJ> pJsSave = new ArrayList<PJ>();
	private static ArrayList<byte[]> nompJsSave = new ArrayList<byte[]>();
	private static ArrayList<PNJ> pNJs = new ArrayList<PNJ>();
	private static ArrayList<PJ> pJCo = new ArrayList<PJ>();
	private static ArrayList<Objet> objetNiv1 = new ArrayList<Objet>();
	private static ArrayList<Objet> objetNiv2 = new ArrayList<Objet>();
	private static ArrayList<Objet> objetNiv3 = new ArrayList<Objet>();
	private static Timer timer;
	private static Timer timer2;
	private static Timer timer3;
	private static Timer timer4;
	private static String log;

	public static void main(String[] args){
		try {
			ServerSocket sS = new ServerSocket(1025, 10, InetAddress.getByName(host));
			System.out.println("En attente de connexions clients");
			//sS.setSoTimeout(10000);
			creerPlacerObjetsJeu();
			creerPNJ();
			placerPNJ();
			timer2 = createTimerCheckVie();
			timer2.start();
			timer3 = createTimerActionPNJ(pNJs, plat);
			timer3.start();

			while(true){
				Socket client = sS.accept();
				System.out.println("Connexion etablie");

				ThreadConnexion thCxion = new ThreadConnexion(client);
				thCxion.start();

			}

		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	static class ThreadConnexion extends Thread {
		DataInputStream in;
		DataOutputStream out;
		Socket client;
		ThreadConnexion(Socket client) throws IOException {
			this.in =  new DataInputStream(client.getInputStream());
			this.out = new DataOutputStream(client.getOutputStream());
			this.client = client; 
			nbClients ++;
		}
		// la méthode run est appelée par start()
		public void run(){
			System.out.println("Done");
			try {
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeInt(nbClients);
				DataInputStream in = new DataInputStream(client.getInputStream());

				//setpJsSave(out, in, 0);

				log = new String("Actions sur le serveur depuis le debut de la partie :\n");

				while (true){
					jouer(out, in, client);
				}
		
			} 
			catch(IOException e) {

			}
			
		}
	}

	public static PJ menuDemarrage(DataOutputStream out, DataInputStream in) throws IOException{
		int choixM = in.readInt();
		System.out.println(choixM);

		PJ pJ = null;

		if (choixM == 1){
			pJ = creaNouvPJ(in, out);
			pJCo.add(pJ);
			String s = new String("Vous serez representez sur la carte par le chiffre " + pJ.getNumPJ() + "\n" + pJ.toString());
			out.writeUTF(s);
		}

		else if (choixM == 2){
			pJ = charger(out, in, 1);
			pJCo.add(pJ);
			log += pJ.getNom() + " est de retour dans le royaume\n";
			out.writeUTF(pJ.getNom());
			out.writeUTF(pJ.toString());
			System.out.println(pJ.toString());
		}
		return pJ;
	}

	public static PJ creaNouvPJ(DataInputStream in, DataOutputStream out) throws IOException{
		PJ newPJ = null;

		String pseudo = in.readUTF();

		/*
		ArrayList<String> noms = new ArrayList<String>();

		for(int i = 0; i < pJCo.size(); i++){
			noms.add(pJCo.get(i).getNom());
		}
		for(int i = 0; i < nompJsSave.size(); i++){
			String nom = pJCo.get(i).getNom();
			if (noms.contains(nom))
				noms.add(nom);
		}

		while (noms.contains(pseudo)){
			pseudo = in.readUTF();
			out.writeBoolean(false);
		}
		System.out.println(noms);
		out.writeBoolean(true);
		*/


		while(true){
			int force =  in.readInt();
			int adresse = in.readInt();
			int resistance = in.readInt();
			int validation = in.readInt();
			
			PotionSoin p1 = new PotionSoin("Soin Test", 1);
			PotionAdresse p2 = new PotionAdresse("Pot Adresse Test", 1);
			PotionForce p3 = new PotionForce("Potion force Test", 1);
			Bombe b4 = new Bombe("Explosion Test", 1);
			Epee a5 = new Epee("Excalibur Test", 2, new Niveaux(5, 3), new Niveaux(5, 1));
			Hache h6 = new Hache("Hache Viking Test", 2, new Niveaux(3, 3), new Niveaux(2, 4));
			Armure a7 = new Armure("Armure du Test Ultime", 3, new Niveaux(4, 3), new Niveaux(2, 1));
			ArrayList<Objet> sac = new ArrayList<Objet>();
			sac.add(p3);
			sac.add(b4);
			sac.add(p1);
			sac.add(p2);
			sac.add(a7);
			
			if (validation == 1){
				newPJ = new PJ(pseudo, force, adresse, resistance, pJCo.size()+1);
				break;
			}
		}
		return newPJ;
	}

	public static void jouer(DataOutputStream out, DataInputStream in, Socket client) throws IOException{
		PJ pJ = menuDemarrage(out, in);
		plat.spawnAleatoire(pJ);

		timer = createTimerPA(pJ);
		timer.start();
		timer4 = createTimerRegainVie(pJ);
		timer4.start();

		while (true && pJCo.contains(pJ)){
			int santePJ = pJ.getBlessure();
			out.writeInt(santePJ); 
			System.out.println(pJ.getExperience());
			pJ.setNiveauExp(pJ.getExperience());

			if (santePJ > 2){
				out.writeUTF(plat.afficher());
				out.writeUTF(pJ.toString());
				int nbPA = pJ.getNbPA();
				out.writeInt(nbPA);

				int choixAction = in.readInt();
				System.out.println(choixAction); 
				boolean sacVide = pJ.sacVide();
				boolean objEquiperVide = pJ.objEquiperVide();

				if (choixAction == 1){
					if (nbPA >+ 2){
						int sens = in.readInt();
						boolean fait = plat.deplacerPerso(sens, pJ);
						out.writeBoolean(fait);
						while (fait != true){
							sens = in.readInt();
							fait = plat.deplacerPerso(sens, pJ);
							out.writeBoolean(fait);
						}
						pJ.deplacement();
						log += pJ.getNom() + " s'est deplacer\n";
					}
				}

				else if (choixAction == 2){
					if(nbPA >= 3){
						Case pos = pJ.getPosition();
						Case[] casesAutour = plat.getCasesAutours(pos);

						ArrayList<Case> casesPerso = new ArrayList<Case>();
			        	Personnage perso = null;

			        	for (int i = 0; i < 4; i++){
			        		Object contenu = casesAutour[i].getContenu();

			        		if (contenu instanceof Personnage){
			        			casesPerso.add(casesAutour[i]);
			        			perso = (Personnage)(casesAutour[i].getContenu());
			        		}
			        	}

			        	int nbPersoAutour = casesPerso.size();
			        	out.writeInt(nbPersoAutour);

			        	Personnage p = null;
			        	if (nbPersoAutour > 0){
			        		choixArmesAtk(out, in, pJ);
			        		for (int i = 0; i < nbPersoAutour; i++){
			        			p = (Personnage)casesPerso.get(i).getContenu();
			        				if (p instanceof Personnage)
			        					out.writeUTF(p.getNom());
			        		}

			        		int choixAtk = in.readInt();
			        		if (choixAtk > 0){
				        		Personnage cible = (Personnage)casesPerso.get(choixAtk-1).getContenu();
				        		boolean resultAtk = pJ.attaquer(cible, pJ.getArmeAtk());
				        		out.writeBoolean(resultAtk);
				        		log += pJ.getNom() + " a attaquer " + cible.getNom() + " avec " + pJ.getArmeAtk() + "\n";
				        		System.out.print(cible.getBlessure());
				        		if(cible.getBlessure() <= 0){
				        			int pJExp = pJ.getExperience();
				        			pJ.setExperience(pJExp + (10*((int)pJExp/cible.getExperience())));
				        		}
			        		}

			        	}
			        }
				}

				else if (choixAction == 3){
					if (sacVide)
						out.writeInt(0);
					else{
						out.writeInt(1);
						Objet objUtiliser = null;
						Objet [] objEquiper = pJ.getObjEquiper();
						String utiliser = new String("");
						for (int i = 0; i < 2; i++) {
				            if (objEquiper[i] != null)
				                utiliser += objEquiper[i] + "\n";
				            else 
				                utiliser += "- Emplacement vide" + "\n";
				        }

				        utiliser += "\n" + "Que voulez vous utiliser ?" + "\n" + "- Votre sac : " + "\n";
				        ArrayList<Objet> sac = pJ.getSac();
			        	out.writeInt(1);
		                for (int i = 0; i < sac.size(); i++) {
		                	utiliser += sac.get(i) + " ==> " + (i+1) + "\n";
		            	}
					
						out.writeUTF(utiliser);
						int objet = in.readInt() - 1;

						Objet objChoisi = sac.get(objet);

						if (objChoisi.getCoutUtil() <= pJ.getNbPA()){
							out.writeBoolean(true);
							if (objChoisi instanceof Equipement){
								String equipements = new String("");
								if (objChoisi instanceof Equipement){
									out.writeInt(1);
					                equipements += "Pour remplacer quelle Equipements ?" + "\n";
					                for (int i = 0; i < 2; i++) {
						                if (objEquiper[i] != null)
						                    equipements += objEquiper[i] + " ==> " + (i+1) + "\n";
						                else
						                    equipements += "- Emplacement vide" + (i+1) + "\n";
						            }

						            out.writeUTF(equipements);
									int aDesequiper = in.readInt() - 1;

									if (objEquiper[aDesequiper] != null){

					                    Objet aEquiper = sac.set(objet, objEquiper[aDesequiper]);
					                    objEquiper[aDesequiper] = aEquiper;
					                    objUtiliser = aEquiper;
					                   
					                }
					                else{
					                    Objet aEquiper = sac.remove(objet);
					                    objEquiper[aDesequiper] = aEquiper;
					                    objUtiliser = aEquiper;
					                }
						            pJ.utiliser(objUtiliser);
						            log += pJ.getNom() + " a equiper un nouvel objet\n";
						            pJ.setCompetences();
						        }
						    }
						    else if(objChoisi instanceof Consommable){
						    	if(objChoisi instanceof Potion){
						    		Potion potion = (Potion)objChoisi;
						    		out.writeInt(2);
						    		potion.appliquerEffet(pJ);
						    		pJ.utiliser(potion);
						    		log += pJ.getNom() + " a utiliser une potion\n";
						    		sac.remove(potion);	
						    	}

						    	else if(objChoisi instanceof Explosif){
						    		out.writeInt(3);

						    		Case pos = pJ.getPosition();
									Case[] casesAutour = plat.getCasesAutours(pos);

									ArrayList<Case> casesPerso = new ArrayList<Case>();
						        	Personnage perso = null;

						        	for (int i = 0; i < 4; i++){
						        		Object contenu = casesAutour[i].getContenu();

						        		if (contenu instanceof Personnage){
						        			casesPerso.add(casesAutour[i]);
						        			perso = (Personnage)(casesAutour[i].getContenu());
						        		}
						        	}

						        	int nbPersoAutour = casesPerso.size();
						        	out.writeInt(nbPersoAutour);

						        	Personnage p = null;
						        	if (nbPersoAutour > 0){
						        		for (int i = 0; i < nbPersoAutour; i++){
						        			p = (Personnage)casesPerso.get(i).getContenu();
						        			out.writeUTF(p.getNom());
						        		}

						        		int choixExplo = in.readInt();
						        		if (choixExplo > 0){
							        		Personnage cible = (Personnage)casesPerso.get(choixExplo-1).getContenu();
							        		Explosif explo = (Explosif)objChoisi;
							        		explo.appliquerEffet(cible);
							        		System.out.print(cible.getBlessure());
							        		pJ.utiliser(explo);
							        		log += pJ.getNom() + " a utiliser un explosif sur " + cible.getNom() + "\n";
						    				sac.remove(explo);	
						        		}
						        	}
						    	}
						        pJ.setCompetences();	
						    
						    }
					    }
					    else
					    	out.writeBoolean(false);
		
					}
				}

				else if (choixAction == 4){
					if (nbPA >= 2){
						int choixA = in.readInt();
						System.out.println(choixA);
						Case pos = pJ.getPosition();
						Case[] casesAutour = plat.getCasesAutours(pos);

						if (choixA == 1){

				        	Case caseObj = null;
				        	Objet obj = null;
				        	int coteCase = 0;

				        	for (int i = 0; i < 4; i++){
				        		Object contenu = casesAutour[i].getContenu();

				        		if (contenu instanceof Objet){
				        			caseObj = casesAutour[i];
				        			obj = (Objet)(casesAutour[i].getContenu());
				        			coteCase = i;

				        		}
				        	}

				        	if (caseObj == null){
				        		out.writeBoolean(false);
				        		}

				        	else{
				        		out.writeBoolean(true);
				        		out.writeUTF(obj.toString());
								int choix = in.readInt();

								if (choix == 'n')
									break;

								plat.ramasserObj(pJ, coteCase);
							}

							pJ.ramasser();	
							log += pJ.getNom() + " a ramasser un objet\n";	
						}

						else{
							if (objEquiperVide && sacVide)
								out.writeInt(0);
							else{
								out.writeInt(1);
								int vide = 0;
								Objet objDepot = null;
								Objet [] objEquiper = pJ.getObjEquiper();
								String deposer = new String("- Vos objet equipes :\n");
								for (int i = 0; i < 2; i++) {
						            if (objEquiper[i] != null)
						                deposer += objEquiper[i] + "\n";
						            else{ 
						                deposer += "- Emplacement vide" + "\n";
						            	vide++;
						            }
						        }

						        deposer += "\n" + "- Votre sac : ";
						        ArrayList<Objet> sac = pJ.getSac();
					            for (int i = 0; i < sac.size(); i++)
					           		deposer += sac.get(i) + " ==> " + (i+1) + "\n";

								out.writeUTF(deposer);
								int objetDeposer = in.readInt() - 1;
								Objet aDeposer = null;

								if (objetDeposer == 0){
									aDeposer = pJ.getObjEquiper1();
									pJ.setObjEquiper1(null);
								}

								else if (objetDeposer == 1){
									aDeposer = pJ.getObjEquiper2();
									pJ.setObjEquiper2(null);
								}

								else{
									aDeposer = pJ.getSac().get(objetDeposer);
									pJ.removeObjetSac(aDeposer);
								}

								for (int i = 0; i < 4; i++){
									if (casesAutour[i] == null)
										casesAutour[i].setContenu(aDeposer);
										log += pJ.getNom() + " a deposer un objet\n";
								}
							}
						} 
					}	
		        }

		        else if (choixAction == 6){
		        	menuPause(out, in, client, pJ);
		        }

		        else if (choixAction == 7){
		        	utiliserNiveaux(out, in, pJ);
		        }

				int continuer = in.readInt();
				if(continuer == 0)
					break;
			}
			else if(santePJ == 1){
				pJCo.remove(pJ);
				break;
			}

			else if(santePJ <= 0){
				pJCo.remove(pJ);
				break;
			}
		}
		supPJCo(pJ, client);
	}

	public static void utiliserNiveaux(DataOutputStream out, DataInputStream in, PJ pJ) throws IOException{
		int nivExp = pJ.getNiveauExp();
		out.writeInt(nivExp);
		int continuer = 1;
		if (nivExp > 0){
			while (continuer == 1){
				nivExp = pJ.getNiveauExp();
				out.writeInt(nivExp);
				int choix = in.readInt();
				if (choix == 1)
					pJ.caracteristiques.setForce(pJ.caracteristiques.getForce() + 1);
				else if (choix == 2)
					pJ.caracteristiques.setAdresse(pJ.caracteristiques.getAdresse() + 1);
				else if (choix == 3)
					pJ.caracteristiques.setResistance(pJ.caracteristiques.getResistance() + 1);
				pJ.setExperience(pJ.getExperience() - 20);
				pJ.setNiveauExp(pJ.getExperience());
				if(nivExp - 1 == 0)
					break;
				continuer = in.readInt();
			}
		}

	}

	public static void choixArmesAtk(DataOutputStream out, DataInputStream in, PJ pJ) throws IOException{
		Objet objEquiper1 = pJ.getObjEquiper1();
		Objet objEquiper2 = pJ.getObjEquiper2();
		if (objEquiper1 instanceof Armes && objEquiper2 instanceof Armes) {
			out.writeBoolean(true);
			String s = new String("");
			s += objEquiper1.toString() + " ==> " + 1 + "\n";
			s += objEquiper2.toString() + " ==> " + 2 + "\n";
			out.writeUTF(s);
			int choix = in.readInt();
			if (choix == 1) {
				pJ.setArmeAtk((Armes)objEquiper1);
			}
			else
				pJ.setArmeAtk((Armes)objEquiper2);
		}
		else{
			out.writeBoolean(false);
			if (objEquiper1 instanceof Armes)
				pJ.setArmeAtk((Armes)objEquiper1);
			else if (objEquiper2 instanceof Armes)
				pJ.setArmeAtk((Armes)objEquiper2);
			else
				pJ.setArmeAtk(null);
		}

		Armes armeAtk = pJ.getArmeAtk();

		if (armeAtk == null) {
			out.writeUTF("Vous n'avez que vos poing pour vous battre. ");
		}
		else
			out.writeUTF("Votre armes d'attaque : " + armeAtk.toString());

		pJ.setCompetences();
	}

	public static void menuPause(DataOutputStream out, DataInputStream in, Socket client, PJ pJ) throws IOException{
		out.writeInt(nbClients);
		int choixPause = in.readInt();
		if (choixPause == 0){
			supPJCo(pJ, client);
		}

		else if (choixPause == 1){

		}

		else if (choixPause == 2){
			sauvegarder(pJ);
			out.writeBoolean(true);
		}
		
		else if (choixPause == 3){
			out.writeUTF(log);
		}
	}

	public static void supPJCo(PJ pJ, Socket client) throws IOException{
		nbClients = nbClients - 1;
		pJCo.remove(pJ);
		pJ.getPosition().setContenu(null);
		timer.stop();
		timer4.stop();
		client.close();
	}

	public static void sauvegarder(PJ perso){
		File file = new File("Fichier.bin");
		File fileNom = new File("FichierNom.bin");

		if (!file.exists() || !fileNom.exists()){
			try {
				file.createNewFile();
				fileNom.createNewFile();
			} catch(IOException e){
				e.printStackTrace();
			}
		}

		try {
			ObjectOutputStream outNom = new ObjectOutputStream(new FileOutputStream(fileNom));
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			if(nompJsSave.contains(perso.getNom().getBytes()) == false){
				nompJsSave.add(perso.getNom().getBytes());
				outNom.writeObject(nompJsSave);
				pJsSave.add(perso);
				out.writeObject(pJsSave);
			}
			else{
				int i = pJsSave.indexOf(perso);
				pJsSave.set(i, perso);
				outNom.writeObject(nompJsSave);
			}

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static PJ charger(DataOutputStream outClient, DataInputStream inClient,int mode){
		File file = new File("Fichier.bin");
		File fileNom = new File("FichierNom.bin");

		if (!file.exists()){
			try {
				file.createNewFile();
			} catch(IOException e){
				e.printStackTrace();
			}
		}

		if (!fileNom.exists()){
			try {
				fileNom.createNewFile();
			} catch(IOException e){
				e.printStackTrace();
			}
		}

		try {
			ObjectInputStream inNom = new ObjectInputStream(new FileInputStream(fileNom));
			nompJsSave = (ArrayList<byte[]>)(inNom.readObject());

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			pJsSave = (ArrayList<PJ>)(in.readObject());
			if (mode == 1){
				String choixP = new String("");
				for(int i = 0; i < pJsSave.size(); i++){
					PJ perso = pJsSave.get(i);
					String pseudo = new String(nompJsSave.get(i));
					perso.setNom(pseudo);
					choixP += "- " + perso.getNom() + " ==> " + (i+1) + "\n";
				}
				outClient.writeUTF(choixP);
				int choixPerso = inClient.readInt();
				PJ perso = pJsSave.get(choixPerso-1);
				perso.setCompetences();
				perso.setNumPJ(pJCo.size()+1);
				pJCo.add(perso);
				return perso;
			}
			else{
				return null;
			}
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setpJsSave(DataOutputStream out, DataInputStream in, int mode){
		charger(out, in, 0);
	}

	private static Timer createTimerPA (PJ pJ){
	    // Création d'une instance de listener 
	    // associée au timer
	    ActionListener action = new ActionListener ()
	    {
	        // Méthode appelée à chaque tic du timer
	        public void actionPerformed (ActionEvent event)
	        {
	          pJ.setNbPA(pJ.getNbPA()+1);
	          System.out.println("PA recharger");
	     	}
	  	};
	      
	    // Création d'un timer qui génère un tic
	    // chaque 500 millième de seconde
	    //return new Timer (600000-(pJ.getInitiative().calculScore()*100000), action);
	    return new Timer(30000-(pJ.getInitiative().calculScore()*500), action);
	}

	private static Timer createTimerCheckVie (){
	    // Création d'une instance de listener 
	    // associée au timer
	    ActionListener action = new ActionListener ()
	    {
	        // Méthode appelée à chaque tic du timer
	        public void actionPerformed (ActionEvent event)
	        {
	          checkViePerso();
	     	}
	  	};
	      
	    // Création d'un timer qui génère un tic
	    // chaque 500 millième de seconde
	    //return new Timer (600000-(pJ.getInitiative().calculScore()*100000), action);
	    return new Timer(300, action);
	}

	private static Timer createTimerActionPNJ (ArrayList<PNJ> pNJs, Plateau plat){
	    // Création d'une instance de listener 
	    // associée au timer
	    ActionListener action = new ActionListener ()
	    {
	        // Méthode appelée à chaque tic du timer
	        public void actionPerformed (ActionEvent event)
	        {
	          	plat.actionPNJ(pNJs);
	          	System.out.println("PNJ bouge");
	    	}
	  	};
	      
	    // Création d'un timer qui génère un tic
	    // chaque 500 millième de seconde
	    //return new Timer (600000-(pJ.getInitiative().calculScore()*100000), action);
	    return new Timer(30000, action);
	}    

	private static Timer createTimerRegainVie (PJ pJ){
	    // Création d'une instance de listener 
	    // associée au timer
	    ActionListener action = new ActionListener ()
	    {
	        // Méthode appelée à chaque tic du timer
	        public void actionPerformed (ActionEvent event)
	        {
	        	int blessure = pJ.getBlessure();
	          	if(blessure < 6 && blessure > 0){
	          		pJ.setBlessure(blessure + 1);
	          	}
	    	}
	  	};
	      
	    // Création d'un timer qui génère un tic
	    // chaque 500 millième de seconde
	    //return new Timer (600000-(pJ.getInitiative().calculScore()*100000), action);
	    return new Timer(60000, action);
	}


	public static void creerPlacerObjetsJeu(){
		//Armes
		objetNiv1.add(new Dague("Dague", 1, new Niveaux(4, 4), new Niveaux(1,2))); 
		objetNiv1.add(new Dague("Baton Nyoi-bo", 1, new Niveaux(3,3), new Niveaux(2,1)));

		objetNiv2.add(new Hache("Hache de Parashu", 2,  new Niveaux(2,1) ,  new Niveaux(3,1)));
		objetNiv2.add(new Hache("Marteau Mjöllnir", 2, new Niveaux(2,1), new Niveaux(2,4)));

		objetNiv3.add(new Epee("Epee Maudite", 3 , new Niveaux(1,4), new Niveaux(5,3)));
		objetNiv3.add(new Epee("Epee Sacree", 3 , new Niveaux(4,2), new Niveaux(3,3)));
		objetNiv3.add(new Epee("Katana Liang", 3 , new Niveaux(1,1), new Niveaux(4,5)));

		//Vetements
		objetNiv1.add(new Habit("Toge", 1, new Niveaux(1, 0), new Niveaux(1,0))); 
		objetNiv1.add(new Habit("Cape", 1, new Niveaux(1,0), new Niveaux(0,2)));

		objetNiv2.add(new Armure("Armure en cuir", 2,  new Niveaux(2,1) ,  new Niveaux(2,1)));
		objetNiv2.add(new Armure("Armure en bronze", 2, new Niveaux(2,1), new Niveaux(2,4)));

		objetNiv3.add(new Armure("Armure Royale", 3 , new Niveaux(4,4), new Niveaux(3,3)));
		objetNiv3.add(new Armure("Armure Sacree", 3 , new Niveaux(4,2), new Niveaux(3,3)));
		objetNiv3.add(new Habit("Costume de Dragon", 3 , new Niveaux(5,1), new Niveaux(4,3)));

		//Consommables
		objetNiv1.add(new PotionSoin("Soin", 1)); 
		objetNiv1.add(new Bombe("Bombe Artisanal", 1));

		objetNiv2.add(new PotionResistance("Potion de resistance", 2));
		objetNiv2.add(new PotionForce("Potion de force", 2));
		objetNiv2.add(new PotionAdresse("Potion d'adresse", 2));

		plat.placerObjet(38, 58, objetNiv3.get(5));
		plat.placerObjet(38, 1, objetNiv3.get(2));
		plat.placerObjet(2, 56, objetNiv2.get(3));
		plat.placerObjet(3, 53, objetNiv2.get(5));
		plat.placerObjet(12, 32, objetNiv1.get(4));
		plat.placerObjet(13, 33, objetNiv1.get(5));
		plat.placerObjet(22, 25, objetNiv1.get(2));

	}


	public static void checkViePerso(){
		try{
			int nbPNJ = pNJs.size();
			if (nbPNJ > 0){
				for(int i = 0; i < nbPNJ; i++){
					PNJ pNJ = pNJs.get(i);
					if(pNJ.getBlessure() < 1){
						pNJ.mourir();
						pNJs.remove(pNJ);
					}
				}
			}
			else
				placerPNJ();

			for(int i = 0; i < pJCo.size(); i++){
				PJ perso = pJCo.get(i);
				if(perso.getBlessure() < 1){
					perso.mourir();
					pJCo.remove(perso);
				}
			}
		} catch (IndexOutOfBoundsException ignored){

		}
	}

	public static void creerPNJ(){
		pNJs.add(new PNJ("Cavalier sans-tete", 2, objetNiv2));
		pNJs.add(new PNJ("Loup-Garou", 2, objetNiv2));
		pNJs.add(new PNJ("Gobelin", 1, objetNiv1));
		pNJs.add(new PNJ("Zombie", 1, objetNiv1));
		pNJs.add(new PNJ("Centaure", 2, objetNiv2));
		pNJs.add(new PNJ("Dragon", 3, objetNiv3));
		pNJs.add(new PNJ("Ogre", 2, objetNiv2));
		pNJs.add(new PNJ("Bandit", 1, objetNiv1));
		pNJs.add(new PNJ("Demon", 3, objetNiv3));
	}

	public static void placerPNJ(){
		plat.placerPerso(15, 18, pNJs.get(0));
		plat.placerPerso(35, 28, pNJs.get(1));
		plat.placerPerso(10, 9, pNJs.get(2));
		plat.placerPerso(17, 50, pNJs.get(3));
		plat.placerPerso(29, 43, pNJs.get(4));
		plat.placerPerso(20, 2, pNJs.get(5));
		plat.placerPerso(30, 9, pNJs.get(6));
		plat.placerPerso(23, 13, pNJs.get(7));
		plat.placerPerso(2, 40, pNJs.get(8));
	}

	public static void fenJeux(DataInputStream in, DataOutputStream out, PJ pJ) throws IOException{
		out.writeUTF(plat.afficher());
		out.writeUTF(pJ.toString());
	}
}
