package com.urise.webapp;

public class MainString {

    public static void main(String[] args) {

        String[] strArray = new String[]{"1", "2", "3", "4", "5"};
        //String result = "";
        StringBuilder sb = new StringBuilder();
        for (String st : strArray) {
            sb.append(st).append(", ");
        }
        System.out.println(sb.toString());

        String st1 = "abc";
        String st5 = "c";
        String st2 = ("ab" + st5).intern();
        System.out.println(st1 == st2);
        System.out.println(st1.equals(st2));

        String st3 = new String("abc");
        String st4 = new String("abc");
        System.out.println(st3 == st4);
        System.out.println(st3.equals(st4));
    }
}
