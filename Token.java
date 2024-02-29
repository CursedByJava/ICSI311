public class Token {

    //Enum to hold the token type
    public enum TokenType {
        WORD, NUMBER, ENDOFLINE
    }
    TokenType tokenType;
    public int lineNumber;
    public int characterPosition;
    String tokenValue;

    //Creates a token with a value
    public Token(TokenType type, int line, int pos) {
        tokenType = type;
        lineNumber = line;
        characterPosition = pos;
    }

    //Creates a token with no value
    public Token(TokenType type, int line, int pos, String value) {
        tokenType = type;
        lineNumber = line;
        characterPosition = pos;
        tokenValue = value;
    }

    public String toString(){
        String tokenString = tokenType + "(" + tokenValue + ")" + "[LINE: " + lineNumber + ", POS: " + characterPosition + "]" + "\n";
        return tokenString;
    }



}
