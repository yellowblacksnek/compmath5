package ru.yellowblacksnek;

import org.knowm.xchart.*;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Drawer {
    private static SwingWrapper<XYChart> wrapper = null;
    private static JFrame frame = null;
    private static XYChart chart = null;

    private final static int DRAWING_ACCURACY = 200;

    private static void init() {
        chart =  new XYChartBuilder().width(800).height(600).title("Line").xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setXAxisDecimalPattern("#.#");
        chart.getStyler().setYAxisDecimalPattern("#.#");

        chart.addAnnotation(new AnnotationLine(0, false, false));
        chart.addAnnotation(new AnnotationLine(0, true, false));

        //chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setLegendVisible(false);
        //chart.getStyler().setLegendLayout(Styler.LegendLayout.Horizontal);

        chart.getStyler().setMarkerSize(6);

        wrapper = new SwingWrapper<>(chart);
        /*frame = wrapper.displayChart();
        SwingUtilities.invokeLater(
                ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        );*/
    }

    public static void draw(Fun[] funcs, double minX, double maxX, double[][] nodes) {
        boolean doInitialize = wrapper == null || frame == null || (!frame.isValid() && !frame.isVisible());
        if(doInitialize) init();

        chart.getSeriesMap().clear();

        double minY = 0d;
        double maxY = 0d;

        for(Fun f : funcs) {
            XYSeries line = null;
            if(f != null) {
                double[][] points = getPoints(f, minX, maxX, DRAWING_ACCURACY);
                double[] xData = points[0];
                double[] yData = points[1];

                minY = Arrays.stream(yData).min().getAsDouble();
                maxY = Arrays.stream(yData).max().getAsDouble();

                line = chart.addSeries(f.toString(), xData, yData);
            } else {
                minY = Arrays.stream(nodes[1]).min().getAsDouble();
                maxY = Arrays.stream(nodes[1]).max().getAsDouble();

                line = chart.addSeries("f(x)", nodes[0], nodes[1]);
            }
            line.setLineColor(Color.BLACK);
            line.setMarker(SeriesMarkers.NONE);
        }
        setEdges(minX, maxX, minY, maxY);

        XYSeries nodesLine = chart.addSeries("Узлы", nodes[0], nodes[1]);
        nodesLine.setMarker(SeriesMarkers.CIRCLE);
        nodesLine.setMarkerColor(Color.RED);
        nodesLine.setLineStyle(SeriesLines.NONE);

        if(doInitialize) {
            frame = wrapper.displayChart();
            SwingUtilities.invokeLater(
                    ()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
            );
        }
        wrapper.repaintChart();
    }

    private static double[][] getPoints(Fun f, double minX, double maxX, int accuracy) {
        double[] xData = new double[accuracy];
        xData[0] = minX;
        for (int i = 1; i < accuracy - 1; i++) xData[i] = xData[i - 1] + (maxX - minX) / accuracy;
        xData[accuracy - 1] = maxX;
        double[] yData = new double[xData.length];
        for (int i = 0; i < xData.length; i++) {
            double value = f.apply(xData[i]);
            if(!Double.isFinite(value)) throw new NumberFormatException();
            yData[i] = value;
        }
        return new double[][]{xData, yData};
    }

    private static void setEdges(double minX, double maxX, double minY, double maxY) {
        if(chart != null) {
            chart.getStyler().setYAxisMin(minY);
            chart.getStyler().setYAxisMax(maxY);
            chart.getStyler().setXAxisMin(minX);
            chart.getStyler().setXAxisMax(maxX);
        }
    }

}