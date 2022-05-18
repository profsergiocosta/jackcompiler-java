package br.ufma.ecp;

import java.io.File;
import java.io.IOException;
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

        
        //String input = "let a[4] = 10 - 5;";
        String input = "while (10) { let a[4] = 10 - 5; }";
        Parser p = new Parser(input.getBytes());
        p.parse();
        
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
