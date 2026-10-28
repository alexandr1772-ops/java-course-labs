package edu.course.lab03;

import java.util.Objects;

public class PriceCalculator {
    private final DiscountPolicy discountPolicy;

    public PriceCalculator(DiscountPolicy discountPolicy) {
        this.discountPolicy = Objects.requireNonNull(discountPolicy, "Discount policy is required");
    }

    public double calculate(double price) {
        return discountPolicy.apply(price);
    }
}
