package br.ufma.ecp.token;

public class IntegerToken extends Token {

    String lexeme;

    public IntegerToken(String lexeme) {
        super(TokenType.NUMBER);
        this.lexeme = lexeme;
    }

    public String toString() {
        return "<integerConstant> " + lexeme  + " </integerConstant>";
    }
    
}
