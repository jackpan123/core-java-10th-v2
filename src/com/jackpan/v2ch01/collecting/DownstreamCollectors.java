package com.jackpan.v2ch01.collecting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

/**
 * @author jackpan
 * @version v1.0 2021/7/28 13:04
 */
public class DownstreamCollectors {

    public static class City
    {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population)
        {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName()
        {
            return name;
        }

        public String getState()
        {
            return state;
        }

        public int getPopulation()
        {
            return population;
        }
    }

    public static Stream<City> readCities(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).map(l -> l.split(", "))
            .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<Locale>> countryToLocalSet = locales.collect(groupingBy(
            Locale::getCountry, toSet()
        ));
        System.out.println("countryToLocalSet: " + countryToLocalSet);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Long> countryToLocaleCounts = locales.collect(groupingBy(
            Locale::getCountry, counting()
        ));
        System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

        Stream<City> cities = readCities("src/gutenberg/cities.txt");
        Map<String, Integer> stateToCityPopulation = cities.collect(groupingBy(
            City::getState, summingInt(City::getPopulation)
        ));
        System.out.println("stateToCityPopulation: " + stateToCityPopulation);

        cities = readCities("src/gutenberg/cities.txt");
        Map<String, Optional<String>> stateToLongestCityName = cities.collect(groupingBy(
            City::getState, mapping(City::getName, maxBy(Comparator.comparing(String::length)))
        ));
        System.out.println("stateToLongestCityName: " + stateToLongestCityName);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryToLanguages = locales.collect(groupingBy(
            Locale::getDisplayCountry,
            mapping(Locale::getDisplayLanguage, toSet())
        ));
        System.out.println("countryToLanguages: " + countryToLanguages);

        cities = readCities("src/gutenberg/cities.txt");
        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities
            .collect(groupingBy(City::getState,
                summarizingInt(City::getPopulation)));
        System.out.println(stateToCityPopulationSummary.get("NY"));

        cities = readCities("src/gutenberg/cities.txt");
        Map<String, String> stateToCityNames = cities.collect(groupingBy(
            City::getState,
            reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s
                + ", " + t)));

        cities = readCities("src/gutenberg/cities.txt");
        stateToCityNames = cities.collect(groupingBy(City::getState,
            mapping(City::getName, joining(", "))));
        System.out.println("stateToCityNames: " + stateToCityNames);
    }

}
