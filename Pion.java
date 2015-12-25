import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;

public class Pion extends JPanel
{
	private Image imgPion;
	private int xPion;
	private int yPion;
	boolean couleur;
	Pion(boolean couleur, int caseX, int caseY)
	{
		xPion = caseX;
		yPion = caseY;
		if(couleur == true)
		{
			try
			{
				imgPion = ImageIO.read(new File("jaune.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				imgPion = ImageIO.read(new File("rouge.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(imgPion, xPion, yPion, null);
	}
}
