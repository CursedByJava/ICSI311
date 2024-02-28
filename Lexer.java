import java.util.LinkedList;

public class Lexer {

    enum states {
        LETTER, DIGIT, INITIAL, SPACE, ENDOFLINE, TAB, NEWLINE
    }



    static Token ProcessWord(String process) {
        return new Token(Token.TokenType.WORD, Token.lineNumber, Token.characterPosition, process);
    }
    public static LinkedList lex(String filename) {

        String wordBuffer = "";

        LinkedList<Token> TokenList = new LinkedList<Token>();
        System.out.println("============================\nFILE NAME:" + filename + "\n============================");

        //Holds the line number and character position (duh)
        int characterPosition;

        int index = 0;

        CodeHandler code = new CodeHandler();
        String file = code.file();
        char currentChar = file.charAt(index);
        states currentState = states.INITIAL;

        int lineNumber = 0;

        //State machine HERE
        while (!code.IsDone()) {
            switch (currentState) {
                case INITIAL:
                    if (Character.isAlphabetic(currentChar)) {
                        System.out.println(currentChar + " LETTER");
                        currentState = states.LETTER;
                        wordBuffer += currentChar;
                        break;
                    } else if (Character.isDigit(currentChar)) {
                        System.out.println(currentChar + " DIGIT");
                        currentState = states.DIGIT;
                        break;
                    } else if (Character.isSpaceChar(currentChar) | currentChar == '\t') {
                        System.out.println("SPACE OR TAB");
                        currentState = states.SPACE;
                        break;
                    } else if (currentChar == '\n') {
                        System.out.println("NEWLINE");
                        lineNumber++;
                        TokenList.add(new Token(Token.TokenType.ENDOFLINE, lineNumber, Token.characterPosition));
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    } else {
                        System.out.println(currentChar);
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    }
                case LETTER:
                    if (Character.isAlphabetic(code.peek(1))) {
                        currentChar = code.GetChar(1);
                        System.out.println(currentChar + " LETTER");
                        wordBuffer += currentChar;
                        break;
                    } else {
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        ProcessWord(wordBuffer);
                        wordBuffer = "";
                        break;
                    }
                case DIGIT:
                    if (Character.isDigit(code.peek(1))) {
                        currentChar = code.GetChar(1);
                        System.out.println(currentChar + " DIGIT");
                        break;
                    } else {
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    }
                case SPACE:
                    currentChar = code.GetChar(1);
                    currentState = states.INITIAL;
                    break;
            }
        }
        System.out.println(TokenList);
        return TokenList;
    }

}
