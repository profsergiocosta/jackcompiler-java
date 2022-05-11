package br.ufma.ecp;

import static br.ufma.ecp.TokenType.*;

public class Parser {

    private Scanner scan;
    private Token currentToken;
    private Token peekToken;

    public Parser (byte[] input) {
        scan = new Scanner(input);
        nextToken();
    }

    private void nextToken() {
        currentToken = peekToken;
        peekToken = scan.nextToken();
    }

   

    void parser () {
        parserLetStatement();
    }

    // letStatement -> 'let' varName  '=' term ';'
    // term -> number;
    void parserLetStatement() {
        System.out.println("<LET>");
        expectPeek(LET);
        expectPeek(IDENTIFIER);
        expectPeek(EQ);
        parseTerm();
        expectPeek(SEMICOLON);
        System.out.println("</LET>");

    }

    void parseExpression () {
 
    }

    void parseTerm () {
        System.out.println("<TERM>");
        switch (peekToken.type) {
            case NUMBER:
                expectPeek(NUMBER);
                break;
            default:
                ;

        }
        System.out.println("</TERM>");
    }
    // auxiliares

    boolean currentTokenIs (TokenType type) {
        return currentToken.type == type;
    }


    boolean peekTokenIs (TokenType type) {
        return currentToken.type == type;
    }


    private void expectPeek (TokenType type) {
        if (peekToken.type == type ) {
            nextToken();
            System.out.println(currentToken);
        } else {
            throw new Error("Syntax error - expected "+type+" found " + peekToken.lexeme);
        }
    }

  

}
