public class Token {

    //Enum to hold the token type
    public enum TokenType {
        WORD, NUMBER, ENDOFLINE
    }
    static TokenType tokenType;
    public static int lineNumber;
    public static int characterPosition;
    static String tokenValue;

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
        String tokenString = "Token Name: " + tokenType + " Token Value: " + tokenValue + " Line Number: " + lineNumber + " Position: " + characterPosition + "\n";
        return tokenString;
    }

}
