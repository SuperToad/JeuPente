import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class FenetrePrincipale extends JFrame implements MouseListener, ActionListener
{
	Quadrillage quadrillage;
	boolean tourJoueur;
	int convertionsJ1, convertionsJ2;
	int [][] bernard = new int[21][21];
	FenetrePrincipale(String s)
	{
		super(s);
		setSize(420, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null) ;
		setResizable(false);
		quadrillage = new Quadrillage();
		setContentPane(quadrillage);
		setVisible(true);
		this.addMouseListener(this);
		tourJoueur = true;
		convertionsJ1 = 0;
		convertionsJ2 = 0;
		for (int x = 0; x < 18; x++)
			for (int y = 0; y < 18; y++)
				bernard[x][y] = 0;		
	}
	
	//Méthode appelée lors du clic de souris
	public void mouseClicked(MouseEvent event)
	{
		
		int x=event.getX();
		int y=(event.getY() - 30);
		int caseX = x-(x%20);
		int caseY = y-(y%20);
		caseX /= 20;
		caseY /= 20;
		System.out.println(caseX + ", " +caseY);
		if (caseX !=0 && caseY != 0 && caseX !=20 && caseY != 20)
		{
			if (bernard[caseX][caseY] == 0)
			{
				if(tourJoueur)
				{
					tourJoueur = false;
					bernard[caseX][caseY] = 1;
				}
				else 
				{
					tourJoueur = true;
					bernard[caseX][caseY] = 2;
				}
				// Creation graphique du pion
				Pion pion = new Pion(tourJoueur, caseX*20, caseY*20);
				setLayout(new BorderLayout());
				add(pion, BorderLayout.CENTER);
				setVisible(true);
				// Verification conditions de convertion det de victoire
				checkCase(caseX, caseY);
			}
		}
	}
	
	public void actionPerformed(ActionEvent evenement)
	{
		if(evenement.getActionCommand().equals("menu_quitter"))
			System.out.println("MENU QUITTER");
	}
	
	public void checkCase(int caseX, int caseY)
	{
		int pionAllie, pionEnnemi;
		int convertions;
		if (!tourJoueur)
		{
			pionAllie = 1;
			pionEnnemi = 2;
			convertions = convertionsJ1;
		}
		else
		{
			pionAllie = 2;
			pionEnnemi = 1;
			convertions = convertionsJ2;
		}
		// Un compteur de pion pour chaque "ligne" possible
		int ligneAllieeHor = 1;
		int ligneAllieeVer = 1;
		int ligneAllieeDiag1 = 1;
		int ligneAllieeDiag2 = 1;
		// Chaque Offset designe une case autour de la case de base
		// Exemple : Dans le cas ou la direction vaut 0, on prendra les coordonnees de la case du dessus
		int offsetX =0, offsetY=0;
		for (int dir=0; dir < 8; dir++)
		{
			switch(dir) {
				case 0 : 
					offsetY = -1; offsetX = 0;
					break;
				case 1 : 
					offsetY = -1; offsetX = 1;
					break;
				case 2 : 
					offsetY = 0; offsetX = 1;
					break;
				case 3 : 
					offsetY = 1; offsetX = 1;
					break;
				case 4 : 
					offsetY = 1; offsetX = 0;
					break;
				case 5 : 
					offsetY = 1; offsetX = -1;
					break;
				case 6 : 
					offsetY = 0; offsetX = -1;
					break;
				case 7 : 
					offsetY = -1; offsetX = -1;
					break;
			}
			if (bernard[caseX + offsetX][caseY + offsetY] != 0)
			{
				int nombreAllie = 0;
				int nombreEnnemi = 0;
				int tour = 1;
				// Detection de pions allies
				while (bernard[caseX + offsetX*tour][caseY + offsetY*tour] == pionAllie)
				{
					nombreAllie++;
					tour++;
				}
				tour = 1;
				//Detection de pions ennemis
				while (bernard[caseX + offsetX*tour][caseY + offsetY*tour] == pionEnnemi)
				{
					nombreEnnemi++;
					tour++;
				}
				System.out.println("Dir "+dir+" : Allies : "+nombreAllie+" Ennemis : "+nombreEnnemi);
				// Detection des convertions
				if (nombreEnnemi == 2 && bernard[caseX + offsetX*tour][caseY + offsetY*tour] == pionAllie)
				{
					// Les cases de Bernard sont converties
					System.out.println("CONVERTION !!");
					bernard[caseX + offsetX][caseY + offsetY] = pionAllie;
					bernard[caseX + offsetX*2][caseY + offsetY*2] = pionAllie;
					// On ajoute les nouveau pions au dessus des anciens
					Pion pionNew = new Pion(tourJoueur, (caseX + offsetX*(tour-2))*20, (caseY + offsetY*(tour-2))*20);
					setLayout(new BorderLayout());
					add(pionNew, BorderLayout.CENTER);
					setVisible(true);
					Pion pionNew2 = new Pion(tourJoueur, (caseX + offsetX*(tour-1))*20, (caseY + offsetY*(tour-1))*20);
					//setLayout(new BorderLayout());
					add(pionNew2, BorderLayout.CENTER);
					setVisible(true);
					
					convertions++;
				}
				// On ajoute le nombre de pions trouves selon la ligne ou on se situe
				switch(dir) {
				case 0 : case 4 :
					ligneAllieeVer += nombreAllie;
					break;
				case 1 : case 5 :
					ligneAllieeDiag2 += nombreAllie;
					break;
				case 2 : case 6 :
					ligneAllieeHor += nombreAllie;
					break;
				case 3 : case 7 :
					ligneAllieeDiag1 += nombreAllie;
					break;
				}
				System.out.println("Hor : "+ligneAllieeHor+" Ver : "+ligneAllieeVer);
			}
		}
		// Detection de victoire
		if (ligneAllieeVer >= 5 || ligneAllieeHor >= 5 || ligneAllieeDiag1 >= 5 || ligneAllieeDiag2 >= 5 || convertions >= 5)
		{
			afficherVictoire();
		}
		if (!tourJoueur)
			convertionsJ1 = convertions;
		else
			convertionsJ2 = convertions;
	}
	
	public void afficherVictoire()
	{
		JOptionPane victoire = new JOptionPane();
		if(tourJoueur)
		{
			victoire.showMessageDialog(null, "Victoire du joueur 2 !", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			victoire.showMessageDialog(null, "Victoire du joueur 1 !", "Fin de partie", JOptionPane.INFORMATION_MESSAGE);
		}
	}

  	//Méthode appelée lors du survol de la souris
	public void mouseEntered(MouseEvent event) { }

 	//Méthode appelée lorsque la souris sort de la zone du bouton
	public void mouseExited(MouseEvent event) { }

 	//Méthode appelée lorsque l'on presse le bouton gauche de la souris
	public void mousePressed(MouseEvent event) { }

  	//Méthode appelée lorsque l'on relâche le clic de souris
	public void mouseReleased(MouseEvent event) { }   
	
}
