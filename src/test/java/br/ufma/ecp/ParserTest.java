package br.ufma.ecp;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class ParserTest extends TestSupport {
    
    @Test
    public void testLetParse() {
        var input = "let a = 5+B-8;";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseLet();
        System.out.println(parser.XMLOutput());
    }


    @Test
    public void testIfParse() {
        var input = "if (10) { let a[4] = 10 - 5; }";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseIf();
        System.out.println(parser.XMLOutput());
    }

    @Test
    public void testDoParse() {
        var input = "do calcula(10);";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseDo();
        System.out.println(parser.XMLOutput());
    }

    @Test
    public void testDoParseMethod() {
        var input = "do ponto.distancia(10);";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseDo();
        System.out.println(parser.XMLOutput());
    }


    @Test
    public void testDoParseExpressionList() {
        var input = "do ponto.distancia(10, 20);";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseDo();
        System.out.println(parser.XMLOutput());
    }

    @Test
    public void testClassVarDecParser() {
        var input = "field int a,b;";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseClassVarDec();
        System.out.println(parser.XMLOutput());
    }

    @Test
    public void testParserSubroutineDec() {
        var input = "method void funcao (){var int a; let a = 10;}";
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseSubroutineDec();
        System.out.println(parser.XMLOutput());
    }


    @Test
    public void testParserWithLessSquareGame() throws IOException {
        var input = fromFile("ExpressionLessSquare/SquareGame.jack");
        var expectedResult =  fromFile("ExpressionLessSquare/SquareGame.xml");

        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        var result = parser.XMLOutput();
        expectedResult = expectedResult.replaceAll("  ", "");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testParserWithSquareGame() throws IOException {
        var input = fromFile("Square/SquareGame.jack");
        var expectedResult =  fromFile("Square/SquareGame.xml");

        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        var result = parser.XMLOutput();
        expectedResult = expectedResult.replaceAll("  ", "");
        assertEquals(expectedResult, result);
    }


    @Test
    public void testParserWithSquare() throws IOException {
        var input = fromFile("Square/Square.jack");
        var expectedResult =  fromFile("Square/Square.xml");

        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        var result = parser.XMLOutput();
        expectedResult = expectedResult.replaceAll("  ", "");
        assertEquals(expectedResult, result);
    }

}
