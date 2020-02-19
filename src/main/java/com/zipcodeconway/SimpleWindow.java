package com.zipcodeconway;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleWindow {
    static JPanel panel;
    static JFrame frame;
    private Integer dim = 0;
    ArrayList<Color> colors = new ArrayList<>();

    public SimpleWindow(Integer dimension) {
        this.dim = dimension * 10;
        panel = new JPanel();
        Dimension dim = new Dimension(this.dim, this.dim);
        panel.setPreferredSize(dim);
        frame = new JFrame();
        Integer framesize = (this.dim < 100) ? 100 : this.dim;
        frame.setSize(framesize, framesize);
        Container contentPane = frame.getContentPane();
        contentPane.add(panel);
        frame.setVisible(true);
        colors = new ArrayList<>();
        while (colors.size() < 4) {
            Color toAdd = getRandomColor();
            while (colors.contains(toAdd)) {
                toAdd = getRandomColor();
            }
            colors.add(toAdd);
        }
    }

    public void sleep(Integer millisecs) {
        try {
            Thread.sleep(millisecs);
            Graphics g = panel.getGraphics();
            g.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void display(int[][] array, Integer n) {
        frame.setTitle(String.format("Generation: %6d", n));
        Graphics g = panel.getGraphics();
        int BOX_DIM = 10;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                g.drawRect(i * BOX_DIM, j * BOX_DIM, 10, 10);
                if (array[i][j] == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(i * BOX_DIM, j * BOX_DIM, 10, 10);
                }
                if (array[i][j] == 1) {
                    g.setColor(getDisplayColor());
                    g.fillRect(i * BOX_DIM, j * BOX_DIM, 10, 10);
                }
            }
        }

    }

    private Color getDisplayColor() {
        if (colors.size() > 3) {
            int roll = ThreadLocalRandom.current().nextInt(0, 4);
            switch (roll) {
                case 0:
                    return new Color(85, 13, 140);
                case 1:
                    return colors.get(0);
                case 2:
                    return colors.get(1);
                case 3:
                    return colors.get(2);
                default:
                    return colors.get(3);
            }
        } else {
            return getRandomColor();
        }
    }

    private Color getRandomColor() {
       int roll = ThreadLocalRandom.current().nextInt(0, 10);
        switch (roll) {
            case 0:
                return new Color(85, 13, 140);
            case 1:
                return Color.BLACK;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.RED;
            case 5:
                return Color.CYAN;
            case 6:
                return Color.ORANGE;
            case 7:
                return Color.GRAY;
            case 8:
                return Color.MAGENTA;
            case 9:
                return Color.PINK;
            default:
                return Color.BLUE;
        }
    }
}
