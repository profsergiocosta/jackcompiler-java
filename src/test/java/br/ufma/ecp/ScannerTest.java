package br.ufma.ecp;

import static org.junit.Assert.assertEquals;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class ScannerTest extends SupportTest {

    @Test
    public void testScannerSimpleCase() {

             String input = "45 preco2 + 96";
             Scanner scan = new Scanner(input.getBytes(StandardCharsets.UTF_8));
             System.out.println("<tokens>");   
             var result = new StringBuilder();
             result.append("<tokens>\r\n");
             for (Token tk = scan.nextToken(); tk.type != TokenType.EOF; tk = scan.nextToken()) {
                result.append(String.format("%s\r\n",tk.toString()));
             }
             result.append("</tokens>\r\n");
             System.out.println(result.toString());       
    }

    @Test
    public void testScannerWithSquareGame() throws IOException {
        var input = fromFile("Square/SquareGame.jack");
        var expectedResult =  fromFile("Square/SquareGameT.xml");

        var scanner = new Scanner(input.getBytes(StandardCharsets.UTF_8));
        var result = new StringBuilder();
        
        result.append("<tokens>\r\n");

        for (Token tk = scanner.nextToken(); tk.type !=TokenType.EOF; tk = scanner.nextToken()) {
            result.append(String.format("%s\r\n",tk.toString()));
        }

        result.append("</tokens>\r\n");
        
        assertEquals(expectedResult, result.toString());
    }


    @Test
    public void testScannerWithSquare() throws IOException {
        var input = fromFile("Square/Square.jack");
        var expectedResult =  fromFile("Square/SquareT.xml");

        var scanner = new Scanner(input.getBytes(StandardCharsets.UTF_8));
        var result = new StringBuilder();
        
        result.append("<tokens>\r\n");

        for (Token tk = scanner.nextToken(); tk.type !=TokenType.EOF; tk = scanner.nextToken()) {
            result.append(String.format("%s\r\n",tk.toString()));
        }
        
        result.append("</tokens>\r\n");
        System.out.println(result.toString());
        assertEquals(expectedResult, result.toString());
    }
    
}
