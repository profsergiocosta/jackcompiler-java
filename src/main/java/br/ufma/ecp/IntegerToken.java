package br.ufma.ecp;

public class IntegerToken extends Token {

    String lexeme;

    public IntegerToken(TokenType type, String lexeme) {
        super(type);
        this.lexeme = lexeme;
    }

    public String toString() {
        return "<integerConstant> " + lexeme  + " </integerConstant>";
    }
    
}
