package com.tutorial.carservice.serviceimpl.utils;

import java.util.Optional;
import java.util.function.Supplier;

public class FeignUtils {

    public static <T> Optional<T> safeFeignCall(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
