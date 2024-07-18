package com.chervonnaya;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParallelStreamTrainer {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Student1", Map.of("Math", 90, "Physics", 85)),
            new Student("Student2", Map.of("Math", 95, "Physics", 88)),
            new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
            new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        Map<String, List<Integer>> subjectGrades = students.parallelStream()
            .flatMap(student -> student.getGrades().entrySet().stream())
            .collect(Collectors.groupingByConcurrent(
                Map.Entry::getKey,
                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
            ));

        Map<String, Double> averageGrades = subjectGrades.entrySet().stream()
            .collect(Collectors.toConcurrentMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0)
            ));

        averageGrades.forEach((subject, average) ->
            System.out.println("Subject: " + subject + ", Average Grade: " + average));


    }
}
