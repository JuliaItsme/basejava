package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;


public class MainStream {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 3, 2, 3, 5, 7, 7};

        System.out.println(minValue(array));

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
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
        List<Integer> list = integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        List<Integer> list2 = integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        return integers.stream().mapToInt(x -> x).sum() % 2 == 0 ? list : list2;
    }
}