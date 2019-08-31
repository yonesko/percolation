/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF perculationUF;
    private final WeightedQuickUnionUF fullnessUF;
    private final int n;
    private final boolean[] opened;
    private int numberOfOpenSites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        perculationUF = new WeightedQuickUnionUF(n * n + 2);
        fullnessUF = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= n; i++) {
            fullnessUF.union(0, index(1, i));
            perculationUF.union(0, index(1, i));
            perculationUF.union(index(n, n) + 1, index(n, i));
        }
        opened = new boolean[n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateCoords(row, col);
        if (isOpen(row, col)) return;

        numberOfOpenSites++;
        opened[indexOpened(row, col)] = true;
        if (row + 1 <= n && isOpen(row + 1, col)) {
            perculationUF.union(index(row, col), index(row + 1, col));
            fullnessUF.union(index(row, col), index(row + 1, col));
        }
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            perculationUF.union(index(row, col), index(row - 1, col));
            fullnessUF.union(index(row, col), index(row - 1, col));
        }
        if (col + 1 <= n && isOpen(row, col + 1)) {
            perculationUF.union(index(row, col), index(row, col + 1));
            fullnessUF.union(index(row, col), index(row, col + 1));
        }
        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            perculationUF.union(index(row, col), index(row, col - 1));
            fullnessUF.union(index(row, col), index(row, col - 1));
        }
    }

    private int index(int row, int col) {
        validateCoords(row, col);
        return (row - 1) * n + (col - 1) + 1;
    }

    private int indexOpened(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void validateCoords(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) throw new IllegalArgumentException();
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateCoords(row, col);
        return opened[indexOpened(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateCoords(row, col);
        return isOpen(row, col) && fullnessUF.connected(0, index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return numberOfOpenSites > 0 && perculationUF.connected(0, index(n, n) + 1);
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
