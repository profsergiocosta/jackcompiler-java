package br.ufma.ecp;

import java.io.EOFException;
import java.nio.charset.StandardCharsets;

import javax.lang.model.type.TypeKind;

public class Scanner {

    private byte[] input;
    private int current;
    private int start;

    public Scanner (byte[] input) {
        this.input = input;
        current = 0;
        start = 0;
    }
    // + - numeros (1, 12, 678)
    public Token nextToken () {

        skipWhitespace();

        start = current;
        char ch = peek();

        if (Character.isDigit(ch)) {
            return number();
        }

        if (Character.isLetter(ch)) {
            return identifier();
        }

        switch (ch) {
            case '+':
                advance();
                return new Token (TokenType.PLUS,"+");
            case '-':
                advance();
                return new Token (TokenType.MINUS,"-"); 
            case 0:
                return new Token(TokenType.EOF, "EOF");  
            default:
                advance(); 
                return new Token(TokenType.ILLEGAL, Character.toString(ch));
        }


      

    }

    private void skipWhitespace() {
        char ch = peek();
        while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            advance();
            ch = peek();
        }
    }

    private boolean isAlphaNumeric(char ch) {
        return Character.isLetter(ch) || Character.isDigit(ch);
    }
    private Token identifier() {
        while (isAlphaNumeric(peek()) ) {
            advance();
        }
        String s = new String(input, start, current-start, StandardCharsets.UTF_8);
        Token token = new Token (TokenType.IDENTIFIER,s);
        return token;
    }

    private Token number () {
        while (Character.isDigit(peek())) {
            advance();
        }
        String s = new String(input, start, current-start, StandardCharsets.UTF_8);
        Token token = new Token (TokenType.NUMBER,s);
        return token;
    }

    private void advance () {
        char ch = peek();
        if (ch != 0) {
            current++;
        }
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


    
}
