package com.lightspeed.deepclone;

import java.util.ArrayList;
import java.util.List;

import static com.lightspeed.deepclone.CopyReflectionUtils.deepCopy;

public class Start {
    public static void main(String[] args) {
        List<String> favoriteBooks = new ArrayList<>();
        favoriteBooks.add("Clean Code");
        favoriteBooks.add("Clean Architecture");

        Man original = new Man("Gor", 34, favoriteBooks);
        Man copy = deepCopy(original);

        System.out.println("original " + original);
        System.out.println("copy " + copy);
        System.out.println("is deep copy " + (original != copy && original.equals(copy)));
    }
}
