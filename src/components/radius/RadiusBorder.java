package components.radius;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

import constant.style.ColorsApp;

public class RadiusBorder extends AbstractBorder {
    private final int radius;

    public RadiusBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(ColorsApp.BORDER); 
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius); // Draw only the border outline with rounded corners
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 5, 5, 5); 
    }
}