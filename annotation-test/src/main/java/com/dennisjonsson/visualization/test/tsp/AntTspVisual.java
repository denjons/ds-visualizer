/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dennisjonsson.visualization.test.tsp;

import com.dennisjonsson.visualization.test.*;
import com.dennisjonsson.annotation.Print;
import com.dennisjonsson.annotation.SourcePath;
import com.dennisjonsson.annotation.VisualClass;
import com.dennisjonsson.annotation.Visualize;
import com.dennisjonsson.annotation.markup.AbstractType;
import java.util.Random;

/*
 *  === Implementation of ant swarm TSP solver. ===
 *  
 * The algorithm is described in [1, page 8].
 * 
 * == Tweaks/notes == 
 *  - I added a system where the ant chooses with probability
 *    "pr" to go to a purely random town. This did not yield better
 * results so I left "pr" fairly low.
 *  - Used an approximate pow function - the speedup is
 *    more than a factor of 10! And accuracy is not needed
 *    See AntTsp.pow for details.
 *  
 * == Parameters ==
 * I set the parameters to values suggested in [1]. My own experimentation
 * showed that they are pretty good.
 * 
 * == Usage ==
 * - Compile: javac AntTsp.java
 * - Run: java AntTspVisual <TSP file>
 * 
 * == TSP file format ==
 * Full adjacency matrix. Columns separated by spaces, rows by newline.
 * Weights parsed as doubles, must be >= 0.
 * 
 * == References == 
 * [1] M. Dorigo, The Ant System: Optimization by a colony of cooperating agents
 * ftp://iridia.ulb.ac.be/pub/mdorigo/journals/IJ.10-SMC96.pdf
 * 
 */

