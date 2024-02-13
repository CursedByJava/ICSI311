import java.util.LinkedList;

public class Lexer {

    enum states {
        LETTER, DIGIT, INITIAL, SPACE, ENDOFLINE, TAB, NEWLINE
    }

    public static LinkedList lex(String filename) {

        LinkedList<Token> TokenList = new LinkedList<Token>();
        System.out.println("FILE NAME: " + filename);

        //Holds the line number and character position (duh)
        int lineNumber;
        int characterPosition;

        CodeHandler code = new CodeHandler();
        char currentChar = code.peek(0);;
        states currentState = states.INITIAL;

        if (!code.IsDone()) {
            for (int i=0; i<filename.length();i++) {

                System.out.println(currentChar);
//                currentChar = code.GetChar(1);

                switch (currentState) {

                    case INITIAL:
                        if(Character.isLetter(currentChar)){
                            //change the state to letter
                            currentState = states.LETTER;
                            ProcessWord(currentChar);
                        }
                        else if(Character.isDigit(currentChar)){
                            currentState = states.DIGIT;
                        }
                        else if((Character.isSpaceChar(currentChar)) | (currentChar == '\t')) {
                            currentState = states.SPACE;
                        }
                        else if(currentChar == '\n') {
                            currentState = states.ENDOFLINE;
                        }

                    case LETTER:
                        //add the letter to currentchar, then if its not a letter move on
                        if(Character.isAlphabetic(code.peek(1)) | (code.peek(1) == '_') | (code.peek(1) == '$') | (code.peek(1) == '%')) {
                            currentChar = code.GetChar(1);
                        }
                        else {
                            currentChar = code.GetChar(1);
                            currentState = states.INITIAL;
                            charString = new StringBuilder();
                            ProcessWord(currentChar);
                        }

                    case DIGIT:

                    case SPACE, TAB:
                        if(!Character.isSpaceChar(code.peek(1))){
                            currentChar = code.GetChar(1);
                            currentState = states.INITIAL;
                        }
                    case ENDOFLINE:
                }
            }
        }
        System.out.println(TokenList);
        return null;
    }

    private static StringBuilder charString = new StringBuilder();
    static Token ProcessWord(char convertedChar) {
        if((Character.isAlphabetic(convertedChar) | (convertedChar == '_') | (convertedChar == '$') | (convertedChar== '%'))) {
            charString.append(convertedChar);
        }
        else {
            return new Token(Token.TokenType.WORD, Token.lineNumber, Token.characterPosition, charString.toString());
        }
        return null;
    }
}
