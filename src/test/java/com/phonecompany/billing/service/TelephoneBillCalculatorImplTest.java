package com.phonecompany.billing.service;

import com.phonecompany.billing.service.impl.TelephoneBillCalculatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TelephoneBillCalculatorImplTest {

    @InjectMocks
    private TelephoneBillCalculatorImpl calculator;

    @Test
    public void testCalculateEmptyLog() {
        String phoneLog = "";
        BigDecimal expectedTotalCost = BigDecimal.ZERO;
        assertEquals(0, expectedTotalCost.compareTo(calculator.calculate(phoneLog)));
    }

    @Test
    public void testCalculateLongCall() {
        String phoneLog = "123456789,2022-01-01T10:00:00,2022-01-01T10:10:00\n" +
                "111111111,2022-01-01T10:15:00,2022-01-01T10:20:00\n" +
                "111111111,2022-01-01T10:20:00,2022-01-01T10:25:00";
        BigDecimal expectedTotalCost = new BigDecimal("6.00");
        assertEquals(0, expectedTotalCost.compareTo(calculator.calculate(phoneLog)));
    }

    @Test
    public void testCalculateWithMostFrequentlyCalledNumber() {
        String phoneLog = "123456789,2022-01-01T10:00:00,2022-01-01T10:03:00\n" +
                "123456789,2022-01-01T10:05:00,2022-01-01T10:08:00\n" + //The most frequently called number
                "987654321,2022-01-01T17:00:00,2022-01-01T17:02:00\n" +
                "987654321,2022-01-01T17:05:00,2022-01-01T17:07:00";

        BigDecimal expectedTotalCost = new BigDecimal("2.00");
        assertEquals(0, expectedTotalCost.compareTo(calculator.calculate(phoneLog)));
    }

    @Test
    public void testCalculateInvalidLogFormat() {
        String phoneLog = "123456789,2022-01-01T10:00:00";
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(phoneLog));
    }

}