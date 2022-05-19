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


    // subroutineCall ->  subroutineName '(' expressionList ')' | (className|varName) '.' subroutineName '(' expressionList ')
    void parseSubroutineCall () {
        if (peekTokenIs (LPAREN)) {
            expectPeek(LPAREN);
            parseExpressionList();
            expectPeek(RPAREN);
        } else {
            // pode ser um metodo de um outro objeto ou uma função
            expectPeek(DOT);
            expectPeek(IDENTIFIER);
            expectPeek(LPAREN);
            parseExpressionList();
            expectPeek(RPAREN);
        }
    }


    void parseDo () {
        printNonTerminal("doStatement");
        expectPeek(DO);
        expectPeek(IDENTIFIER);
        parseSubroutineCall();
        expectPeek(SEMICOLON);

        printNonTerminal("/doStatement");
    }

    //classVarDec → ( 'static' | 'field' ) type varName ( ',' varName)* ';'
    void parseClassVarDec () {
        printNonTerminal("classVarDec");
        expectPeek(FIELD,STATIC);
        // 'int' | 'char' | 'boolean' | className
        expectPeek(INT,CHAR,BOOLEAN,IDENTIFIER);
        expectPeek(IDENTIFIER);

        while (peekTokenIs(COMMA)) {
            expectPeek(COMMA);
            expectPeek(IDENTIFIER);
        }

        expectPeek(SEMICOLON);
        printNonTerminal("/classVarDec");
    }


    void parseSubroutineDec () {
        printNonTerminal("classVarDec");
        expectPeek(CONSTRUCTOR, FUNCTION, METHOD);
        // 'int' | 'char' | 'boolean' | className
        expectPeek(VOID, INT,CHAR,BOOLEAN,IDENTIFIER);
        expectPeek(IDENTIFIER);

        expectPeek(LPAREN);
        parseParameterList();
        expectPeek(RPAREN);
        parseSubroutineBody();

        printNonTerminal("/classVarDec");
    }


    void parseParameterList()
    {
        printNonTerminal("parameterList");


        if (!peekTokenIs(RPAREN)) // verifica se tem pelo menos uma expressao
        {
            expectPeek(INT,CHAR,BOOLEAN,IDENTIFIER);
            expectPeek(IDENTIFIER);
        }


        while (peekTokenIs(COMMA))
        {
            expectPeek(COMMA);
            expectPeek(INT,CHAR,BOOLEAN,IDENTIFIER);
            expectPeek(IDENTIFIER);
        }

        printNonTerminal("/parameterList");
    }

    void parseSubroutineBody () {
        expectPeek(LBRACE);
        parseStatements();
        expectPeek(RBRACE);
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
            case DO:
                parseDo();
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

    void parseExpressionList() {
        printNonTerminal("expressionList");

        if (!peekTokenIs(RPAREN)) // verifica se tem pelo menos uma expressao
        {
            parseExpression();
        }

        // procurando as demais
        while (peekTokenIs(COMMA)) {
            expectPeek(COMMA);
            parseExpression();
        }

        printNonTerminal("/expressionList");
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