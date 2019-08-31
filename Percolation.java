/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.LinkedList;
import java.util.List;

public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int n;
    private final boolean[] opened;
    private int numberOfOpenSites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) throw new IllegalArgumentException();
        this.n = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= n; i++) {
            weightedQuickUnionUF.union(0, index(1, i));
            weightedQuickUnionUF.union(index(n, n) + 1, index(n, i));
        }
        opened = new boolean[n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1) throw new IllegalArgumentException();
        if (isOpen(row, col)) return;

        openOne(indexOpened(row, col));
        List<Integer> openNeigbours = new LinkedList<>();

        if (row + 1 <= n && isOpen(row + 1, col)) openNeigbours.add(index(row + 1, col));
        if (row - 1 >= 1 && isOpen(row - 1, col)) openNeigbours.add(index(row - 1, col));
        if (col + 1 <= n && isOpen(row, col + 1)) openNeigbours.add(index(row, col + 1));
        if (col - 1 >= 1 && isOpen(row, col - 1)) openNeigbours.add(index(row, col - 1));

        for (Integer openNeigbour : openNeigbours) {
            openOne(openNeigbour - 1);
            weightedQuickUnionUF.union(index(row, col), openNeigbour);
        }
    }

    private void openOne(int i) {
        numberOfOpenSites++;
        opened[i] = true;
    }

    private int index(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
        return (row - 1) * n + (col - 1) + 1;
    }

    private int indexOpened(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1) throw new IllegalArgumentException();
        return opened[indexOpened(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) throw new IllegalArgumentException();
        return weightedQuickUnionUF.connected(0, index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, index(n, n) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        if (percolation.isOpen(1, 1)) throw new RuntimeException();
        percolation.open(1, 1);
        if (!percolation.isOpen(1, 1)) throw new RuntimeException();
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        if (percolation.percolates()) throw new RuntimeException();
        percolation.open(5, 1);
        if (!percolation.percolates()) throw new RuntimeException();
    }
}
