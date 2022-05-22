package br.ufma.ecp;

public class Tkeyword extends Token {

    public Tkeyword(TokenType type, String lexeme) {
        super(type, lexeme);
    }

    public String toString() {
        return "<keyword> " + type.valueOf  + " </keyword>";
    }
    
}
