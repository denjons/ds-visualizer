/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.tsp;

import com.dennisjonsson.visualization.test.*;
import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.markup.AbstractType;
import java.util.Random;


public class AntTspVisual{
public static com.dennisjonsson.log.ast.ASTLogger logger = 
com.dennisjonsson.log.ast.ASTLogger.instance(
new com.dennisjonsson.log.ast.SourceHeader(
"AntTspVisual",
"",
new com.dennisjonsson.markup.DataStructure [] {  com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","int[]","tour"),com.dennisjonsson.markup.DataStructureFactory.getDataStructure("array","boolean[]","visited"),com.dennisjonsson.markup.DataStructureFactory.getDataStructure("adjacencymatrix","double[][]","graph"),com.dennisjonsson.markup.DataStructureFactory.getDataStructure("adjacencymatrix","double[][]","trails")},
com.dennisjonsson.log.DefaultInterpreter.instance()));

    // Algorithm parameters:
    // original amount of trail
    private double c = 1.0;

    // trail preference
    private double alpha = 1;

    // greedy preference
    private double beta = 5;

    // trail evaporation coefficient
    private double evaporation = 0.5;

    // new trail deposit coefficient;
    private double Q = 500;

    // number of ants used = numAntFactor*numTowns
    private double numAntFactor = 0.8;

    // probability of pure random selection of the next town
    private double pr = 0.01;

    // Reasonable number of iterations
    // - results typically settle down by 500
    private int maxIterations = 5;

    int size = 10;

    // # towns
    public int n = 0;

    // # ants
    public int m = 0;

    
    private double graph[][];

    
    private double trails[][];

    private Ant ants[] = null;

    private Random rand = new Random();

    private double probs[] = null;

    private int currentIndex = 0;

    public int[] bestTour;

    public double bestTourLength;

    // Ant class. Maintains tour and tabu information.
    private class Ant {

        
        public int tour[] = eval("tour", write(null, new int[graph.length], 3, 1), 0);

        // Maintain visited list for towns, much faster
        // than checking if in tour so far.
        
        public boolean visited[] = eval("visited", write(null, new boolean[graph.length], 3, 1), 0);

        public void visitTown(int town) {
            eval("tour[currentIndex + 1]", tour[read("tour", 0, currentIndex + 1)] = write("town", town, 1, 0), 0);
            eval("visited[town]", visited[read("visited", 0, town)] = write(null, true, 3, 0), 0);
        }

        public boolean visited(int i) {
            return visited[i];
        }

        public double tourLength() {
            double length = eval("length", write("graph", graph[read("graph", 0, tour[read("tour", 0, n - 1)])][read("graph", 1, tour[read("tour", 0, 0)])], 0, 1), 0);
            for (int i = 0; i < n - 1; i++) {
                eval("length", length += write("graph", graph[read("graph", 0, tour[read("tour", 0, i)])][read("graph", 1, tour[read("tour", 0, i + 1)])], 0, 1), 0);
            }
            return length;
        }

        public void clear() {
            for (int i = 0; i < n; i++) eval("visited[i]", visited[read("visited", 0, i)] = write(null, false, 3, 0), 0);
        }
    }

    // Read in graph from a file.
    // Allocates all memory.
    // Adds 1 to edge lengths to ensure no zero length edges.
    public void readGraph(String path) {
        eval("graph", graph = write(null, new double[size][size], 3, 1), 0);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                eval("graph[i][j]", graph[read("graph", 0, i)][read("graph", 1, j)] = write(null, (size - 1) * Math.random(), 3, 0), 0);
            }
        }
        n = graph.length;
        m = (int) (n * numAntFactor);
        // all memory allocations done here
        eval("trails", trails = write(null, new double[n][n], 3, 1), 0);
        probs = new double[n];
        ants = new Ant[m];
        for (int j = 0; j < m; j++) ants[j] = new Ant();
    }

    // Approximate power function, Math.pow is quite slow and we don't need accuracy.
    // See: 
    // http://martin.ankerl.com/2007/10/04/optimized-pow-approximation-for-java-and-c-c/
    // Important facts:
    // - >25 times faster
    // - Extreme cases can lead to error of 25% - but usually less.
    // - Does not harm results -- not surprising for a stochastic algorithm.
    public static double pow(final double a, final double b) {
        final int x = (int) (Double.doubleToLongBits(a) >> 32);
        final int y = (int) (b * (x - 1072632447) + 1072632447);
        return Double.longBitsToDouble(((long) y) << 32);
    }

    // Store in probs array the probability of moving to each town
    // [1] describes how these are calculated.
    // In short: ants like to follow stronger and shorter trails more.
    private void probTo(Ant ant) {
        int i = ant.tour[currentIndex];
        double denom = 0.0;
        for (int l = 0; l < n; l++) if (!ant.visited(l))
            denom += pow(eval(null, trails[read("trails", 0, i)][read("trails", 1, l)], 2), alpha) * pow(1.0 / eval(null, graph[read("graph", 0, i)][read("graph", 1, l)], 2), beta);
        for (int j = 0; j < n; j++) {
            if (ant.visited(j)) {
                probs[j] = 0.0;
            } else {
                double numerator = pow(eval(null, trails[read("trails", 0, i)][read("trails", 1, j)], 2), alpha) * pow(1.0 / eval(null, graph[read("graph", 0, i)][read("graph", 1, j)], 2), beta);
                probs[j] = numerator / denom;
            }
        }
    }

    // Given an ant select the next town based on the probabilities
    // we assign to each town. With pr probability chooses
    // totally randomly (taking into account tabu list).
    private int selectNextTown(Ant ant) {
        // sometimes just randomly select
        if (rand.nextDouble() < pr) {
            // random town
            int t = rand.nextInt(n - currentIndex);
            int j = -1;
            for (int i = 0; i < n; i++) {
                if (!ant.visited(i))
                    j++;
                if (j == t)
                    return i;
            }
        }
        // calculate probabilities for each town (stored in probs)
        probTo(ant);
        // randomly select according to probs
        double r = rand.nextDouble();
        double tot = 0;
        for (int i = 0; i < n; i++) {
            tot += probs[i];
            if (tot >= r)
                return i;
        }
        throw new RuntimeException("Not supposed to get here.");
    }

    // Update trails based on ants tours
    private void updateTrails() {
        // evaporation
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) eval("trails[i][j]", trails[read("trails", 0, i)][read("trails", 1, j)] *= write("evaporation", evaporation, 1, 0), 0);
        // each ants contribution
        for (Ant a : ants) {
            double contribution = Q / a.tourLength();
            for (int i = 0; i < n - 1; i++) {
                eval("trails[a.tour[i]][a.tour[i + 1]]", trails[read("trails", 0, a.tour[i])][read("trails", 1, a.tour[i + 1])] += write("contribution", contribution, 1, 0), 0);
            }
            eval("trails[a.tour[n - 1]][a.tour[0]]", trails[read("trails", 0, a.tour[n - 1])][read("trails", 1, a.tour[0])] += write("contribution", contribution, 1, 0), 0);
        }
    }

    // Choose the next town for all ants
    private void moveAnts() {
        // each ant follows trails...
        while (currentIndex < n - 1) {
            for (Ant a : ants) a.visitTown(selectNextTown(a));
            currentIndex++;
        }
    }

    // m ants with random start city
    private void setupAnts() {
        currentIndex = -1;
        for (int i = 0; i < m; i++) {
            // faster than fresh allocations.
            ants[i].clear();
            ants[i].visitTown(rand.nextInt(n));
        }
        currentIndex++;
    }

    private void updateBest() {
        if (bestTour == null) {
            bestTour = ants[0].tour;
            bestTourLength = ants[0].tourLength();
        }
        for (Ant a : ants) {
            if (a.tourLength() < bestTourLength) {
                bestTourLength = a.tourLength();
                bestTour = a.tour.clone();
            }
        }
    }

    public static String tourToString(int tour[]) {
        String t = new String();
        for (int i : tour) t = t + " " + i;
        return t;
    }

    public int[] solve() {
        // clear trails
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) eval("trails[i][j]", trails[read("trails", 0, i)][read("trails", 1, j)] = write("c", c, 1, 0), 0);
        int iteration = 0;
        // preserve best tour
        while (iteration < maxIterations) {
            setupAnts();
            moveAnts();
            updateTrails();
            updateBest();
            iteration++;
        }
        // Subtract n because we added one to edges on load
        System.out.println("Best tour length: " + (bestTourLength - n));
        System.out.println("Best tour:" + tourToString(bestTour));
        return bestTour.clone();
    }

    // Load graph file given on args[0].
    // (Full adjacency matrix. Columns separated by spaces, rows by newlines.)
    // Solve the TSP repeatedly for maxIterations
    // printing best tour so far each time. 
    public static void main(String[] args) {
        // Load in TSP data file.
        AntTspVisual anttsp = new AntTspVisual();
        anttsp.readGraph("");
        // Repeatedly solve - will keep the best tour found.
        anttsp.solve();
        print();
    }

    
    public static void print() {
        logger.print();
    }

