package com.lightspeed.deepclone;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CopyReflectionUtilsTest {

    @Test
    public void testDeepCopyManObject() {
        Man original = new Man("Gor", 30, new ArrayList<>());
        Man copy = CopyReflectionUtils.deepCopy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);
    }

    @Test
    public void testDeepCopyWithNestedObjects() {
        List<String> favoriteBooks = new ArrayList<>(Arrays.asList("clean code", "Clean Architecture"));
        Man original = new Man("Gor", 34, favoriteBooks);
        Man copy = CopyReflectionUtils.deepCopy(original);

        assertNotSame(original, copy);
        assertEquals(original, copy);

        assertNotSame(original.getFavoriteBooks(), copy.getFavoriteBooks());
        assertEquals(original.getFavoriteBooks(), copy.getFavoriteBooks());

        original.getFavoriteBooks().add("effective java");
        assertNotEquals(original.getFavoriteBooks(), copy.getFavoriteBooks());
    }

    @Test
    public void testDeepCopyWithPrimitiveTypes() {
        Integer original = 42;
        Integer copy = CopyReflectionUtils.deepCopy(original);

        assertSame(original, copy);
    }

    @Test
    public void testDeepCopyWithImmutableString() {
        String original = "immutableString";
        String copy = CopyReflectionUtils.deepCopy(original);

        assertSame(original, copy);
    }

    @Test
    public void testDeepCopyWithArray() {
        int[] original = {1, 2, 3};
        int[] copy = CopyReflectionUtils.deepCopy(original);

        assertNotSame(original, copy);
        assertArrayEquals(original, copy);

        original[0] = 42;
        assertNotEquals(original[0], copy[0]);
    }
}