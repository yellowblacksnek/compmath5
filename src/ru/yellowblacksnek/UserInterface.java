package ru.yellowblacksnek;

import org.mariuszgromada.math.mxparser.Function;

import java.io.IOException;
import java.util.Arrays;

import static ru.yellowblacksnek.Drawer.draw;
import static ru.yellowblacksnek.InputHandler.*;
import static ru.yellowblacksnek.Solver.doBetterEuler;

public class UserInterface {
    public static void start() throws IOException {
        FunOfTwo f = inputFunction();
        while(true) {
            Task task = inputTask();
            double x0 = task.x0;
            double y0 = task.y0;
            double xn = task.xn;
            double h = task.h;
            if(Double.isNaN(h)) break; //NaN for h is reserved for function change

            try {
                double[][] nodes = doBetterEuler(f, x0, y0, xn, h);
                if (nodes[0].length < 50) draw(new Fun[]{(x -> Interpolator.interpolate(nodes, x))}, x0, xn, nodes);
                else draw(new Fun[]{null}, x0, xn, nodes);

                boolean printNodes = false;
                if (printNodes) {
                    int n = nodes[0].length;
                    for (int i = 0; i < n; ++i) System.out.print(nodes[0][i] + " ");
                    System.out.println();
                    for (int i = 0; i < n; ++i) System.out.print(nodes[1][i] + " ");
                    System.out.println();
                }

                System.out.println("Решение найдено");
            } catch(NumberFormatException e) {
                System.out.println("Не удалось найти решение");
            }
        }
    }

    private static FunOfTwo inputFunction() throws IOException {
        System.out.println("Введите функцию: ");
        while(true) {
            String input = getInput();
            Function parsedF = new Function("f(x,y) = " + input);
            if(!parsedF.checkSyntax()) {
                System.out.println("Неверный синтаксис.");
                continue;
            }
            return parsedF::calculate;
        }
    }

    private static Task inputTask() throws IOException{
        System.out.println("Для изменения уравнения введите 0, для решения ОДУ введите данные в формате:");
        System.out.println("x0 y0 [правая граница] [точность]");

        while(true) {
            String input = getInput();
            String[] split = input.split(" ");
            if (split.length == 1) {
                if(Math.abs(Double.parseDouble(split[0].replace(',', '.'))) < 10e-15)
                    return new Task(0,0,0,Double.NaN);
            } else if(split.length == 4) {
                try {
                    double x0 = Double.parseDouble(split[0].replace(',', '.'));
                    double y0 = Double.parseDouble(split[1].replace(',', '.'));
                    double xn = Double.parseDouble(split[2].replace(',', '.'));
                    double h = Double.parseDouble(split[3].replace(',', '.'));

                    if(x0 < xn && h > 0 && xn-x0 > h) return new Task(x0, y0, xn, h);
                } catch (Exception e) {
                }
            }
            System.out.println("Неверный формат");
        }
    }


}
