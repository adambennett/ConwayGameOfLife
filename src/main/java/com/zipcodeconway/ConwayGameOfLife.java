package com.zipcodeconway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ConwayGameOfLife {

    private SimpleWindow window;
    private int[][] currentGeneration;
    private int[][] nextGen;

    public ConwayGameOfLife(Integer dimension) {
        window = new SimpleWindow(dimension);
        currentGeneration = createRandomStart(dimension);
        nextGen = new int[0][0];
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        window = new SimpleWindow(dimension);
        currentGeneration = new int[dimension][dimension];
        nextGen = new int[0][0];
        for (int i = 0; i < startmatrix.length; i++) {
            for (int k = 0; k < startmatrix.length; k++) {
                currentGeneration[i][k] = startmatrix[i][k];
            }
        }
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(100);
        int[][] endingWorld = sim.simulate(100);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        int[][] toRet = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int k = 0; k < dimension; k++) {
                toRet[i][k] = ThreadLocalRandom.current().nextInt(0, 2);
            }
        }
        return toRet;
    }

    public int[][] simulate(Integer maxGenerations) {
        int[][] nextGeneration = new int[this.currentGeneration.length][this.currentGeneration.length];
        for (int i = 0; i < maxGenerations; i++) {
            for (int row = 0; row < this.currentGeneration.length; row++) {
                for (int col = 0; col < this.currentGeneration.length; col++) {
                    nextGeneration[row][col] = isAlive(row, col, this.currentGeneration);
                }
            }
            this.window.display(this.currentGeneration, i + 1);
            this.window.sleep(125);
            copyAndZeroOut(nextGeneration, this.currentGeneration);
        }
        return this.currentGeneration;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for(int row = 0; row < current.length; row++) {
            for(int col = 0; col < current.length; col++) {
                current[row][col] = next[row][col];
                next[row][col] = 0;
            }
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {
        ArrayList<Point> points = new ArrayList<>();
        int maxSize = world.length - 1;

        points.add(new Point(row, col+1));
        points.add(new Point(row, col-1));
        points.add(new Point(row+1, col));
        points.add(new Point(row-1, col));
        points.add(new Point(row+1, col+1));
        points.add(new Point(row-1, col-1));
        points.add(new Point(row+1, col-1));
        points.add(new Point(row-1, col+1));

        for (Point point : points) {
            if (point.x > maxSize) {
                point.x = 0;
            } else if (point.x < 0) {
                point.x = maxSize;
            }

            if (point.y > maxSize) {
                point.y = 0;
            } else if (point.y < 0) {
                point.y = maxSize;
            }
        }

        int neighbors = 0;
        for (Point point : points) {
            if (world[point.x][point.y] == 1) {
                neighbors++;
            }
        }

        if (world[row][col] == 1 && neighbors > 1 && neighbors < 4) {
            return 1;
        } else if (world[row][col] == 0 && neighbors == 3) {
            return 1;
        } else {
            return 0;
        }
    }


}
