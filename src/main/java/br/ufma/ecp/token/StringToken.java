package br.ufma.ecp.token;


public class StringToken extends Token {

    String lexeme;

    public StringToken(String lexeme) {
        super(TokenType.STRING);
        this.lexeme = lexeme;

    }

    public String toString() {
        return "<string> " + lexeme  + " </string>";
    }
    
}
