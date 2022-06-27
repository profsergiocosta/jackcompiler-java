package br.ufma.ecp;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class GeneratorCodeTest {


    @Test
    public void helloTest () {
        var input = """
            class Main {
                function void main () {
                    do Output.printString ("Ola!");
                    return;
                }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Main.main 0
            push constant 4
            call String.new 1
            push constant 79
            call String.appendChar 2
            push constant 108
            call String.appendChar 2
            push constant 97
            call String.appendChar 2
            push constant 33
            call String.appendChar 2
            call Output.printString 1
            pop temp 0
            push constant 0
            return         
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void methodsConstructorTest () {
        var input = """
            class Point {
                field int x, y;
            
                method int getX () {
                    return x;
                }
            
                method int getY () {
                    return y;
                }
            
                method void print () {
                    do Output.printInt(getX());
                    do Output.printInt(getY());
                    return;
                }
            
                constructor Point new(int Ax, int Ay) { 
                  var int w;             
                  let x = Ax;
                  let y = Ay;
                  let w = 42;
                  let x = w;
                  return this;
               }
              }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Point.getX 0
            push argument 0
            pop pointer 0
            push this 0
            return
            function Point.getY 0
            push argument 0
            pop pointer 0
            push this 1
            return
            function Point.print 0
            push argument 0
            pop pointer 0
            push pointer 0
            call Point.getX 1
            call Output.printInt 1
            pop temp 0
            push pointer 0
            call Point.getY 1
            call Output.printInt 1
            pop temp 0
            push constant 0
            return
            function Point.new 1
            push constant 2
            call Memory.alloc 1
            pop pointer 0
            push argument 0
            pop this 0
            push argument 1
            pop this 1
            push constant 42
            pop local 0
            push local 0
            pop this 0
            push pointer 0
            return            
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void methodTest () {
        var input = """
            class Main {
                function void main () {
                    var Point p;
                    var int x;
                    let p = Point.new (10, 20);
                    let x = p.getX();
                    return;
                }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Main.main 2
            push constant 10
            push constant 20
            call Point.new 2
            pop local 0
            push local 0
            call Point.getX 1
            pop local 1
            push constant 0
            return
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void doStatement () {
        var input = """
            class Main {
                function void main () {
                    var int x;
                    let x = 10;
                    do Output.printInt(x);
                    return;
                }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Main.main 1
            push constant 10
            pop local 0
            push local 0
            call Output.printInt 1
            pop temp 0
            push constant 0
            return
                """;
        assertEquals(expected, actual);
    }


    @Test
    public void termExpressionLiteralKeyword () {
        var input = """
            class Main {
                function void main () {
                    var bool x;
                    let x = true;
                    let x = false;
                    let x = null;
                    return;
                }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Main.main 1
            push constant 0
            not
            pop local 0
            push constant 0
            pop local 0
            push constant 0
            pop local 0
            push constant 0
            return
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void termExpressionVar () {
        var input = """
            class Main {
            
              function void main () {
                  var int x, y;
                  let x = 42;
                  let y = x;
              }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Main.main 2
            push constant 42
            pop local 0
            push local 0
            pop local 1
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void termExpressionLiteral () {
        var input = """
            class Main {
            
              function void main () {
                  var int x;
                  let y = 42;
              }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Main.main 1
            push constant 42
            pop local 0
                """;
        assertEquals(expected, actual);
    }

    @Test
    public void termExpression () {
        var input = """
            class Point {
              field int x, y;
              constructor Point new(int Ax, int Ay) { 
                var int w;             
                let x = Ax;
                let y = Ay;
                let w = 42;
                let x = w;
                return this;
             }
            }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();
        String actual = parser.VMOutput();
        String expected = """
            function Point.new 1
            push constant 2
            call Memory.alloc 1
            pop pointer 0
            push argument 0
            pop this 0
            push argument 1
            pop this 1
            push constant 42
            pop local 0
            push local 0
            pop this 0
            push pointer 0
            return
                """;
        assertEquals(expected, actual);
    }


    @Test
    public void writeFunctionTest() {

        var input = """
            class Main {
                function int soma (int x, int y) {
                       return 2 * x;
                }
               
                function void main () {
                       var int d;
                       let d = Main.soma(4,5);
                       return;
                 }
               
               }
            """;;
        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parse();


        String actual = parser.VMOutput();
        String expected = """
            function Main.soma 0
            function Main.main 1
                """;
        assertEquals(expected, actual);
 
 
    }
    
}
