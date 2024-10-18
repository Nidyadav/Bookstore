package com.example.bookstore.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Utils {

    public List<String> stringToList(String str) {
        str = str.substring(1, str.length() - 1);

        // Split by ', ' and then trim quotes
        return Arrays.stream(str.split(","))
                .map(s -> s.trim().replaceAll("^\'|\'$", ""))
                .toList();
    }
}
