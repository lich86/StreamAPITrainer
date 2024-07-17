package com.chervonnaya;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollectorsTrainer {
    public static void main(String[] args) {
        List<Order> orders = List.of(
            new Order("Laptop", 1200.0),
            new Order("Smartphone", 800.0),
            new Order("Laptop", 1500.0),
            new Order("Tablet", 500.0),
            new Order("Smartphone", 900.0)
        );

        Map<String, List<Order>> ordersGroupedByProduct = orders.stream()
            .collect(Collectors.groupingBy(Order::getProduct));
        ordersGroupedByProduct.forEach((k, v) -> System.out.println(v));

        System.out.println();

        Map<String, Double> totalOrderCost = orders.stream()
            .collect(Collectors.groupingBy(Order::getProduct, Collectors.summingDouble(Order::getCost)));

        Map<String, Double> totalOrderCostSorted = totalOrderCost.entrySet().stream().
            sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new));

        totalOrderCostSorted.forEach((k, v) -> System.out.println(k + ": " + v));

        Map.Entry<String, Double> threeMostExpensiveItems = totalOrderCostSorted.entrySet().stream()
            .limit(3)
            .reduce((e1, e2) -> new AbstractMap.SimpleEntry<>(
                e1.getKey() + ", " + e2.getKey(),
                e1.getValue() + e2.getValue()
            )).get();

        System.out.println();
        System.out.println(threeMostExpensiveItems);
    }


}
