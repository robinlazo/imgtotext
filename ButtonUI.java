package pixelArt;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class ButtonUI extends BasicButtonUI {

	public void installUI(JComponent j) {
		super.installUI(j);
		j.setBorder(new EmptyBorder(5, 0, 5, 0));
		j.setForeground(new Color(0x2c3e50));
		j.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public void paint(Graphics g, JComponent j) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		
		Dimension size = j.getSize();
		j.setBackground(new Color(0x00cec9));
		g2d.fillRect(0, 0, size.width, size.height);
		g2d.setColor(new Color(0x3498db));
		g2d.drawRect(1, 2, size.width - 2, size.height - 3);
		super.paint(g, j);
	}
}
