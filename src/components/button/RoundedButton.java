package components.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RoundedButton extends JButton {

    private final int radius = 8; // Radius for rounded corners

    // Constructor with text, action listener, width, height, background color, and text color
    public RoundedButton(String text, ActionListener listener, int width, int height, Color backgroundColor, Color textColor) {
        super(text); // Set the button text
        setFocusPainted(false); // Remove the focus border
        setContentAreaFilled(false); // Don't fill the content area with the default background
        setOpaque(true); // Make the button opaque (so the background color is visible)
        setBackground(backgroundColor); // Set background color (from argument)
        setForeground(textColor); // Set text color (from argument)
        setBorder(new EmptyBorder(10, 20, 10, 20)); // Add padding around the text
        setPreferredSize(new Dimension(width, height)); // Set the width and height of the button
        
        // Add the action listener
        addActionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing for smooth edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the box shadow (simulating the CSS box-shadow: rgba(0, 0, 0, 0.05) 0px 1px 2px 0px)
        g2d.setColor(new Color(0, 0, 0, 12)); // Shadow color with alpha (rgba(0, 0, 0, 0.05))
        g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius); // Shadow offset

        // Set the button's color
        if (getModel().isPressed()) {
            g2d.setColor(new Color(0, 0, 200)); // Darker color when pressed
        } else {
            g2d.setColor(getBackground()); // Normal background color
        }
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius); // Draw rounded corners

        super.paintComponent(g); // Paint the button text
    }
}
