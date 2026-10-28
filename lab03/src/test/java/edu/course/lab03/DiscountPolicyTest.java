package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountPolicyTest {
    @Test
    void noDiscountKeepsPrice() {
        assertEquals(100.0, new NoDiscount().apply(100.0), 0.000001);
    }

    @Test
    void noDiscountAcceptsZero() {
        assertEquals(0.0, new NoDiscount().apply(0.0), 0.000001);
    }

    @Test
    void noDiscountRejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new NoDiscount().apply(-1.0));
    }

    @Test
    void percentDiscountReducesPrice() {
        assertEquals(80.0, new PercentDiscount(20).apply(100.0), 0.000001);
    }

    @Test
    void percentDiscountSupportsBoundaryPercentages() {
        assertEquals(100.0, new PercentDiscount(0).apply(100.0), 0.000001);
        assertEquals(0.0, new PercentDiscount(100).apply(100.0), 0.000001);
    }

    @Test
    void percentDiscountRejectsInvalidPercentages() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-1));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(101));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(Double.NaN));
    }

    @Test
    void percentDiscountRejectsInvalidPrices() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(20).apply(-5));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(20).apply(Double.POSITIVE_INFINITY));
    }

    @Test
    void calculatorCanUseDifferentPolicies() {
        PriceCalculator regular = new PriceCalculator(new NoDiscount());
        PriceCalculator discounted = new PriceCalculator(new PercentDiscount(20));
        assertEquals(100.0, regular.calculate(100), 0.000001);
        assertEquals(80.0, discounted.calculate(100), 0.000001);
    }

    @Test
    void calculatorRequiresPolicy() {
        assertThrows(NullPointerException.class, () -> new PriceCalculator(null));
    }
}
