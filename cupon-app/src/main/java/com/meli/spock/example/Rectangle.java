package com.meli.spock.example;

import org.springframework.util.Assert;

public class Rectangle extends Figure {

    private final Double length;
    private final Double width;

    public Rectangle(Double length, Double width) {
        Assert.notNull(length, "The length could not be null.");
        Assert.notNull(width, "The width could not be null.");
        if (length <= 0) throw new IllegalArgumentException("The length should be greater than zero.");
        if (width <= 0) throw new IllegalArgumentException("The width should be greater than zero.");

        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
