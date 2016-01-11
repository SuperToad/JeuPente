import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;

public class Quadrillage extends JPanel 
{
	private Image img;
	Quadrillage()
	{
		try
		{
			img = ImageIO.read(new File("croix.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g)
	{
		for(int i=1; i<=19; i++)
		{
			for(int y=1; y<=19; y++)
			{
				g.drawImage(img, i*20, y*20, null);
			}
		}
		char lettre = 'A';
		for (int j = 20; j<400; j+=20)
		{
			int chiffre = j/20;
			String string = String.valueOf(chiffre);
			g.drawString(string, 2, j+15);
			g.drawString(Character.toString(lettre), j+6, 15);
			lettre++;
		}
	}
}
