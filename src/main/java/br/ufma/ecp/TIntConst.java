package br.ufma.ecp;

public class TIntConst extends Token {

    public TIntConst(TokenType type, String lexeme) {
        super(type, lexeme);
    }

    public String toString() {
        return "<integerConstant> " + lexeme  + " </integerConstant>";
    }
    
}
