package br.ufma.ecp;

public enum TokenType {
 
    STRING,

    NUMBER,

    IDENTIFIER,

    // keywords
    WHILE, CLASS,CONSTRUCTOR,FUNCTION,
    METHOD,FIELD,STATIC,VAR,INT,
    CHAR,BOOLEAN,VOID,TRUE,FALSE,
    NULL,THIS,LET,DO,IF,ELSE, RETURN,


     // symbols
    // delimitator

    LPAREN,
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



    EOF, 

    ILLEGAL
    
}
