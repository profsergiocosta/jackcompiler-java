package br.ufma.ecp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


import static br.ufma.ecp.TokenType.*;
public class App 
{

    private static String fromFile() {
        File file = new File("Main.jack");

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
            String textoDoArquivo = new String(bytes, "UTF-8");
            return textoDoArquivo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    } 

    public static void main( String[] args )
    {
        var input = fromFile();
        var scanner = new Scanner(input.getBytes(StandardCharsets.UTF_8));
        var result = new StringBuilder();
        
        result.append("<tokens>\r\n");

        for (Token tk = scanner.nextToken(); tk.type !=TokenType.EOF; tk = scanner.nextToken()) {
            result.append(String.format("%s\r\n",tk.toString()));
        }

        result.append("</tokens>\r\n");
        System.out.println(result.toString());

        
        //String input = "let a[4] = 10 - 5;";
        //String input = "if (10) { let a[4] = 10 - 5; }";
        //String input = "let a = ;";
        //Parser p = new Parser(input.getBytes());
        //p.parse();
       // System.out.println(p.XMLOutput());
        
        //String input = "45 preco2 + 96";


        /*
        Scanner scan = new Scanner(fromFile().getBytes());
        System.out.println("<tokens>");        
        for (Token tk = scan.nextToken(); tk.type != EOF; tk = scan.nextToken()) {
            System.out.println(tk);
        }
        System.out.println("</tokens>");        
        */
    }
}
