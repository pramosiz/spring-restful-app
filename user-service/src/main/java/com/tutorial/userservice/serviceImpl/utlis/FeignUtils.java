package com.tutorial.userservice.serviceimpl.utlis;

import java.util.List;
import java.util.function.Supplier;

public class FeignUtils {

    public static <T> List<T> safeFeignCall(Supplier<List<T>> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return List.of();
        }
    }
}
