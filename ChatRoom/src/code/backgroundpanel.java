package code;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class backgroundpanel extends JPanel{
    Image im;

    public backgroundpanel(Image im){
        this.im=im;
        this.setOpaque(true);
    }

    public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);
		
	}
}