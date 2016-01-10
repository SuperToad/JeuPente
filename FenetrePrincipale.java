import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FenetrePrincipale extends JFrame implements MouseListener
{
	Quadrillage quadrillage;
	boolean tourJoueur;
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
		if (caseX !=0 && caseY != 0)
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
				setContentPane(pion);
				setVisible(true);
				// Verification conditions de convertion det de victoire
				checkCase(caseX, caseY);
			}
		}
	}
	
	public void checkCase(int caseX, int caseY)
	{
		int pionAllie, pionEnnemi;
		if (!tourJoueur)
		{
			pionAllie = 1;
			pionEnnemi = 2;
		}
		else
		{
			pionAllie = 2;
			pionEnnemi = 1;
		}
		//System.out.println("Allies : "+pionAllie+" Ennemis : "+pionEnnemi);
		int ligneAllieeHor = 1;
		int ligneAllieeVer = 1;
		int ligneAllieeDiag1 = 1;
		int ligneAllieeDiag2 = 1;
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
			//System.out.println("Direction : " + dir);
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
					System.out.println("CONVERTION !!");
					for (int i = 1; i <= 2; i++)
					{
						System.out.println((caseX + offsetX*i) + ", "+(caseY + offsetY*i));
						bernard[caseX + offsetX*i][caseY + offsetY*i] = pionAllie;
						Pion pionNew = new Pion(tourJoueur, (caseY + offsetX*i)*20, (caseY + offsetY*i)*20);
						setContentPane(pionNew);
						setVisible(true);
						
					}
				}
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
				// Detection de victoire
				if (ligneAllieeVer >= 5 || ligneAllieeHor >= 5 || ligneAllieeDiag1 >= 5 || ligneAllieeDiag2 >= 5)
				{
					System.out.println("VICTOIRE !!");
				}
			}
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
