package com.bits.hr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** JUnit tests for LeaveCalculator - DIVYANSH JHA */
public class LeaveCalculatorTest {

    private final LeaveCalculator calculator = new LeaveCalculator();

    @Test
    public void leaveBalanceIsEntitlementMinusTaken() {
        assertEquals(18, calculator.leaveBalance(30, 12));
    }

    @Test
    public void leaveEncashmentUsesPerDayBasicPay() {
        assertEquals(36000.0, calculator.leaveEncashment(18, 60000), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void leaveTakenCannotExceedEntitlement() {
        calculator.leaveBalance(10, 15);
    }
}
