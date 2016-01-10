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
		System.out.println(caseX/20 + ", " +caseY/20);
		if (bernard[caseX/20][caseY/20] == 0)
		{
			if(tourJoueur)
			{
				tourJoueur = false;
				bernard[caseX/20][caseY/20] = 1;
			}
			else 
			{
				tourJoueur = true;
				bernard[caseX/20][caseY/20] = 2;
			}
			Pion pion = new Pion(tourJoueur, caseX, caseY);
			setContentPane(pion);
			setVisible(true);
			checkCase(caseX/20, caseY/20);
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
				int tour = 0;
				do
				{
					System.out.println("Tour : " + tour);
					if (bernard[caseX + offsetX*tour][caseY + offsetY*tour] == pionAllie)
						nombreAllie++;
					if (bernard[caseX + offsetX*tour][caseY + offsetY*tour] == pionEnnemi)
						nombreEnnemi++;
					tour++;
				}
				while (bernard[caseX + offsetX*tour][caseY + offsetY*tour] != bernard[caseX + offsetX*(tour+1)][caseY + offsetY*(tour+1)]);
				System.out.println("Dir "+dir+" : Allies : "+nombreAllie+" Ennemis : "+nombreEnnemi);
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
