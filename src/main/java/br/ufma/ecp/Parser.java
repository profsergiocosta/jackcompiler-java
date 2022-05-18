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
        parserLet();
    }

    //letStatement -> 'let' identifier  '=' expression ';'
    void parserLet() {
        System.out.println("<letStatement>");
        expectPeek(LET);
        expectPeek(IDENTIFIER);
        expectPeek(EQ);
        parserExpression();
        expectPeek(SEMICOLON);
        System.out.println("</letStatement>");
    }
    // expression -> term (op term)*

    void parserExpression() {
        System.out.println("<expression>");
        parserTerm ();
        while (peekTokenIs(PLUS)) {
            expectPeek(PLUS);
            parserTerm();
        }
        System.out.println("</expression>");
    }

     // term -> number | identifier | stringConstant | keywordConstant
    void parserTerm () {
        System.out.println("<term>");
        switch (peekToken.type) {
            case NUMBER:
                expectPeek(NUMBER);
                break;
            case IDENTIFIER:
                expectPeek(IDENTIFIER);
                break;
            case STRING:
                expectPeek(STRING);
                break;
            case FALSE:
            case NULL:
            case TRUE:
                expectPeek(FALSE,NULL,TRUE);
                break;
            default:
                ;
        }
        System.out.println("</term>");
    }

    // funções auxiliares

    boolean peekTokenIs (TokenType type) {
        return peekToken.type == type;
    }
   
    boolean currentTokenIs (TokenType type) {
        return currentToken.type == type;
    }

    private void expectPeek (TokenType... types) {
        for (TokenType type : types) {
            if (peekToken.type == type ) {
                expectPeek(type);
                return;
            }
        }
       
        throw new Error("Syntax error");
       
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