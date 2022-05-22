package br.ufma.ecp;

import java.nio.charset.StandardCharsets;


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

        
            case '"':
                return string();

            case '/':
                if (peekNext() == '/') {
                    skipLineComments();
                    return nextToken();
                } else if (peekNext() == '*') {
                    skipBlockComments();
                    return nextToken();
                }
                else {
                    advance();
                    return new Token (TokenType.SLASH);
                }

            case '+':
                advance();
                return new SymbolToken (TokenType.PLUS);
            case '-':
                advance();
                return new SymbolToken (TokenType.MINUS); 
            case '*':
                advance();
                return new SymbolToken (TokenType.ASTERISK); 
            case '.':
                advance();
                return new SymbolToken (TokenType.DOT); 
            case '&':
                advance();
                return new SymbolToken (TokenType.AND); 
            case '|':
                advance();
                return new SymbolToken (TokenType.OR); 
            case '~':
                advance();
                return new SymbolToken (TokenType.NOT); 


            case '>':
                advance();
                return new SymbolToken (TokenType.GT); 
            case '<':
                advance();
                return new SymbolToken (TokenType.LT); 
            case '=':
                advance();
                return new SymbolToken (TokenType.EQ); 

            case '(':
                advance();
                return new SymbolToken (TokenType.LPAREN); 
            case ')':
                advance();
                return new SymbolToken (TokenType.RPAREN); 
            case '{':
                advance();
                return new SymbolToken (TokenType.LBRACE); 
            case '}':
                advance();
                return new SymbolToken (TokenType.RBRACE); 
            case '[':
                advance();
                return new SymbolToken (TokenType.LBRACKET); 
            case ']':
                advance();
                return new SymbolToken (TokenType.RBRACKET); 
            case ';':
                advance();
                return new SymbolToken (TokenType.SEMICOLON); 
            case ',':
                advance();
                return new SymbolToken (TokenType.COMMA); 

            case 0:
                return new Token(TokenType.EOF);  
            default:
                advance(); 
                return new IllegalToken(TokenType.ILLEGAL,ch);
        }


      

    }


    private void skipBlockComments() {
        boolean endComment = false;
        advance();

        while (!endComment) {
            advance();
            char ch = peek();

            if ( ch == 0) { // eof
                System.exit(1);
            }
    
         
            if (ch == '*') {

               for (ch = peek(); ch == '*';  advance(), ch = peek()) ;

             
                if (ch == '/') {
                    endComment = true;
                    advance();
                }
            }

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
        Token token = new StringToken (TokenType.STRING,s);
        advance();
        return token;
    }

    private Token identifier() {
        while (isAlphaNumeric(peek()) ) {
            advance();
        }
        String id = new String(input, start, current-start, StandardCharsets.UTF_8);
        TokenType type = Token.keyword(id);
        if (type == null) {
            type = TokenType.IDENTIFIER;
            return new IdentifierToken (type,id);
        } else {
            return new KeywordToken (type);
        }
        
    }

    private Token number () {
        while (Character.isDigit(peek())) {
            advance();
        }
        String s = new String(input, start, current-start, StandardCharsets.UTF_8);
        Token token = new IntegerToken (TokenType.NUMBER,s);
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
