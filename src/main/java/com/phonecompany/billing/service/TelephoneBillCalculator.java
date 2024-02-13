package com.phonecompany.billing.service;

import java.math.BigDecimal;

public interface TelephoneBillCalculator {
    /**
     * Calculates the total cost of all phone calls in the provided phone log.
     *
     * Each line should contain the phone number, start time, and end time of the call, separated by commas.
     * The start and end times should be in the format of 'dd-MM-yyyy HH:mm:ss'.
     *
     * @param phoneLog the phone log as a string
     * @return the total cost of all phone calls in the phone log as a BigDecimal
     */
    BigDecimal calculate (String phoneLog);
}
