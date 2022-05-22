package br.ufma.ecp;

public class TStringConst extends Token {

    public TStringConst(TokenType type, String lexeme) {
        super(type, lexeme);
    }

    public String toString() {
        return "<string> " + lexeme  + " </string>";
    }
    
}
