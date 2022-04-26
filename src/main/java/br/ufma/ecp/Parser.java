package br.ufma.ecp;

public class Parser {

    private byte[] input;
    private int current;

    public Parser (byte[] input) {
        this.input = input;
    }

    private void match (char c) {
        if (c == peek ()) {
            current++;
        } else {
            throw new Error("Syntax error");
        }
    }

    private char peek () {
         if ( current < input.length) {
             return (char)input[current];
         } else {
             return 0;
         }
    }

    void parser () {
        expr();
    }

    void expr () {
          digit();
          oper();
    }

    /*
    oper -> + digit oper
     | - digit oper
     | ϵ 
     */
    void oper () {
        if (peek () == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (peek () == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } else if (peek () == 0) {
            // to de boa
        } else {
            throw new Error ("syntax error");
        }

    }

 
    void digit () {
        if (Character.isDigit(peek())) {
            System.out.println("push " + peek());
            match (peek());
        } else {
            throw new Error ("syntax error");  
        }
    }

  

}
