package ru.yellowblacksnek;

public class Solver {
    public static double[][] doBetterEuler(FunOfTwo f, double x0, double y0, double xn, double h) {
        int n = (int) Math.ceil((xn-x0)/Math.sqrt(h));
        h = (xn-x0)/n;
        ++n;
        return doBetterEuler(f, x0, y0, h, n);
    }

    private static double[][] doBetterEuler(FunOfTwo f, double x0, double y0, double h, int n) {
        double[] x = new double[n];
        double[] y = new double[n];
        x[0] = x0;
        y[0] = y0;
        for(int i = 1; i < n; ++i) {
            x[i] = x[i-1]+h;
            double predY = y[i-1] + h*f.apply(x[i-1],y[i-1]);
            y[i] = y[i-1] + (h/2)*(f.apply(x[i-1],y[i-1])+f.apply(x[i],predY));
            if(!Double.isFinite(y[i])) throw new NumberFormatException();
        }
        return new double[][] {x,y};
    }

    public static double[][] doEuler(FunOfTwo f, double x0, double y0, double xn, double h) {
        int n = (int) Math.ceil((xn-x0)/h) + 1;
        double[] x = new double[n];
        double[] y = new double[n];
        x[0] = x0;
        y[0] = y0;
        for(int i = 1; i < n; ++i) {
            x[i] = x[i-1]+h;
            y[i] = y[i-1] + h*f.apply(x[i-1], y[i-1]);
        }
        return new double[][] {x,y};
    }
}
