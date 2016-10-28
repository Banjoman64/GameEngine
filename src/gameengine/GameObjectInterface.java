/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameengine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wyatt
 */
public interface GameObjectInterface {
    public void step();
    public void draw(Graphics g);
    
    public String getName();
    public BufferedImage getIcon();
}