public static int eval( String targetId, int value, int expressionType){
logger.eval("AntTspVisual", targetId, value, expressionType);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType){
logger.eval("AntTspVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}

public static boolean eval( String targetId, boolean value, int expressionType){
logger.eval("AntTspVisual", targetId, value, expressionType);
return value;
}
public static boolean write(String name, boolean value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, value, sourceType, targetType);
return value;
}
public static boolean[] eval( String targetId, boolean[] value, int expressionType){
logger.eval("AntTspVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static boolean[] write(String name, boolean[] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static boolean[][] eval( String targetId, boolean[][] value, int expressionType){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<boolean[][]>().deepCopy(value), expressionType);
return value;
}
public static boolean[][] write(String name, boolean[][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.log.ast.LogUtils<boolean[][]>().deepCopy(value), sourceType, targetType);
return value;
}

public static double eval( String targetId, double value, int expressionType){
logger.eval("AntTspVisual", targetId, value, expressionType);
return value;
}
public static double write(String name, double value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, value, sourceType, targetType);
return value;
}
public static double[] eval( String targetId, double[] value, int expressionType){
logger.eval("AntTspVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType);
return value;
}
public static double[] write(String name, double[] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static double[][] eval( String targetId, double[][] value, int expressionType){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<double[][]>().deepCopy(value), expressionType);
return value;
}
public static double[][] write(String name, double[][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.log.ast.LogUtils<double[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static double[][][] eval( String targetId, double[][][] value, int expressionType){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.log.ast.LogUtils<double[][][]>().deepCopy(value), expressionType);
return value;
}
public static double[][][] write(String name, double[][][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.log.ast.LogUtils<double[][][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index){ 
logger.read("AntTspVisual", name ,index ,dimension);
return index; 
}
}
