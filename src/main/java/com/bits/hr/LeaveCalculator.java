package com.bits.hr;

/**
 * Employee Leave Service - DIVYANSH JHA
 *
 * Calculates an employee's remaining leave balance and the amount payable
 * when the remaining balance is encashed.
 */
public class LeaveCalculator {

    private static final int WORKING_DAYS_PER_MONTH = 30;

    /** Remaining leave = entitled days - days already taken. */
    public int leaveBalance(int entitledDays, int daysTaken) {
        if (entitledDays < 0 || daysTaken < 0) {
            throw new IllegalArgumentException("Leave days cannot be negative");
        }
        if (daysTaken > entitledDays) {
            throw new IllegalArgumentException("Leave taken cannot exceed entitlement");
        }
        return entitledDays - daysTaken;
    }

    /** Encashment = (monthly basic / 30) * balance days, rounded to 2 decimals. */
    public double leaveEncashment(int balanceDays, double monthlyBasic) {
        if (balanceDays < 0 || monthlyBasic < 0) {
            throw new IllegalArgumentException("Values cannot be negative");
        }
        double perDayPay = monthlyBasic / WORKING_DAYS_PER_MONTH;
        return Math.round(perDayPay * balanceDays * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        LeaveCalculator calculator = new LeaveCalculator();
        int balance = calculator.leaveBalance(30, 12);
        System.out.println("Leave balance    : " + balance + " days");
        System.out.println("Leave encashment : Rs. " + calculator.leaveEncashment(balance, 60000));
    }
}
