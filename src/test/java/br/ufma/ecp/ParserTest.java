package br.ufma.ecp;

import java.nio.charset.StandardCharsets;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class ParserTest extends TestSupport {
    
    @Test
    public void testLetParse() {
        var input = "let a = 5+B-8;";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        System.out.println(parser.XMLOutput());
    }


    @Test
    public void testIfParse() {
        var input = "if (10) { let a[4] = 10 - 5; }";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        System.out.println(parser.XMLOutput());
    }

}
