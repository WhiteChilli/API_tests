package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterTest {

    @ValueSource(strings = {"Hello", "Bye"})
    @ParameterizedTest
    public void dummyTest(String string) {

        Assertions.assertTrue( string.length() > 1 );

    }





    @ValueSource(doubles = {1.5, 4.0, 5.0})
    @ParameterizedTest
    public void dummyDoubleTest(double testParameter) {

        Assertions.assertTrue( testParameter > 0 );

    }





    @CsvSource({
            "Peter,         1",
            "Erik,          2",
            "Ivar,          4"
    })
    @ParameterizedTest
    public void dummyTestCSV(String name, int amount) {
        Assertions.assertNotNull(name );
        Assertions.assertTrue( amount > 0);
    }

    @CsvFileSource(resources = "/testdata.csv")
    @ParameterizedTest
    public void dummyTestFileCSV(String name, int amount) {
        Assertions.assertNotNull(name );
        Assertions.assertTrue( amount > 10);
    }


}