public class AntTspVisual{
public static com.dennisjonsson.annotation.log.ast.ASTLogger logger = 
com.dennisjonsson.annotation.log.ast.ASTLogger.instance(new com.dennisjonsson.annotation.log.ast.SourceHeader("AntTspVisual",new String [] { "/*"," * To change this license header, choose License Headers in Project Properties."," * To change this template file, choose Tools | Templates"," * and open the template in the editor."," */","package com.dennisjonsson.visualization.test.tsp;","","import com.dennisjonsson.visualization.test.*;","import com.dennisjonsson.annotation.Print;","import com.dennisjonsson.annotation.SourcePath;","import com.dennisjonsson.annotation.VisualClass;","import com.dennisjonsson.annotation.Visualize;","import com.dennisjonsson.annotation.markup.AbstractType;","import java.util.Random;","","/*"," *  === Implementation of ant swarm TSP solver. ==="," *  "," * The algorithm is described in [1, page 8]."," * "," * == Tweaks/notes == "," *  - I added a system where the ant chooses with probability"," *    'pr' to go to a purely random town. This did not yield better"," * results so I left 'pr' fairly low."," *  - Used an approximate pow function - the speedup is"," *    more than a factor of 10! And accuracy is not needed"," *    See AntTsp.pow for details."," *  "," * == Parameters =="," * I set the parameters to values suggested in [1]. My own experimentation"," * showed that they are pretty good."," * "," * == Usage =="," * - Compile: javac AntTsp.java"," * - Run: java AntTsp <TSP file>"," * "," * == TSP file format =="," * Full adjacency matrix. Columns separated by spaces, rows by newline."," * Weights parsed as doubles, must be >= 0."," * "," * == References == "," * [1] M. Dorigo, The Ant System: Optimization by a colony of cooperating agents"," * ftp://iridia.ulb.ac.be/pub/mdorigo/journals/IJ.10-SMC96.pdf"," * "," */","@VisualClass","public class AntTsp {","","    // Algorithm parameters:","    // original amount of trail","    private double c = 1.0;","","    // trail preference","    private double alpha = 1;","","    // greedy preference","    private double beta = 5;","","    // trail evaporation coefficient","    private double evaporation = 0.5;","","    // new trail deposit coefficient;","    private double Q = 500;","","    // number of ants used = numAntFactor*numTowns","    private double numAntFactor = 0.8;","","    // probability of pure random selection of the next town","    private double pr = 0.01;","","    // Reasonable number of iterations","    // - results typically settle down by 500","    private int maxIterations = 10;","","    int size = 10;","","    // # towns","    public int n = 0;","","    // # ants","    public int m = 0;","","    @Visualize(abstractType = 'matrix')","    private double graph[][];","","    @Visualize(abstractType = 'matrix')","    private double trails[][];","","    private Ant ants[] = null;","","    private Random rand = new Random();","","    private double probs[] = null;","","    private int currentIndex = 0;","","    public int[] bestTour;","","    public double bestTourLength;","","    // Ant class. Maintains tour and tabu information.","    private class Ant {","","        @Visualize(abstractType = AbstractType.ARRAY)","        public int tour[] = new int[graph.length];","","        // Maintain visited list for towns, much faster","        // than checking if in tour so far.","        @Visualize(abstractType = AbstractType.ARRAY)","        public boolean visited[] = new boolean[graph.length];","","        public void visitTown(int town) {","            tour[currentIndex + 1] = town;","            visited[town] = true;","        }","","        public boolean visited(int i) {","            return visited[i];","        }","","        public double tourLength() {","            double length = graph[tour[n - 1]][tour[0]];","            for (int i = 0; i < n - 1; i++) {","                length += graph[tour[i]][tour[i + 1]];","            }","            return length;","        }","","        public void clear() {","            for (int i = 0; i < n; i++) visited[i] = false;","        }","    }","","    // Read in graph from a file.","    // Allocates all memory.","    // Adds 1 to edge lengths to ensure no zero length edges.","    public void readGraph(String path) {","        graph = new double[size][size];","        for (int i = 0; i < size; i++) {","            for (int j = 0; j < size; j++) {","                graph[i][j] = (size - 1) * Math.random();","            }","        }","        n = graph.length;","        m = (int) (n * numAntFactor);","        // all memory allocations done here","        trails = new double[n][n];","        probs = new double[n];","        ants = new Ant[m];","        for (int j = 0; j < m; j++) ants[j] = new Ant();","    }","","    // Approximate power function, Math.pow is quite slow and we don't need accuracy.","    // See: ","    // http://martin.ankerl.com/2007/10/04/optimized-pow-approximation-for-java-and-c-c/","    // Important facts:","    // - >25 times faster","    // - Extreme cases can lead to error of 25% - but usually less.","    // - Does not harm results -- not surprising for a stochastic algorithm.","    public static double pow(final double a, final double b) {","        final int x = (int) (Double.doubleToLongBits(a) >> 32);","        final int y = (int) (b * (x - 1072632447) + 1072632447);","        return Double.longBitsToDouble(((long) y) << 32);","    }","","    // Store in probs array the probability of moving to each town","    // [1] describes how these are calculated.","    // In short: ants like to follow stronger and shorter trails more.","    private void probTo(Ant ant) {","        int i = ant.tour[currentIndex];","        double denom = 0.0;","        for (int l = 0; l < n; l++) if (!ant.visited(l))","            denom += pow(trails[i][l], alpha) * pow(1.0 / graph[i][l], beta);","        for (int j = 0; j < n; j++) {","            if (ant.visited(j)) {","                probs[j] = 0.0;","            } else {","                double numerator = pow(trails[i][j], alpha) * pow(1.0 / graph[i][j], beta);","                probs[j] = numerator / denom;","            }","        }","    }","","    // Given an ant select the next town based on the probabilities","    // we assign to each town. With pr probability chooses","    // totally randomly (taking into account tabu list).","    private int selectNextTown(Ant ant) {","        // sometimes just randomly select","        if (rand.nextDouble() < pr) {","            // random town","            int t = rand.nextInt(n - currentIndex);","            int j = -1;","            for (int i = 0; i < n; i++) {","                if (!ant.visited(i))","                    j++;","                if (j == t)","                    return i;","            }","        }","        // calculate probabilities for each town (stored in probs)","        probTo(ant);","        // randomly select according to probs","        double r = rand.nextDouble();","        double tot = 0;","        for (int i = 0; i < n; i++) {","            tot += probs[i];","            if (tot >= r)","                return i;","        }","        throw new RuntimeException('Not supposed to get here.');","    }","","    // Update trails based on ants tours","    private void updateTrails() {","        // evaporation","        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) trails[i][j] *= evaporation;","        // each ants contribution","        for (Ant a : ants) {","            double contribution = Q / a.tourLength();","            for (int i = 0; i < n - 1; i++) {","                trails[a.tour[i]][a.tour[i + 1]] += contribution;","            }","            trails[a.tour[n - 1]][a.tour[0]] += contribution;","        }","    }","","    // Choose the next town for all ants","    private void moveAnts() {","        // each ant follows trails...","        while (currentIndex < n - 1) {","            for (Ant a : ants) a.visitTown(selectNextTown(a));","            currentIndex++;","        }","    }","","    // m ants with random start city","    private void setupAnts() {","        currentIndex = -1;","        for (int i = 0; i < m; i++) {","            // faster than fresh allocations.","            ants[i].clear();","            ants[i].visitTown(rand.nextInt(n));","        }","        currentIndex++;","    }","","    private void updateBest() {","        if (bestTour == null) {","            bestTour = ants[0].tour;","            bestTourLength = ants[0].tourLength();","        }","        for (Ant a : ants) {","            if (a.tourLength() < bestTourLength) {","                bestTourLength = a.tourLength();","                bestTour = a.tour.clone();","            }","        }","    }","","    public static String tourToString(int tour[]) {","        String t = new String();","        for (int i : tour) t = t + ' ' + i;","        return t;","    }","","    public int[] solve() {","        // clear trails","        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) trails[i][j] = c;","        int iteration = 0;","        // preserve best tour","        while (iteration < maxIterations) {","            setupAnts();","            moveAnts();","            updateTrails();","            updateBest();","            iteration++;","        }","        // Subtract n because we added one to edges on load","        System.out.println('Best tour length: ' + (bestTourLength - n));","        System.out.println('Best tour:' + tourToString(bestTour));","        return bestTour.clone();","    }","","    // Load graph file given on args[0].","    // (Full adjacency matrix. Columns separated by spaces, rows by newlines.)","    // Solve the TSP repeatedly for maxIterations","    // printing best tour so far each time. ","    public static void main(String[] args) {","        // Load in TSP data file.","        AntTsp anttsp = new AntTsp();","        anttsp.readGraph('');","        // Repeatedly solve - will keep the best tour found.","        anttsp.solve();","    }","}"},"",new com.dennisjonsson.annotation.markup.DataStructure [] {  com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","int[]","com.dennisjonsson.visualization.test.tsp.AntTsp tour"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("array","boolean[]","com.dennisjonsson.visualization.test.tsp.AntTsp visited"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("matrix","double[][]","com.dennisjonsson.visualization.test.tsp.AntTsp graph"),com.dennisjonsson.annotation.markup.DataStructureFactory.getDataStructure("matrix","double[][]","com.dennisjonsson.visualization.test.tsp.AntTsp trails")},new com.dennisjonsson.visualization.test.app.MyInterpreter(),"C:.Users.dennis.Documents.NetBeansProjects.annotation-test"));

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
    private int maxIterations = 10;

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

        
        public int tour[] = eval("com.dennisjonsson.visualization.test.tsp.AntTsp tour", write(null, new int[graph.length], 3, 1), 0, new int[] { 89, 89 });

        // Maintain visited list for towns, much faster
        // than checking if in tour so far.
        
        public boolean visited[] = eval("com.dennisjonsson.visualization.test.tsp.AntTsp visited", write(null, new boolean[graph.length], 3, 1), 0, new int[] { 93, 93 });

        public void visitTown(int town) {
            eval("com.dennisjonsson.visualization.test.tsp.AntTsp tour", tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, currentIndex + 1)] = write("town", town, 1, 0), 0, new int[] { 96, 96 });
            eval("com.dennisjonsson.visualization.test.tsp.AntTsp visited", visited[read("com.dennisjonsson.visualization.test.tsp.AntTsp visited", 0, town)] = write(null, true, 3, 0), 0, new int[] { 97, 97 });
        }

        public boolean visited(int i) {
            return eval(null, visited[read("com.dennisjonsson.visualization.test.tsp.AntTsp visited", 0, i)], 2, new int[] { 101, 101 });
        }

        public double tourLength() {
            double length = eval("length", write("com.dennisjonsson.visualization.test.tsp.AntTsp graph", graph[read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 0, tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, n - 1)])][read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 1, tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, 0)])], 0, 1), 0, new int[] { 105, 105 });
            for (int i = 0; i < n - 1; i++) {
                eval("length", length += write("com.dennisjonsson.visualization.test.tsp.AntTsp graph", graph[read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 0, tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, i)])][read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 1, tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, i + 1)])], 0, 1), 0, new int[] { 107, 107 });
            }
            return length;
        }

        public void clear() {
            for (int i = 0; i < n; i++) eval("com.dennisjonsson.visualization.test.tsp.AntTsp visited", visited[read("com.dennisjonsson.visualization.test.tsp.AntTsp visited", 0, i)] = write(null, false, 3, 0), 0, new int[] { 114, 114 });
        }
    }

    // Read in graph from a file.
    // Allocates all memory.
    // Adds 1 to edge lengths to ensure no zero length edges.
    public void readGraph(String path) {
        eval("com.dennisjonsson.visualization.test.tsp.AntTsp graph", graph = write(null, new double[size][size], 3, 1), 0, new int[] { 125, 125 });
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                eval("com.dennisjonsson.visualization.test.tsp.AntTsp graph", graph[read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 0, i)][read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 1, j)] = write(null, (size - 1) * Math.random(), 3, 0), 0, new int[] { 129, 129 });
            }
        }
        n = graph.length;
        m = (int) (n * numAntFactor);
        // all memory allocations done here
        eval("com.dennisjonsson.visualization.test.tsp.AntTsp trails", trails = write(null, new double[n][n], 3, 1), 0, new int[] { 138, 138 });
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
        int i = eval("i", write("com.dennisjonsson.visualization.test.tsp.AntTsp tour", ant.tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, currentIndex)], 0, 1), 0, new int[] { 162, 162 });
        double denom = 0.0;
        for (int l = 0; l < n; l++) if (!ant.visited(l))
            denom += pow(eval(null, trails[read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 0, i)], 2, new int[] { 167, 167 })[l], alpha) * pow(1.0 / eval(null, graph[read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 0, i)], 2, new int[] { 168, 168 })[l], beta);
        for (int j = 0; j < n; j++) {
            if (ant.visited(j)) {
                probs[j] = 0.0;
            } else {
                double numerator = pow(eval(null, trails[read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 0, i)], 2, new int[] { 175, 175 })[j], alpha) * pow(1.0 / eval(null, graph[read("com.dennisjonsson.visualization.test.tsp.AntTsp graph", 0, i)], 2, new int[] { 176, 176 })[j], beta);
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
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) eval("com.dennisjonsson.visualization.test.tsp.AntTsp trails", trails[read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 0, i)][read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 1, j)] *= write("evaporation", evaporation, 1, 0), 0, new int[] { 218, 218 });
        // each ants contribution
        for (Ant a : ants) {
            double contribution = Q / a.tourLength();
            for (int i = 0; i < n - 1; i++) {
                eval("com.dennisjonsson.visualization.test.tsp.AntTsp trails", trails[read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 0, a.tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, i)])][read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 1, a.tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, i + 1)])] += write("contribution", contribution, 1, 0), 0, new int[] { 224, 224 });
            }
            eval("com.dennisjonsson.visualization.test.tsp.AntTsp trails", trails[read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 0, a.tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, n - 1)])][read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 1, a.tour[read("com.dennisjonsson.visualization.test.tsp.AntTsp tour", 0, 0)])] += write("contribution", contribution, 1, 0), 0, new int[] { 226, 226 });
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
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) eval("com.dennisjonsson.visualization.test.tsp.AntTsp trails", trails[read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 0, i)][read("com.dennisjonsson.visualization.test.tsp.AntTsp trails", 1, j)] = write("c", c, 1, 0), 0, new int[] { 275, 275 });
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
    }

