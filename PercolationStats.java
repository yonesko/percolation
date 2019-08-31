/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double DOUBLECONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final double lo;
    private final double hi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        double[] ans = new double[trials];
        for (int t = 0; t < trials; t++) ans[t] = makeTrial(n);
        mean = StdStats.mean(ans);
        stddev = StdStats.stddev(ans);
        lo = mean() - DOUBLECONFIDENCE_95 * stddev() / Math.sqrt(trials);
        hi = mean() + DOUBLECONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    private double makeTrial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
        }
        return percolation.numberOfOpenSites() / ((double) n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return hi;
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);

        System.out.println("stats.mean() = " + stats.mean());
        System.out.println("stats.stddev() = " + stats.stddev());
        System.out.println("stats.confidenceLo() = " + stats.confidenceLo());
        System.out.println("stats.confidenceHi() = " + stats.confidenceHi());
    }
}
