package com.meli.spock.example

import spock.lang.Rollup
import spock.lang.Specification
import spock.lang.Unroll

class RectangleSpec extends Specification {

    // Explain parametrized tests, @Unroll annotation, how to catch exceptions and # variables
    @Unroll("Instance return IllegalArgumentException due Rectangle[length=#length,width=#width]")
    def "Invalid rectangle instance"() {
        when:
        new Rectangle(length, width)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == expectedMesagge

        where:
        length | width || expectedMesagge
        null   | 1     || "The length could not be null."
        1      | null  || "The width could not be null."
        null   | null  || "The length could not be null."
        -1     | 1     || "The length should be greater than zero."
        0      | 1     || "The length should be greater than zero."
        1      | -1    || "The width should be greater than zero."
        1      | 0     || "The width should be greater than zero."
    }


    // Explain the brief way to tests simple methods
    def "instance is valid way one"() {
        given: 'the length and width'
        def length = 10
        def width = 20

        when: 'we create a new rectangle'
        def result = new Rectangle(length, width)

        then: 'the rectangle must have the right length and width'
        result.length == 10
        result.width == 20
    }

    def "instance is valid way two"() {
        expect:
        with(new Rectangle(10, 20)) {
            length == 10
            width == 20
        }
    }

    // Explain how the errors are shown
    def "calculate area when Rectangle[length=#length,width=#width] then [area=#expectedArea]"() {
        given: 'a Rectangle'
        def aRectangle = new Rectangle(length, width)

        when:
        def result = aRectangle.calculateArea()

        then:
        result == expectedArea

        where:
        length | width || expectedArea
        10     | 10    || 100
        10     | 20    || 150
    }
}
