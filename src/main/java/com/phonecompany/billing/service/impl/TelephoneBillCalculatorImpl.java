package com.phonecompany.billing.service.impl;

import com.phonecompany.billing.PricingPolicy;
import com.phonecompany.billing.domain.PhoneCall;
import com.phonecompany.billing.service.TelephoneBillCalculator;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {

    @Override
    public BigDecimal calculate(final String phoneLog) {
        final List<PhoneCall> calls = parsePhoneLog(phoneLog);
        final String mostFrequentlyCalledNumber = getMostFrequentlyCalledNumber(calls);
        BigDecimal totalCost = BigDecimal.ZERO;

        for (final PhoneCall call : calls) {
            if (!call.getPhoneNumber().equals(mostFrequentlyCalledNumber)) {
                totalCost = totalCost.add(calculateCallCost(call));
            }
        }

        return totalCost;
    }

    private String getMostFrequentlyCalledNumber(List<PhoneCall> calls) {
        return calls.stream()
                .collect(Collectors.groupingBy(PhoneCall::getPhoneNumber, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.<String, Long>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .map(Map.Entry::getKey)
                .orElse("");
    }

    private List<PhoneCall> parsePhoneLog(final String phoneLog) {
        final List<PhoneCall> calls = new ArrayList<>();
        if (phoneLog.isEmpty()) {
            return calls;
        }
        final String[] entries = phoneLog.split("\n");
        for (final String entry : entries) {
            final String[] parts = entry.split(",");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid log format");
            }
            final String phoneNumber = parts[0];
            final LocalDateTime startTime = LocalDateTime.parse(parts[1]);
            final LocalDateTime endTime = LocalDateTime.parse(parts[2]);
            calls.add(new PhoneCall(phoneNumber, startTime, endTime));
        }
        return calls;
    }

    private BigDecimal calculateCallCost(final PhoneCall call) {
        final Duration duration = Duration.between(call.getStartTime(), call.getEndTime());
        final long totalMinutes = duration.toMinutes();
        BigDecimal cost = BigDecimal.ZERO;

        final boolean isDaytime = call.getStartTime().toLocalTime().isAfter(PricingPolicy.DAY_START) && call.getStartTime().toLocalTime().isBefore(PricingPolicy.DAY_END);

        for (long i = 0; i < totalMinutes; i++) {
            if (i < PricingPolicy.LONG_CALL_MINUTES_THRESHOLD) {
                cost = cost.add(isDaytime ? PricingPolicy.DAY_RATE : PricingPolicy.NIGHT_RATE);
            } else {
                cost = cost.add(PricingPolicy.LONG_CALL_RATE);
            }
        }

        return cost;
    }

}
