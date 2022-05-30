package br.ufma.ecp;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class GeneratorCodeTest {

    @Test
    public void termExpression () {
        var input = """
            class Point {
              field int x, y;
              constructor Point new(int Ax, int Ay) { 
                var int w;
                let w = 10;              
                let x = Ax;
                let y = Ay;
                let x = w;
                return this;
             }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            push argument 0
            push argument 1
            push local 0
                """;
        assertEquals(expected, actual);
    }
    
}
