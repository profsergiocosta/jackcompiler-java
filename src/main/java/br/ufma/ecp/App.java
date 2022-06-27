package br.ufma.ecp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class App 
{


    public static void saveToFile(String fileName, String output) {
  
       
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(fileName);
            byte[] strToBytes = output.getBytes();
            outputStream.write(strToBytes);
    
            outputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private static String fromFile(File file) {        

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


    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please provide a single file path argument.");
            System.exit(1);
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            System.err.println("The file doesn't exist.");
            System.exit(1);
        }

        // we need to compile every file in the directory
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isFile() && f.getName().endsWith(".jack")) {

                    var inputFileName = f.getAbsolutePath();
                    var pos = inputFileName.indexOf('.');
                    var outputFileName = inputFileName.substring(0, pos) + ".vm";
                    
                    System.out.println("compiling " +  inputFileName);
                    var input = fromFile(f);
                    var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
                    parser.parse();
                    var result = parser.VMOutput();
                    saveToFile(outputFileName, result);
                }

            }
        // we only compile the single file
        } else if (file.isFile()) {
            if (!file.getName().endsWith(".jack"))  {
                System.err.println("Please provide a file name ending with .jack");
                System.exit(1);
            } else {
                var inputFileName = file.getAbsolutePath();
                var pos = inputFileName.indexOf('.');
                var outputFileName = inputFileName.substring(0, pos) + ".vm";
                
                System.out.println("compiling " +  inputFileName);
                var input = fromFile(file);
                var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
                parser.parse();
                var result = parser.VMOutput();
                saveToFile(outputFileName, result);
                
            }
        }
    }


    /*



    public static void main( String[] args )
    {


 

        var input = """
            if ( direction = 1) { 
            // teste    
            let string = 10;
        
            }
        """
            ;
      

        var parser = new Parser(input.getBytes(StandardCharsets.UTF_8));
        parser.parseIf();
        var result = parser.XMLOutput();
        System.out.println(result);

        /*
        var a = TokenType.RBRACKET;
        System.out.println(a.valueOf);

        
  
     
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


        
        Scanner scan = new Scanner(fromFile().getBytes());
        System.out.println("<tokens>");        
        for (Token tk = scan.nextToken(); tk.type != EOF; tk = scan.nextToken()) {
            System.out.println(tk);
        }
        System.out.println("</tokens>");        
            }*/


}
