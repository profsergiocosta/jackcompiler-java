package br.ufma.ecp;

import static br.ufma.ecp.TokenType.*;

public class Parser {

    private Scanner scan;
    private Token currentToken;
    private Token peekToken;
    private String xmlOutput = "";

    public Parser(byte[] input) {
        scan = new Scanner(input);
        nextToken();
    }

    void parse() {
     
    }

    // term -> number | identifier
    void parseTerm() {
        printNonTerminal("term");
        switch (peekToken.type) {
            case NUMBER:
                expectPeek(NUMBER);
                break;
            case IDENTIFIER:
                expectPeek(IDENTIFIER);
                break;
            default:
                ;
        }
        printNonTerminal("/term");
    }

    public String XMLOutput() {
        return xmlOutput;
    }

    private void printNonTerminal(String nterminal) {
        xmlOutput += String.format("<%s>\r\n", nterminal);
    }

    private void nextToken() {
        currentToken = peekToken;
        peekToken = scan.nextToken();
    }



    boolean peekTokenIs(TokenType type) {
        return peekToken.type == type;
    }

    private void expectPeek(TokenType... types) {
        for (TokenType type : types) {
            if (peekToken.type == type) {
                expectPeek(type);
                return;
            }
        }

        throw new Error("Syntax error");

    }

    private void expectPeek(TokenType type) {
        if (peekToken.type == type) {
            nextToken();
            // System.out.println(currentToken);
            xmlOutput += String.format("%s\r\n", currentToken.toString());
        } else {
            throw new Error("Syntax error - expected " + type + " found " + peekToken.lexeme);
        }
    }

}
