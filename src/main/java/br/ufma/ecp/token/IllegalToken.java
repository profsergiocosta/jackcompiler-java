package br.ufma.ecp.token;

public class IllegalToken extends Token {

    char c;
    public IllegalToken(TokenType type, char c) {
        super(type);
        this.c = c;
    }
    
}