package ru.yellowblacksnek;

public class Interpolator {
    public static double interpolate(double[][] nodes, double x) {
        double[] X = nodes[0];
        double[] Y = nodes[1];
        int n = X.length;
        return interpolate(X, Y, n, x);
    }
    private static double interpolate(double[] X, double[] Y, int n, double x) {
        double s = 0;
        for(int i = 0; i < n; ++i) {
            double p = 1;
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    p *= (x - X[j])/(X[i] - X[j]);
                }
            }
            s += p * Y[i];
        }
        return s;
    }
}