public static int eval( String targetId, int value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, value, expressionType, line);
return value;
}
public static int write(String name, int value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, value, sourceType, targetType);
return value;
}
public static int[] eval( String targetId, int[] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static int[] write(String name, int[] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static int[][] eval( String targetId, int[][] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), expressionType, line);
return value;
}
public static int[][] write(String name, int[][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<int[][]>().deepCopy(value), sourceType, targetType);
return value;
}

public static boolean eval( String targetId, boolean value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, value, expressionType, line);
return value;
}
public static boolean write(String name, boolean value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, value, sourceType, targetType);
return value;
}
public static boolean[] eval( String targetId, boolean[] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static boolean[] write(String name, boolean[] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static boolean[][] eval( String targetId, boolean[][] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<boolean[][]>().deepCopy(value), expressionType, line);
return value;
}
public static boolean[][] write(String name, boolean[][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<boolean[][]>().deepCopy(value), sourceType, targetType);
return value;
}

public static double eval( String targetId, double value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, value, expressionType, line);
return value;
}
public static double write(String name, double value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, value, sourceType, targetType);
return value;
}
public static double[] eval( String targetId, double[] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, java.util.Arrays.copyOf(value,value.length), expressionType, line);
return value;
}
public static double[] write(String name, double[] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, java.util.Arrays.copyOf(value,value.length), sourceType, targetType);
return value;
}
public static double[][] eval( String targetId, double[][] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<double[][]>().deepCopy(value), expressionType, line);
return value;
}
public static double[][] write(String name, double[][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<double[][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static double[][][] eval( String targetId, double[][][] value, int expressionType, int [] line){
logger.eval("AntTspVisual", targetId, new com.dennisjonsson.annotation.log.ast.LogUtils<double[][][]>().deepCopy(value), expressionType, line);
return value;
}
public static double[][][] write(String name, double[][][] value, int sourceType, int targetType ){
logger.write("AntTspVisual", name, new com.dennisjonsson.annotation.log.ast.LogUtils<double[][][]>().deepCopy(value), sourceType, targetType);
return value;
}
public static int read(String name,int dimension, int index ){ 
logger.read("AntTspVisual", name ,index ,dimension);
return index; 
}
}
