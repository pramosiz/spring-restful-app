package com.tutorial.userservice.serviceimpl.utlis;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.function.Supplier;

public class FeignUtilsTest {

    @Mock
    private Supplier<List<String>> supplier;

    @Test
    void testSafeFeignCallSuccess() {
        // Given
        Supplier<List<String>> supplier = mock(Supplier.class);
        List<String> expectedList = List.of("item1", "item2");
        when(supplier.get()).thenReturn(expectedList);

        // When
        List<String> result = FeignUtils.safeFeignCall(supplier);

        // Then
        assertEquals(expectedList, result);
        verify(supplier).get();
    }

    @Test
    void testSafeFeignCallException() {
        // Given
        Supplier<List<String>> supplier = mock(Supplier.class);
        when(supplier.get()).thenThrow(new RuntimeException("Feign call failed"));

        // When
        List<String> result = FeignUtils.safeFeignCall(supplier);

        // Then
        assertTrue(result.isEmpty());
        verify(supplier).get();
    }
}
