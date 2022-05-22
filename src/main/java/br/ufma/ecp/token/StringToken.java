package br.ufma.ecp.token;

public class StringToken extends Token {

    String lexeme;

    public StringToken(TokenType type, String lexeme) {
        super(type);
        this.lexeme = lexeme;

    }

    public String toString() {
        return "<string> " + lexeme  + " </string>";
    }
    
}
