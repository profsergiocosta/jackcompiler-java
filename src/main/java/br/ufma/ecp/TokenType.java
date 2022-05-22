package br.ufma.ecp;

public enum TokenType {
 
    STRING,

    NUMBER,

    IDENTIFIER,

    // keywords
    WHILE("while"), CLASS("class"),CONSTRUCTOR("constructor"),FUNCTION("function"),
    METHOD("method"),FIELD("field"),STATIC("static"),VAR("var"),INT("int"),
    CHAR("char"),BOOLEAN("boolean"),VOID("void"),TRUE("true"),FALSE("false"),
    NULL("null"),THIS("this"),LET("let"),DO("do"),IF("if"),
    ELSE("else"), RETURN("return"),


    PLUS("+"),
    EQ("="),
    MINUS("-"),
    ASTERISK("*"),
    SLASH("/"),
    AND("&"),
    OR("|"),
    NOT("~"),
    
    LT("<"),
    GT(">"),

    DOT("."),
    COMMA(","),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),


    EOF, 

    ILLEGAL;


     // symbols
    // delimitator
/*
    LPAREN('('),
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,

    COMMA,
    SEMICOLON,
    DOT,

    // operators
    PLUS,
    MINUS,
    ASTERISK,
    SLASH,

    AND,
    OR,
    NOT,

    LT,
    GT,
    EQ,

*/



    private TokenType() {
    }

    private TokenType(String valueOf) {
        this.valueOf = valueOf;
    }

    String valueOf;
    
}
