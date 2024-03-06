public class Token {

    //Enum to hold the token type
    enum TokenType {
        //Lexer 1 basic token types
        WORD, NUMBER, ENDOFLINE,
        //Lexer 2 advanced token types
        PRINT, READ, INPUT, DATA, GOSUB, FOR, TO, STEP, NEXT, RETURN, IF, THEN, FUNCTION, WHILE, END,
        STRINGLITERAL,
        //SINGLE SYMBOLS
        GREATERTHAN,LESSTHAN,LEFTPARENTHESIS,RIGHTPARENTHESIS,EQUALS,PLUS,MINUS,TIMES,FORWARDSLASH,DOLLARSIGN,LABEL,
        //DOUBLE SYMBOLS
        LESSTHANEQUALTO,GREATERTHANEQUALTO,NOTEQUALS,DOUBLEEQUALS,DOUBLEAND,DOUBLEPLUS,PLUSEQUALS,

        IDENTIFIER, COMMA,
    }

    //ADDS TYPE OF TOKEN
    TokenType tokenType;
    //KEEPS TRACK OF WHAT LINE WE'RE ON
    int lineNumber;
    //Shows the position of each caharacter
    int characterPosition;
    //Stores the value of the token as a string
    String tokenValue;

    //Creates a token with a value
    Token(TokenType type, int line, int pos) {
        tokenType = type;
        lineNumber = line;
        characterPosition = pos;
    }

    //Creates a token with no value
    Token(TokenType type, int line, int pos, String value) {
        tokenType = type;
        lineNumber = line;
        characterPosition = pos;
        tokenValue = value;
    }
    //Makes a string of tokens
    public String toString(){
        String tokenString = tokenType + "(" + tokenValue + ")" + "[LINE: " + lineNumber + ", POS: " + characterPosition + "]" + "\n";
        return tokenString;
    }



}
