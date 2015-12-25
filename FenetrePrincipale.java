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
	int [][] bernard = new int[20][20];
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
			if(tourJoueur == true)
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
