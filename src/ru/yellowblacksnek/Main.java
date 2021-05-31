package ru.yellowblacksnek;

import static ru.yellowblacksnek.Drawer.draw;
import static ru.yellowblacksnek.Interpolator.interpolate;
import static ru.yellowblacksnek.Solver.doBetterEuler;

import org.mariuszgromada.math.mxparser.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        while(true) {
            try {
                UserInterface.start();
            } catch (IOException e) {
                System.out.println("Произошла ошибка ввода-вывода((((");
            }
        }
    }
}
