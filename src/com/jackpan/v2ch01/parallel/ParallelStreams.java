package com.jackpan.v2ch01.parallel;

import static java.util.stream.Collectors.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * @author jackpan
 */
public class ParallelStreams {
   public static void main(String[] args) throws IOException
   {
      String contents = new String(Files.readAllBytes(
          Paths.get("src/gutenberg/alice30.txt")), StandardCharsets.UTF_8
      );
      List<String> wordList = Arrays.asList(contents.split("\\PL+"));

      int[] shortWords = new int[10];
      wordList.parallelStream().forEach(s -> {
         if (s.length() < 10) {
            shortWords[s.length()]++;
         }
      });
      System.out.println(Arrays.toString(shortWords));

      Arrays.fill(shortWords, 0);
      wordList.parallelStream().forEach(s -> {
         if (s.length() < 10) {
            shortWords[s.length()]++;
         }
      });
      System.out.println(Arrays.toString(shortWords));

      Map<Integer, Long> shortWordCounts = wordList.parallelStream().filter(s -> s.length() < 10)
          .collect(groupingBy(String::length, counting()));
      System.out.println(shortWordCounts);

      ConcurrentMap<Integer, List<String>> result = wordList.parallelStream()
          .collect(groupingByConcurrent(String::length));
      System.out.println(result.get(14));

      result = wordList.parallelStream().collect(groupingByConcurrent(String::length));
      System.out.println(result.get(14));

      ConcurrentMap<Integer, Long> wordCounts = wordList.parallelStream()
          .collect(groupingByConcurrent(String::length, counting()));
      System.out.println(wordCounts);
   }
}
