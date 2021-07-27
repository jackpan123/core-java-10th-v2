package com.jackpan.v2ch01.collecting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jackpan
 * @version v1.0 2021/7/27 12:54
 */
public class CollectingResults {

    public static void main(String[] args) throws IOException {

        Iterator<Integer> iterator = Stream.iterate(0, n -> n + 1).limit(10)
            .iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10)
            .toArray();
        System.out.println("Object array:" + numbers);

        try {
            Integer number = (Integer) numbers[0];
            System.out.println("number: " + number);
            System.out.println("The following statement throws an exception:");
            Integer[] numbers2 = (Integer[]) numbers;
        } catch (ClassCastException ex) {
            System.out.println(ex);
        }

        Integer[] numbers3 = Stream.iterate(0, n -> n + 1).limit(10)
            .toArray(Integer[]::new);
        System.out.println("Integer array: " + numbers3);

        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet", noVowelSet);

        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet", noVowelTreeSet);

        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("Joining: " + result);
        result = noVowels().limit(10).collect(Collectors.joining(", "));
        System.out.println("Joining with commas: " + result);

        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word Length: " + maxWordLength);
        System.out.println("Foreach:");
        noVowels().limit(10).forEach(System.out::println);

    }

    public static Stream<String> noVowels() throws IOException {
        String contents = new String(Files.readAllBytes(
            Paths.get("src/gutenberg/alice30.txt")),
            StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        Stream<String> words = wordList.stream();
        return words;
    }

    public static <T> void show(String label, Set<T> set) {
        System.out.print(label + ": " + set.getClass().getName());
        System.out.println("[" + set.stream().limit(10).map(Objects::toString)
            .collect(Collectors.joining(", ")) + "]");
    }
}
