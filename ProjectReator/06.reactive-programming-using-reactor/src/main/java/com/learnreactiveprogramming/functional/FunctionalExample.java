package com.learnreactiveprogramming.functional;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionalExample {
    public static void main(String[] args) {
        var names = List.of("adm",  "cathy", "john", "roger","andy", "andy");
        List<String> filterStrings = filterNamesByLength(names, 3);
        System.out.println(filterStrings);
    }

    private static List<String> filterNamesByLength(List<String> names, int size) {
       return names.stream()
               .filter(name -> name.length() > size)
               .distinct()
               .map(String::toUpperCase)
               .sorted()
               .collect(Collectors.toList());
    }
}
