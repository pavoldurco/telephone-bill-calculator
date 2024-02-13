package com.phonecompany.billing.policy;

import java.math.BigDecimal;
import java.time.LocalTime;

public class PricingPolicy {
    public static final BigDecimal DAY_RATE = new BigDecimal("1.00");
    public static final BigDecimal NIGHT_RATE = new BigDecimal("0.50");
    public static final BigDecimal LONG_CALL_RATE = new BigDecimal("0.20");
    public static final int LONG_CALL_MINUTES_THRESHOLD = 5;
    public static final LocalTime DAY_START = LocalTime.of(8, 0);
    public static final LocalTime DAY_END = LocalTime.of(16, 0);
}
