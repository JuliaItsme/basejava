package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 3, 2, 3, 5, 7, 7};

        System.out.println(minValue(array));

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .boxed()
                .distinct()
                .sorted()
                .reduce((x, y) -> (10 * x) + y).get();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(x -> x).sum();
        return integers.stream().filter(x -> (sum % 2 == 0) == (x % 2 == 0)).collect(Collectors.toList());
    }
}