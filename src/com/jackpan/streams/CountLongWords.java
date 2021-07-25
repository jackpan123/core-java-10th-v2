package com.jackpan.streams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author jackpan
 * @version v1.0 2021/7/25 18:00
 */
public class CountLongWords {

    public static void main(String[] args) throws IOException {

        String contents = new String(Files.readAllBytes(
            Paths.get("src/gutenberg/alice30.txt")), StandardCharsets.UTF_8
        );
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        long count = 0;
        for (String word : words) {
            if (word.length() > 12) {
                count++;
            }
        }
        System.out.println(count);

        count = words.stream().filter(word -> word.length() > 12).count();
        System.out.println(count);

        count = words.parallelStream().filter(word -> word.length() > 12).count();
        System.out.println(count);
    }
}
