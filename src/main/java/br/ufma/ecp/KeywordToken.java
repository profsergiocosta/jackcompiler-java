package br.ufma.ecp;

public class KeywordToken extends Token {

    public KeywordToken(TokenType type) {
        super(type);
    }

    public String toString() {
        return "<keyword> " + type.valueOf  + " </keyword>";
    }
    
}
