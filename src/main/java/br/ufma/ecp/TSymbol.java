package br.ufma.ecp;

public class TSymbol extends Token {

    public TSymbol(TokenType type, String lexeme) {
        super(type, lexeme);
    }

    public String toString() {
        return "<symbol> " + type.c  + " </symbol>";
    }
    
}
