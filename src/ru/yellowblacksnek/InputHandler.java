package ru.yellowblacksnek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String getInput() throws IOException {
        return reader.readLine();
    }

    public static int getInputAsInt() throws IOException{
        while(true) {
            try {
                int i = Integer.parseInt(getInput());
                return i;
            } catch(NumberFormatException e) {
                System.out.println("Вы ввели не число");
            }
        }
    }

    public static double getInputAsDouble() throws IOException{
        while(true) {
            try {
                double i = Double.parseDouble(getInput().replace(',', '.'));
                return i;
            } catch(NumberFormatException e) {
                System.out.println("Вы ввели не число");
            }
        }
    }
}
