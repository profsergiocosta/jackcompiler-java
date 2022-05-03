package br.ufma.ecp;

import java.util.Arrays;
import java.util.List;

public class Token {

    final TokenType type;
    final String lexeme;

    List<String> symbols = Arrays.asList("+","-");

    public Token (TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public String toString() {
        String categoria = type.toString();
        if (symbols.contains(lexeme)) {
            categoria = "symbol";
        } else if (categoria == "NUMBER") {
            categoria = "integerConstant";
        }
        return "<" + categoria + ">" + lexeme  + "</" + categoria + ">";
    }
    
}
