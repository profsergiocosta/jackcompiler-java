package br.ufma.ecp;

public class SymbolToken extends Token {

    public SymbolToken(TokenType type) {
        super(type);
    }

    public String toString() {
        return "<symbol> " + type.valueOf  + " </symbol>";
    }
    
}
