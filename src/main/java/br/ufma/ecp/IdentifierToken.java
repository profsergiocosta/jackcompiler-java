package br.ufma.ecp;

public class IdentifierToken extends Token {

    String lexeme;

    public IdentifierToken(TokenType type, String lexeme) {
        super(type);
        this.lexeme = lexeme;

    }

    public String toString() {
        return "<identifier> " + lexeme  + " </identifier>";
    }
    
}
