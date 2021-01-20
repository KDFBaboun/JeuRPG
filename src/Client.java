import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Client {

	private static Socket client;
	private static Socket chat;
	private static String pseudo;

	public static void main(String[] args) throws UnknownHostException, IOException {
		try {
			client = new Socket("127.0.0.1", 1025);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			DataInputStream in = new DataInputStream(client.getInputStream());
			int nbClients = in.readInt();

			
			menuDemarrage(out, in);
			jouer(out, in);



		} catch(IOException e) {
			System.out.println("Deconnexion");
		} catch(InputMismatchException inputEx) {
			System.out.println("Votre saisie est incorrect");
		}

	}

	public static void menuDemarrage(DataOutputStream out, DataInputStream in) throws IOException{
		System.out.println("Menu WORLD OF KNIGHTS B");
		System.out.println("1 - Nouvelle partie");
		System.out.println("2 - Reprendre une partie");
		System.out.println("3 - Quitter");
		System.out.print("Choix : ");
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		System.out.println(" ");
		out.writeInt(choix);

		if (choix == 1){
				nouvPartie(out, in);
			}

		if (choix == 2){
			String choixP = in.readUTF();
			System.out.println(choixP);
			int choixPerso = sc.nextInt();
			out.writeInt(choixPerso);
			String pseudo = in.readUTF();
			System.out.println("Bon retour dans le royaume " + pseudo + " !" 
								+ "\nContinuer(1)");
			int continuer = sc.nextInt();
			connexionTchat(pseudo, 1);
			String savePJ = in.readUTF();
		}
	}

	public static void connexionTchat(String pseudo, int mode) throws IOException{
		int rep = 1;
		if (mode == 1){
			Scanner sc = new Scanner(System.in);
			System.out.print(pseudo + " voulez vous rejoindre le chat du jeux ?  Oui(1)/Non(2) ==>");
			rep = sc.nextInt();
		}
		else if (mode == 0){
		}
		if (rep == 1){
			chat = new Socket("127.0.0.1", 1026);
			DataOutputStream outChat = new DataOutputStream(chat.getOutputStream());
			outChat.writeUTF(pseudo);
			FenChat fenChat = new FenChat();
		}
	}

	public static void nouvPartie(DataOutputStream out, DataInputStream in) throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenue dans ce magnifique jeux d'un tout nouveau genre appeler le EHLPTMMMORPGSVR");
		System.out.println("WORLD OF KNIGHTS");
		System.out.println("Vous incarenerez dans ce jeux un preux chevalier \nenfermer dans les terres sombres et hostiles du royaume des ombres");
		System.out.println("Etes vous pret a relevez le defi ?");
		System.out.print("OUI(o)/NON(n) : ");
		char choix = 'p';
		while (choix != 'o' && choix != 'n'){
			choix = sc.nextLine().charAt(0);
		}

		System.out.println(" ");
		if (choix == 'n'){
			System.out.println("Dommage a la prochaine");
		    return;
		}

		String pseudo = creaPerso(out, in);
		String newPJ = in.readUTF();
		System.out.println("Voila comment ce presente l'interface de ton chevalier");
		System.out.println("Pour l'instant tu n'a pas d'objet mais plus tu en trouvera/gagnera");
		System.out.println("Plus tes Caracteristiques s'amelioreront pour devenir un veritable chevalier crains de tes ennemies ! \n");
        System.out.println(newPJ);
        System.out.println("Etes vous pret a penetrez dans le royaume des ombres ? \n");
		System.out.print("OUI(o)/NON(n) : ");
		choix = 'p';
		while (choix != 'o' && choix != 'n' ){
			choix = sc.nextLine().charAt(0);
		}
		connexionTchat(pseudo, 1);
		System.out.println(" ");

	}

	public static String creaPerso(DataOutputStream out, DataInputStream in) throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisissez votre nom de chevalier");
		pseudo = sc.next();
		/*
		Boolean confirmation = false;
		while (confirmation == false){
			out.writeUTF(pseudo);
			confirmation = in.readBoolean();
		}*/
		out.writeUTF(pseudo);

		while (true) {
			int nbDegres = 18;
	        System.out.println(pseudo + " vous disposez de 18 degres a repartir entre 3 Caracteristiques : " );
	        System.out.print("Force : ");
	        int f = sc.nextInt();
	        while (nbDegres - f < 0){
	        	System.out.print("Attention il vous reste " + nbDegres);
				f = sc.nextInt();
			}
			nbDegres -= f;

	        out.writeInt(f);
	        System.out.println(" ");

	        System.out.print("Adresse : ");
	        int a = sc.nextInt();
	        while (nbDegres - a < 0){
	        	System.out.print("Attention il vous reste " + nbDegres);
				a = sc.nextInt();
			}
			nbDegres -= a;

	        out.writeInt(a);
	        System.out.println(" ");

	        System.out.print("Resistance : ");
	        int r = sc.nextInt();
	        while (nbDegres - r < 0){
	        	System.out.print("Attention il vous reste " + nbDegres);
				f = sc.nextInt();
			}
			nbDegres -= r;

	        out.writeInt(r);
	        System.out.println(" ");

	        if (nbDegres == 0)
	        	System.out.println("Vous avez utiliser tout vos degres.");
	        else
	        	System.out.println("Il vous reste " + nbDegres + " degres, ils seront perdu si ils ne sont pas utiliser.");

	        System.out.print("Validez(1) ou Recommencer(2) : ");
	        int rep = sc.nextInt();
	        out.writeInt(rep);
	        System.out.println(" ");
	        if (rep == 1){
	            break;
	        }
        }
        return pseudo;
 	}

	public static void jouer(DataOutputStream out, DataInputStream in) throws IOException{
		Scanner sc = new Scanner(System.in);
		while(true){
			int santePJ = in.readInt();
			if (santePJ > 2){
				String plat = in.readUTF();
				String pJ = in.readUTF();
				int nbPA = in.readInt();

				String afficherTour = new String("");
				afficherTour = plat + "\n" + pJ;
				System.out.println(afficherTour);

				System.out.print("Votre choix : ");
				int choixAction = sc.nextInt();
		        while (choixAction < 1 || choixAction > 7){
		        	System.out.print("Choisir une action entre 1 et 7");
					choixAction = sc.nextInt();
				}
				out.writeInt(choixAction);

				if (choixAction == 1){
					if (nbPA >= 2){
						System.out.println("Dans quelle direction voulez vous allez " + "\n"
							+ "haut(1) bas(2) droite(3) gauche(4)" + "\n");
						int sens = sc.nextInt();
						if (sens < 1 || sens > 4){
							System.out.println("Numero non valable ==> choisir entre 1 et 4");
							sens = sc.nextInt();
						}
						out.writeInt(sens);
						boolean fait = in.readBoolean();
						while (fait != true){
							System.out.println("Impossible d'aller dans cette direction " + "\n"
								+ "veuillez en choisir une autre");
							sens = sc.nextInt();
							out.writeInt(sens);
							fait = in.readBoolean();
						}
					}
					else
						System.out.println("Nombres de PA insuffisant.");
				}

				else if (choixAction == 2){
					if(nbPA >= 3){
						int nbPersoAutour = in.readInt();

						if (nbPersoAutour == 0)
							System.out.println("Il n'y personne a attaque");

						else{
							int choixAtk = 1;
							if (nbPersoAutour == 1){
								choixArmesAtk(out, in, sc);
								String pseudo = in.readUTF();
								System.out.println("Voulez vous attaquer " + pseudo + " ? Oui(1)/Non(0)");
								choixAtk = sc.nextInt();
								out.writeInt(choixAtk);
							}
							else {
								choixArmesAtk(out, in, sc);
								System.out.println("Qui voulez vous attaquer ? (0 pour annulez)");
								for (int i = 0; i < nbPersoAutour; i++){
									String pseudo = in.readUTF();
									System.out.println(" - " + pseudo + "==> " + (i+1));
								}

								choixAtk = sc.nextInt();
								out.writeInt(choixAtk);
							}

							if (choixAtk == 1){
								boolean resultAtk = in.readBoolean();
								if (resultAtk)
									System.out.println("Attaque reussit");
								else
									System.out.println("Attaque rater");
							}
						}
					}
					else
						System.out.println("Nombres de PA insuffisant.");

					}

				else if (choixAction == 3){
					int equipVide = in.readInt();
					if (equipVide == 0)
						System.out.println("Vous ne possedez pas d'objet");
					else {
						System.out.println("- Vos objets equipés :");
						int sacVide = in.readInt();

						String utiliser = in.readUTF();
						System.out.println(utiliser);

						if (sacVide == 1){
							System.out.println("Choix");
							int objet = sc.nextInt();
							out.writeInt(objet);

							boolean conf = in.readBoolean();
							if (conf == false)
								System.out.println("Nombres de PA insuffisant pour utiliser cet Objet.");
							else {
								int type = in.readInt();
								if(type == 1){
									String equipements = in.readUTF();
									System.out.println(equipements);

									System.out.println("Choix");
									int objetDesequiper = sc.nextInt();
									out.writeInt(objetDesequiper);
								}
								else if(type == 3){
									int nbPersoAutour = in.readInt();
									if(nbPersoAutour == 0){
										System.out.println("Personne a faire exploser :/");
									}

									else if(nbPersoAutour == 1){
										System.out.println("Qui voulez vous faire explosez ?");
										for (int i = 0; i < nbPersoAutour; i++){
											String pseudo = in.readUTF();
											System.out.println(" - " + pseudo + "==> " + (i+1));
										}

										int choixExplo = sc.nextInt();
										out.writeInt(choixExplo);
									}
								}
							}
						}
					}
				}

				else if (choixAction == 4){
					if (nbPA >= 2){
						System.out.print("Ramassez(1) ou Deposer(2) un objet ? ==> ");
						int choixA = sc.nextInt();
						out.writeInt(choixA);	


						if (choixA == 1){
							boolean objPresent = in.readBoolean();

							if (objPresent == false)
								System.out.println("Il n'y a pas d'objet a rammassez autour de vous.");

							else{
								System.out.print("Voulez vous rammassez cet objet : ");
								String objet = in.readUTF();
								System.out.println(objet);
								
								System.out.print("Oui(1)/Non(2) : ");
								int choix = sc.nextInt();
								out.writeInt(choix);
							}
						}
						else {
							int equipVide = in.readInt();
							if (equipVide == 0)
								System.out.println("Vous ne possedez pas d'objet");
							else{
							System.out.println("Que voulez vous deposer :");

							String deposer = in.readUTF();
							System.out.println(deposer);

							System.out.println("Choix");
							int objet = sc.nextInt();
							out.writeInt(objet);
							}
						}
					}
					else
						System.out.println("Nombres de PA insuffisant.");
				}

				else if (choixAction == 5){
						System.out.println("Gardons nos forces !");
				}

				else if (choixAction == 6){
					menuPause(out, in, sc);
				}

				else if (choixAction == 7){
					utiliserNiveaux(out, in, sc);
				}

				System.out.println("Continuer (1/0)?");
				int continuer = sc.nextInt();
				out.writeInt(continuer);

				if(continuer == 0)
					break;
			}

			else if (santePJ == 2){
					System.out.println("Vous etes inconscient vous ne pouvez plus effectuer d'action. \n"
										+ "Vous regagner un niveau de blessure toute les minutes");
				}

			else{
				System.out.println("VOUS ETES MORT\nRevenir au menu => 1");
				int continuer = sc.nextInt();
				while (continuer != 1)
					continuer = sc.nextInt();
				out.writeInt(continuer);
			}
		}
	}

	public static void utiliserNiveaux(DataOutputStream out, DataInputStream in, Scanner sc) throws IOException{
		int nivExp = in.readInt();
		int continuer = 1;
		if (nivExp > 0){
			while (continuer == 1){
				nivExp = in.readInt();
				System.out.println("Vous disposez de " + nivExp + " niveau quelle caracteristiques voulez vous ameliorez ?\n"
					+ "1 - Force\n2 - Adresse\n3 - Resistance");
				int choix = sc.nextInt();
				while (choix < 1 || choix > 3){
					choix = sc.nextInt();
				}
				out.writeInt(choix);
				if (nivExp-1 > 0){
					System.out.println("Continuer a ameliorez vos caracteristiques ? (il vous reste " + (nivExp-1) + " niveaux)\n" 
						+ "oui(1)/non(0)");
					continuer = sc.nextInt();
					while (choix != 1 && choix != 0){
						continuer = sc.nextInt();
					}
					out.writeInt(continuer);

				}
				else{
					System.out.println("Vous avez utiliser tout vos niveaux !");
					continuer = 0;
				}
			}
		}
		else
			System.out.println("Vous n'avez pas de niveaux a utiliser");
	}

	public static void choixArmesAtk(DataOutputStream out, DataInputStream in, Scanner sc) throws IOException{
		Boolean armeEquip = in.readBoolean();
		if (armeEquip == true) {
			System.out.println("Quelle arme souhaitez vous utiliser pour votre attaque ?");
			String s = in.readUTF();
			System.out.println("Choix arme : " + s);
			int choix = sc.nextInt();
			if (choix < 1 || choix > 2) {
			 	choix = sc.nextInt();
			} 
			out.writeInt(choix);
		}
		String armeAtk = in.readUTF();
		System.out.println(armeAtk);
	}


	public static void menuPause(DataOutputStream out, DataInputStream in, Scanner sc) throws IOException{
		int nbJoueurCo = in.readInt();
		System.out.println("\n\n      PAUSE");
		System.out.println( "\n \n Il y a " + nbJoueurCo + " joueurs actif dans votre partie ! \n \n");
		System.out.println("- Reprendre la partie (1)");
		System.out.println("- Sauvegardez votre chevalier (2)");
		System.out.println("- Voir les logs du serveur (3)");
		System.out.println("- Rejoindre le tchat (4)");
		System.out.println("\n" + " - Quitter (0) - ");
		System.out.print("\n" + "Que souhaitez vous faire chevalier ? ");
		int choixPause = sc.nextInt();
		if (choixPause < 0 || choixPause > 4)
			choixPause = sc.nextInt();
		out.writeInt(choixPause);
		if (choixPause == 0){
			System.out.print("\n A bientot dans le royaume des ombres !");
		}

		else if (choixPause == 1){
			System.out.print("\n Pas de repos pour les braves ! \n");
		}

		else if (choixPause == 2){
			Boolean confirmation = in.readBoolean();
		}

		else if (choixPause == 3){
			String logs = in.readUTF();
			FenLog fenLogs = new FenLog(logs);
		}

		else if (choixPause == 4){
			connexionTchat(pseudo, 0);
		}
	}

	static class FenLog extends JFrame {
		private static String logs;
		private static JTextArea log;

		public FenLog(String logs) throws IOException{
			this.logs = logs;
			this.initComposants();
			this.centrer(0.4);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		}

		 public void initComposants() throws IOException{
            JPanel panPrincipal = new JPanel();
            panPrincipal.setLayout(new BorderLayout());
            
            this.add(panPrincipal);

            panPrincipal.add(buildPanelLogs());
        }

        public JPanel buildPanelLogs(){
        	JPanel panLogs = new JPanel();
            panLogs.setLayout(new BorderLayout());

            log = new JTextArea(logs);
            log.setEditable(false);
            JScrollPane sP = new JScrollPane(log);
			sP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            sP.setBorder(BorderFactory.createEtchedBorder());

            panLogs.add(sP);

            return panLogs;
        }

        public void centrer(double d) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension dim = tk.getScreenSize();
            int largeur = (int) (d * dim.width);
            int longueur = (int) (d * dim.height);
            this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
        }


	}

	static class FenChat extends JFrame {
		private static DataOutputStream outChat;
		private static DataInputStream inChat;
		private static int nbClients;

		private static String pseudo;
		private static String affichage;
        private static JTextArea discussion;
        private JButton btnEnvoyer;
        private JTextField message;
        private JLabel txt;
        private static JLabel info;
        private boolean nouvMessage;

        private static Timer timer;

        static final int CODE_ENVOYER = 1;

        public FenChat() throws IOException{
        	super("Tchat de WORLDS OF KNIGHTS");
        	this.outChat = new DataOutputStream(chat.getOutputStream());
        	this.inChat = new DataInputStream(chat.getInputStream());
            this.initComposants();
            this.initEcouteurs();
            this.centrer(0.5);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent e){
			    	try{
				        outChat.writeBoolean(false);
				        timer.stop();
				        char confirmation = inChat.readChar();
				        if (confirmation == 'c' || confirmation == ' '){
				        	e.getWindow().dispose();
				        }
				        chat.close();
				    } catch(IOException o){

				    }
			    }
			});

			this.timer = createTimer();
			timer.start();
        }

        public void initComposants() throws IOException{
            JPanel panPrincipal = new JPanel();
            panPrincipal.setLayout(new BorderLayout());
            
            this.add(panPrincipal);

            panPrincipal.add(buildPanelInfo(), BorderLayout.PAGE_START);
            panPrincipal.add(buildPanelDiscussion(), BorderLayout.CENTER);
            panPrincipal.add(buildPanelEnvoyer(), BorderLayout.PAGE_END);
        }


        
        public JPanel buildPanelInfo() throws IOException{
            JPanel panInfo = new JPanel();
            panInfo.setLayout(new BorderLayout());
            this.nbClients = inChat.readInt();

            info = new JLabel("Vous etes actuellements " + nbClients + " connecte sur le tchat");
            panInfo.add(info, BorderLayout.CENTER);
            panInfo.setBorder(BorderFactory.createEtchedBorder());

            return panInfo;
        }
        

        public JPanel buildPanelDiscussion() {
            JPanel panMess = new JPanel();
            panMess.setLayout(new BorderLayout());

            discussion = new JTextArea(15, 40);
            discussion.setText(affichage);
            discussion.setEditable(false);

            JScrollPane sp = new JScrollPane(discussion);
            sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            panMess.add(sp, BorderLayout.CENTER);
            panMess.setBorder(BorderFactory.createEtchedBorder());
            return panMess;
        }

        public JPanel buildPanelEnvoyer() {
            JPanel panEnvoyer = new JPanel();

            txt = new JLabel("Message :");
            panEnvoyer.add(txt);

            message = new JTextField(40);
            panEnvoyer.add(message);

            btnEnvoyer = new JButton("Envoyer");
            panEnvoyer.add(btnEnvoyer);

            panEnvoyer.setBorder(BorderFactory.createEtchedBorder());
            return panEnvoyer;
        }

        public void centrer(double d) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension dim = tk.getScreenSize();
            int largeur = (int) (d * dim.width);
            int longueur = (int) (d * dim.height);
            this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
        }

        public static void refresh(){
        	try {
	        	nbClients = inChat.readInt();
	        	info.setText("Vous etes actuellements " + nbClients + " connecter sur le tchat");
	        	affichage = inChat.readUTF();
	        	discussion.setText(affichage);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
        }

        private static Timer createTimer (){
		    // Création d'une instance de listener 
		    // associée au timer
		    ActionListener action = new ActionListener ()
		    {
		        // Méthode appelée à chaque tic du timer
		        public void actionPerformed (ActionEvent event)
		        {
		          	refresh();
		     	}
		  	};
		      
		    // Création d'un timer qui génère un tic
		    // chaque 500 millième de seconde
		    //return new Timer (600000-(pJ.getInitiative().calculScore()*100000), action);
		    return new Timer(500, action);
		}

        class EcouteurBoutons implements ActionListener {
         
            private int code;
            
            public EcouteurBoutons(int c) {
                this.code = c;
            }
     
            public void actionPerformed(ActionEvent e) {
            	try {
	                switch (code) {
	                case CODE_ENVOYER: {
	                	outChat.writeBoolean(true);
	                	outChat.writeInt(CODE_ENVOYER);
	                    outChat.writeUTF(message.getText());
	                    refresh();
	                    break;
	                }
	                default:
	                    break;
	                }
	            } catch(IOException o){

	            }
            }
        }
        
        public void initEcouteurs() {
            this.btnEnvoyer.addActionListener(new EcouteurBoutons(CODE_ENVOYER));
        }
    }

