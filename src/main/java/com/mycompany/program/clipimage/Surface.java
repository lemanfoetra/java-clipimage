/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.program.clipimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ardyn Sulaeman
 */
class Surface extends JPanel 
        implements ActionListener {

    private Timer timer;
    private double rotate = 1;
    private int pos_x = 8;
    private int pos_y = 8;
    private final double delta[] = {1, 1};
    
    private final int RADIUS = 60;
    

    public Surface() {

        initTimer();
    }

    private void initTimer() {

        timer = new Timer(10, this);
        timer.start();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        Shape oldClip = g2d.getClip();

        int w = getWidth();
        int h = getHeight();

        Rectangle rect = new Rectangle(0, 0, 200, 80);

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(rotate), w / 2, h / 2);
        tx.translate(w / 2 - 100, h / 2 - 40);

        Ellipse2D circle = new Ellipse2D.Double(pos_x, pos_y,
                RADIUS, RADIUS);

        GeneralPath path = new GeneralPath();
        path.append(tx.createTransformedShape(rect), false);

        g2d.clip(circle);
        g2d.clip(path);
        
        g2d.setPaint(new Color(110, 110, 110));
        g2d.fill(circle);

        g2d.setClip(oldClip);

        g2d.draw(circle);
        g2d.draw(path);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    public void step() {

        int w = getWidth();
        int h = getHeight();
        
        rotate += 1;

        if (pos_x < 0) {

            delta[0] = 1;
        } else if (pos_x > w - RADIUS) {

            delta[0] = -1;
        }

        if (pos_y < 0) {

            delta[1] = 1;
        } else if (pos_y > h - RADIUS) {

            delta[1] = -1;
        }

        pos_x += delta[0];
        pos_y += delta[1];
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        step();
        repaint();
    }
}