package com.app.soft2projbackend.handlers;

public class MathFunctions {
    public static long square(int x) {
        int result = x * x;
        System.out.println("square result: " + result);
        return result;
    }

    public static long factorial(int x) {
        if (x < 0) {
            System.out.println("factorial result: 0");
            return 0;
        }

        long result = 1;
        for (int i = 1; i <= x; i++) {
            result *= i;
        }

        System.out.println("factorial result: " + result);
        return result;
    }

    public static long expo(int x) {
        if (x < 0) {
            System.out.println("expo result: 0");
            return 0;
        }

        long result = 1L << x;
        System.out.println("expo result: " + result);
        return result;
    }

    public static long add(int num) {
        int result = num + 10;
        System.out.println("add result: " + result);
        return result;
    }
}