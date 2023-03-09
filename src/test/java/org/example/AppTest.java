package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    @DisplayName("Single test successful")
    public void shouldAnswerWithTrue() {
        Assertions.assertTrue( 2 < 3 );
    }

    @Test
    public void shouldAnswerWithFalse()
    {
        Assertions.assertFalse( 2 > 3 );
    }
}
