package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

public class Repeat {

    @RepeatedTest(10)
    public void shouldAnswerWithTrue() {
        Assertions.assertTrue( Math.random() > 0.5 );
    }
}
