package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZeroFunctionTest {
    @Test
    void ConstructorTest() {
        try {
            ZeroFunction func = new ZeroFunction();
        }
        catch (Exception e) {}

    }
}