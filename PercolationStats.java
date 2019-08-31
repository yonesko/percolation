/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] ans;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        ans = new double[this.trials];
        for (int t = 0; t < this.trials; t++) ans[t] = makeTrial(n);
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
        return StdStats.mean(ans);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ans);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);

        System.out.println("stats.mean() = " + stats.mean());
        System.out.println("stats.stddev() = " + stats.stddev());
        System.out.println("stats.confidenceLo() = " + stats.confidenceLo());
        System.out.println("stats.confidenceHi() = " + stats.confidenceHi());
    }
}
