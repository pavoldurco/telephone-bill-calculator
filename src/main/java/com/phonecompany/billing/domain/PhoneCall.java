package com.phonecompany.billing.domain;

import java.time.LocalDateTime;

public class PhoneCall {

    private String phoneNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PhoneCall() {
    }

    public PhoneCall(String phoneNumber, LocalDateTime startTime, LocalDateTime endTime) {
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PhoneCall{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}