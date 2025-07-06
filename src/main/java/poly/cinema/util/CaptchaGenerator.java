/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cinema.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Bao Nhien
 */
public class CaptchaGenerator {
       private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateText(int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return text.toString();
    }

    public static BufferedImage generateImage(String text) {
        BufferedImage img = new BufferedImage(120, 40, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // Nền
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 120, 40);

        // Viền + chữ
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString(text, 15, 28);

        // Nhiễu
        Random rand = new Random();
        for (int i = 0; i < 12; i++) {
            int x1 = rand.nextInt(120);
            int y1 = rand.nextInt(40);
            int x2 = rand.nextInt(120);
            int y2 = rand.nextInt(40);
            g.drawLine(x1, y1, x2, y2);
        }

        g.dispose();
        return img;
    }
}

