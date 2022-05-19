package br.ufma.ecp;

import static br.ufma.ecp.TokenType.*;

public class Parser {

    private Scanner scan;
    private Token currentToken;
    private Token peekToken;
    private String xmlOutput;

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
        printNonTerminal("letStatement");
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
        printNonTerminal("/letStatement");
    }

    // 'while' '(' expression ')' '{' statements '}'
    void parseWhile () {
        printNonTerminal("whileStatement");
        expectPeek(WHILE);
        expectPeek(LPAREN);
        parseExpression();
        expectPeek(RPAREN);
        expectPeek(LBRACE);
        parseStatements();
        expectPeek(RBRACE);
        printNonTerminal("/whileStatement");
    }

    void parseIf () {
        printNonTerminal("ifStatement");
        expectPeek(IF);
        expectPeek(LPAREN);
        parseExpression();
        expectPeek(RPAREN);
        expectPeek(LBRACE);
        parseStatements();
        expectPeek(RBRACE);
        printNonTerminal("/ifStatement");
    }


    void parseStatements () {
        printNonTerminal("statements");
        while (peekToken.type == WHILE ||
        peekToken.type == IF ||
        peekToken.type == LET ||
        peekToken.type == DO ||
        peekToken.type == RETURN ) {
            parseStatement();
        }
        
        printNonTerminal("/statements");
    }

    void parseStatement () {
        switch (peekToken.type) {
            case LET:
                parseLet();
                break;
            case WHILE:
                parseWhile();
                break;
            case IF:
                parseIf();
                break;
            case RETURN:
                parseReturn();
                break;
            default:
                throw new Error("Syntax error - expected a statement");
        }
    }
    //ReturnStatement -> 'return' expression? ';'
    void parseReturn () {
        printNonTerminal("returnStatement");
        expectPeek(RETURN);
        if (!peekTokenIs(SEMICOLON)) {
            parseExpression();
        }
        expectPeek(SEMICOLON);

        printNonTerminal("/returnStatement");
    }

    // expression -> term (op term)*
    void parseExpression() {
        printNonTerminal("expression");
        parseTerm ();
        while (isOperator(peekToken.type)) {
            expectPeek(peekToken.type);
            parseTerm();
        }
        printNonTerminal("/expression");
    }



     // term -> number | identifier | stringConstant | keywordConstant
    void parseTerm () {
        printNonTerminal("term");
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
        printNonTerminal("/term");
    }

    // funções auxiliares

    public String XMLOutput () {
        return xmlOutput;
    }

    private void printNonTerminal (String nterminal) {
        //System.out.println("<"+nterminal+">");
        xmlOutput += String.format ("<%s>\n",nterminal);
    }

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
            //System.out.println(currentToken);
            xmlOutput += String.format ("%s\n",currentToken.toString());
        } else {
            throw new Error("Syntax error - expected "+type+" found " + peekToken.lexeme);
        }
    }

  

}