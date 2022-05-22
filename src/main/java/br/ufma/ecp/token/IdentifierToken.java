package br.ufma.ecp.token;

public class IdentifierToken extends Token {

    String lexeme;

    public IdentifierToken(String lexeme) {
        super(TokenType.IDENTIFIER);
        this.lexeme = lexeme;

    }

    public String toString() {
        return "<identifier> " + lexeme  + " </identifier>";
    }
    
}
