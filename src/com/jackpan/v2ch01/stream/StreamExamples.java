package com.jackpan.v2ch01.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author jackpan
 * @version v1.0 2021/7/25 22:54
 */
public class StreamExamples {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/gutenberg/alice30.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("\\PL+"));
        Object[] objects = words.stream().skip(1).map(String::toLowerCase)
            .peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();

        Stream.iterate(1.0, p -> p * 2)
            .peek(e -> System.out.println("Fetching " + e)).limit(20).toArray();

        Optional<String> max = words.stream().max(String::compareToIgnoreCase);
        System.out.println("Max: " + max.orElse(""));

        Optional<String> q = words.stream().filter(s -> s.startsWith("Q")).findFirst();
        System.out.println("first Q: " + q.orElse(""));

        Optional<String> any = words.stream().parallel().filter(s -> s.startsWith("Q")).findAny();
        System.out.println("first Q: " + any.orElse(""));

        boolean result = words.stream().parallel().anyMatch(s -> s.startsWith("Q"));
        System.out.println(result);
    }

    public static Stream<String> letters(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            result.add(s.substring(i, i+1));
        }
        return result.stream();
    }
}
