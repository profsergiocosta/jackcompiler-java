package br.ufma.ecp;

import java.io.EOFException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementScanner14;

public class Scanner {

    private byte[] input;
    private int current;
    private int start;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("while", TokenType.WHILE);
    }

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

            case '/':
                if (peekNext() == '/') {
                    skipLineComments();
                    return nextToken();
                } else {
                    advance();
                    return new Token (TokenType.SLASH,"/");
                }


            case '"':
                return string();
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


    
    private void skipLineComments() {
  
        for (char ch = peek(); ch != '\n' && ch != 0;  advance(), ch = peek()) ;
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

    private Token string () {
        advance();
        start = current;
        while (peek() != '"' && peek() != 0) {
            advance();
        }
        String s = new String(input, start, current-start, StandardCharsets.UTF_8);
        Token token = new Token (TokenType.STRING,s);
        advance();
        return token;
    }

    private Token identifier() {
        while (isAlphaNumeric(peek()) ) {
            advance();
        }
        String id = new String(input, start, current-start, StandardCharsets.UTF_8);
        TokenType type = keywords.get(id);
        if (type == null) type = TokenType.IDENTIFIER;
        Token token = new Token (type,id);
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

    private char peek () {
         if ( current < input.length) {
             return (char)input[current];
         } else {
             return 0;
         }
    }

    private char peekNext () {
        int next = current + 1;
        if ( next  < input.length) {
            return (char)input[next];
        } else {
            return 0;
        }
   }


    
}
