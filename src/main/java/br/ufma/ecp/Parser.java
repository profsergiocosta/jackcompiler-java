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


    void parse () {
        parseStatements();
    }

    //letStatement -> 'let' identifier( '[' expression ']' )?  '=' expression ';'
    void parseLet() {
        System.out.println("<letStatement>");
        expectPeek(LET);
        expectPeek(IDENTIFIER);

        if (peekTokenIs (LBRACKET)) {
            expectPeek(LBRACKET);
            parseExpression();
            expectPeek(RBRACKET);
        }

        expectPeek(EQ);
        parseExpression();
        expectPeek(SEMICOLON);
        System.out.println("</letStatement>");
    }

    // 'while' '(' expression ')' '{' statements '}'
    void parseWhile () {
        System.out.println("<whileStatement>");
        expectPeek(WHILE);
        expectPeek(LPAREN);
        parseExpression();
        expectPeek(RPAREN);
        expectPeek(LBRACE);
        parseStatements();
        expectPeek(RBRACE);
        System.out.println("</whileStatement>");
    }

    void parseStatements () {
        System.out.println("<statements>");
        while (peekToken.type == WHILE ||
        peekToken.type == IF ||
        peekToken.type == LET ||
        peekToken.type == DO ||
        peekToken.type == RETURN ) {
            parseStatement();
        }
        
        System.out.println("</statements>");
    }

    void parseStatement () {
        switch (peekToken.type) {
            case LET:
                parseLet();
                break;
            case WHILE:
                parseWhile();
                break;
            default:
                return;
        }
    }


    // expression -> term (op term)*
    void parseExpression() {
        System.out.println("<expression>");
        parseTerm ();
        while (isOperator(peekToken.type)) {
            expectPeek(peekToken.type);
            parseTerm();
        }
        System.out.println("</expression>");
    }



     // term -> number | identifier | stringConstant | keywordConstant
    void parseTerm () {
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

    private boolean isOperator (TokenType type) {
        return type.ordinal() >= PLUS.ordinal() && type.ordinal() <= EQ.ordinal();
    }

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