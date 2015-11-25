package forte.example.guava;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        List<Person> persons = getPersons();

        // runStringsExamples();
        // runJoinerExample();
        // runMapSplitterExample();
        // runIterablesExample(persons);
        // runStopWatchExample();
        // runPreconditions(persons);

        // runJava8Examples(persons);
    }

    private static void runPreconditions(List<Person> persons) {
        Preconditions.checkNotNull(persons, "Person list should be empty");
    }

    private static List<Person> getPersons() {
        Person person = new Person();
        person.setAge(10);
        person.setName("taner");
        Person person2 = new Person();
        person2.setAge(19);
        person2.setName("secil");
        return Lists.newArrayList(person, person2);
    }

    private static void runJava8Examples(List<Person> persons) {
        Map<Integer, List<Person>> personByAge = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getAge()));

        Double averageAge = persons.stream()
                .collect(Collectors.averagingInt(p -> p.getAge()));

        Comparator
                .comparing(Person::getName)
                .thenComparing(Person::getAge);

        LocalDate first = LocalDate.of(2014, 1, 1);
        LocalDate last = LocalDate.of(2014, 1, 31);
        LocalDate middle = LocalDate.of(2014, 1, 15);

        Range<LocalDate> range = Range.closed(first, last);
        Range<LocalDate> part = Range.closed(first, middle);


        try {
            List<String> lines = Files.readLines(
                    new File("foo.txt"), Charsets.UTF_8);

            Files.copy(new File("foo.txt"), new File("bar.txt"));

            FluentIterable<File> traversal = Files.fileTreeTraverser().
                    postOrderTraversal(new File("dir"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Stream<String> stream = java.nio.file.Files.lines(
                    new File("foo.txt").toPath(),
                    Charset.forName("UTF-8"));

            java.nio.file.Files.copy(new File("foo.txt").toPath(),
                    new File("bar.txt").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            try (Stream<Path> paths = java.nio.file.Files.walk(new File("dir").toPath())) {
                // ...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.util.Optional<Person> optional = getPerson();

        String name = optional.map(p -> p.getName()).orElse("Unknown");

    }

    private static void runStopWatchExample() {
        Stopwatch stopwatch = Stopwatch.createStarted(); // Guava 15.0
        //doSomething();
        stopwatch.stop();
        System.out.println(stopwatch);
    }

    private static Optional<Person> getPerson() {
        return null;
    }

    private static void runIterablesExample(List<Person> persons) {
        Iterable<Person> adults = Iterables.filter(persons, p -> p.getAge() >= 18);
        List<String> names = Lists.newArrayList(Iterables.transform(adults, p -> p.getName()));
        names.forEach(System.out::println);

        ImmutableList<String> immutableList = FluentIterable.from(persons).filter(p -> p.getAge() < 18).transform(p -> p.getName()).toList();
        immutableList.forEach(System.out::println);
    }

    private static void runMapSplitterExample() {
        Splitter.MapSplitter splitter = Splitter.on(" ").withKeyValueSeparator(":");
        Map<String, String> map = splitter.split("a:1 b:2"); // => Map {a=1, b=2}

        map.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    private static void runJoinerExample() {
        List<String> parts = Arrays.asList("a", "b", "c", null);
        String joined = Joiner.on(", ").skipNulls().join(parts);
        System.out.println(joined);
    }

    private static void runStringsExamples() {
        String name = "Taner";
        String nullStr = null;
        String emptyStr = "";
        boolean isNullOrEmpty = Strings.isNullOrEmpty(nullStr);
        System.out.println(isNullOrEmpty);
        isNullOrEmpty = Strings.isNullOrEmpty(emptyStr);
        System.out.println(isNullOrEmpty);
        isNullOrEmpty = Strings.isNullOrEmpty(name);
        System.out.println(isNullOrEmpty);

        String secondNullStr = Strings.emptyToNull(emptyStr);
        System.out.println("emptyStr : " + emptyStr + "  ->  secondNullStr : " + secondNullStr);

        String repeatItself = Strings.repeat("a", 3);
        System.out.println("repeatItself : " + repeatItself);

        name = Strings.padEnd(name, 12, 'a');
        System.out.println(name);
    }
}