/*
    static class FenJeux extends JFrame {
    	private static DataOutputStream out;
    	private static DataInputStream in;

    	private static int nbClients;
		private static JTextArea map;

		private static JTextArea player;
		private static JLabel sac;

		private static JLabel deplacer;
		private static JButton deplacerH;
		private static JButton deplacerB;
		private static JButton deplacerG;
		private static JButton deplacerD;

		private static JButton btnAttaquer;
		private static JButton btnUtiliser;
		private static JButton btnRammasser;

		public FenJeux(DataOutputStream out,DataInputStream in) throws IOException{
			super("WORLDS OF KNIGHTS");
			this.out = out;
			this.in = in;
            this.initComposants();
            //this.initEcouteurs();
            this.centrer(0.9);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }


		public void initComposants() throws IOException{
            JPanel panPrincipal = new JPanel();
            panPrincipal.setLayout(new BorderLayout());
            
            this.add(panPrincipal);

            panPrincipal.add(buildPanelMap(), BorderLayout.CENTER);
            panPrincipal.add(buildPanelInfoPJ(), BorderLayout.LINE_START);
            panPrincipal.add(buildPanelDeplacer(), BorderLayout.PAGE_END);
            panPrincipal.add(buildPanelAction(), BorderLayout.SOUTH);
        }

         public void centrer(double d) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension dim = tk.getScreenSize();
            int largeur = (int) (d * dim.width);
            int longueur = (int) (d * dim.height);
            this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
        }

		public JPanel buildPanelMap() throws IOException{
        	JPanel panMap = new JPanel();
        	panMap.setLayout(new BorderLayout());

        	String plat = in.readUTF();
        	map = new JTextArea(plat);

        	Font font = new Font("Consolas", Font.PLAIN, 12);
        	map.setFont(font);
        	map.setEditable(false);

        	System.out.println(map.getText());
        	panMap.add(map);
        	panMap.setBorder(BorderFactory.createEtchedBorder());

        	return panMap;
        }

        public JPanel buildPanelInfoPJ() throws IOException{
        	JPanel panPJ = new JPanel();
        	panPJ.setLayout(new BorderLayout());

        	String pJ = in.readUTF();
        	player = new JTextArea(pJ);
        	player.setEditable(false);

        	panPJ.add(player, BorderLayout.CENTER);

        	panPJ.setBorder(BorderFactory.createEtchedBorder());

        	return panPJ;
        }

        public JPanel buildPanelDeplacer(){
        	JPanel panDeplacer = new JPanel();
        	panDeplacer.setLayout(new BorderLayout());

        	deplacer = new JLabel("Deplacement (1PA)");
        	panDeplacer.add(deplacer , BorderLayout.PAGE_START);

        	deplacerH = new JButton("î");
        	panDeplacer.add(deplacerH, BorderLayout.CENTER);

        	deplacerB = new JButton("!");
        	panDeplacer.add(deplacerB, BorderLayout.CENTER);

        	deplacerG = new JButton("<-");
        	panDeplacer.add(deplacerG, BorderLayout.LINE_START);

        	deplacerD = new JButton("->");
        	panDeplacer.add(deplacerD, BorderLayout.LINE_END);

        	panDeplacer.setBorder(BorderFactory.createEtchedBorder());

        	return panDeplacer;
        }

        public JPanel buildPanelAction(){
        	JPanel panAction = new JPanel();
        	panAction.setLayout(new BorderLayout());

        	btnAttaquer = new JButton("Attaquer");
        	panAction.add(btnAttaquer);

        	btnUtiliser = new JButton("Utiliser");
        	panAction.add(btnUtiliser);

        	btnRammasser = new JButton("Rammassez/Jeter");
        	panAction.add(btnRammasser);

        	panAction.setBorder(BorderFactory.createEtchedBorder());

        	return panAction;

        }
        
        class EcouteurBoutons implements ActionListener {
         
            private int code;
            
            public EcouteurBoutons(int c) {
                this.code = c;
            }
     
            public void actionPerformed(ActionEvent e) {
            	try {
	                switch (code) {
	                case CODE_ENVOYER: {
	                	outChat.writeBoolean(true);
	                	outChat.writeInt(CODE_ENVOYER);
	                    outChat.writeUTF(message.getText());
	                    refresh();
	                    break;
	                }
	                case CODE_REFRESH: {
	                	outChat.writeBoolean(true);
	                	outChat.writeInt(CODE_REFRESH);
	                	refresh();
	                }
	                default:
	                    break;
	                }
	            } catch(IOException o){

	            }
            }
        }
        
        public void initEcouteurs() {
            this.btnEnvoyer.addActionListener(new EcouteurBoutons(CODE_ENVOYER));
            this.btnRefresh.addActionListener(new EcouteurBoutons(CODE_REFRESH));
        }
    }
*/

}
