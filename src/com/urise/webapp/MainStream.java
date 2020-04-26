package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
/*
реализовать метод через стрим int minValue(int[] values).
Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
(int)Math.log10(x)+1 - разрядность числа
 */

    public static int minValue(int[] values) {
        int sum = 0;
        List<Integer> list = Arrays.stream(values)
                .boxed()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        int j = list.size();
        for (Integer i : list) {
            sum = (int) (sum + (i * Math.pow(10, --j)));
        }
        return sum;

        /*int s = IntStream.of(values)
                .boxed()
                .distinct()
                .sorted()
                .mapToInt(x -> (int) (x * Math.pow(10, ))).sum();*/
    }

/*
реализовать метод List<Integer> oddOrEven(List<Integer> integers)
если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
 */

    public static List<Integer> oddOrEven(List<Integer> integers) {

        return integers.stream().reduce(0, (x, y) -> x + y) % 2 == 0 ?
                integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList())
                : integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());

        // Optional<List<Integer>> listOfOptional = Optional.of(integers);
        /*return listOfOptional
                .flatMap(integers -> {
                    if (integers.stream().reduce(0, (x, y) -> x + y) % 2 != 0)
                        integers.removeIf(x -> x % 2 != 0);
                    else
                        integers.removeIf(x -> x % 2 != 0);
                    return integers;
                }).*/
    }
}