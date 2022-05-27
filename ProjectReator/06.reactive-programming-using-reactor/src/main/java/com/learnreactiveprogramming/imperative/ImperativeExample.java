package com.learnreactiveprogramming.imperative;

import java.util.ArrayList;
import java.util.List;

public class ImperativeExample {
    public static void main(String[] args) {
        var names = List.of("adm",  "cathy", "john", "roger","andy", "andy");
        List<String> filterStrings = filterNamesByLength(names, 3);
        System.out.println(filterStrings);
    }

    private static List<String> filterNamesByLength(List<String> names, int size) {
        List<String> strings = new ArrayList<>();
        for (String name : names) {
            if (name.length()> size && !strings.contains(name.toUpperCase())) {
                strings.add(name.toUpperCase());
            }
        }
        return strings;
    }
}
