import java.util.LinkedList;

public class Lexer {

    enum states {
        LETTER, DIGIT, INITIAL, SPACE,
    }

    public static int lineNumber = 1;

    public static LinkedList<Token> TokenList = new LinkedList<>();
    public static int index = CodeHandler.fileIndex();

    public static Token newToken = null;

    static Token ProcessWord(String process) {
        newToken = new Token(Token.TokenType.WORD,1,1,process);
        return newToken;
    }
    public static String wordBuffer = "";
    public static LinkedList lex(String filename) {


        System.out.println("============================\nFILE NAME:" + filename + "\n============================");

        //Holds the line number and character position (duh)
        int characterPosition;

        CodeHandler code = new CodeHandler();
        String file = code.file();
        char currentChar = file.charAt(index);
        states currentState = states.INITIAL;


        //State machine HERE
        while (!code.IsDone()) {
            switch (currentState) {
                case INITIAL:
                    if (Character.isAlphabetic(currentChar)) {
                        System.out.println(currentChar + " LETTER" + " INDEX: " + CodeHandler.publicIndex);
                        currentState = states.LETTER;
                        wordBuffer += currentChar;
                        break;
                    }
                    else if (Character.isDigit(currentChar)) {
                        System.out.println(currentChar + " DIGIT");
                        currentState = states.DIGIT;
                        break;
                    }
                    else if (Character.isSpaceChar(currentChar) | currentChar == '\t') {
                        System.out.println("SPACE OR TAB");
                        currentState = states.SPACE;
                        break;
                    }
                    else if (currentChar == '\n') {
                        System.out.println("NEWLINE");
                        lineNumber++;
                        //Same as processword with no value

                        TokenList.add(new Token(Token.TokenType.ENDOFLINE, lineNumber, index));
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    }
                    else {
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
                    }
                    else {
                        ProcessWord(wordBuffer);
                        TokenList.add(newToken);
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        System.out.println("WORD: " + wordBuffer);
                        wordBuffer = "";
                        break;
                    }
                case DIGIT:
                    if (Character.isDigit(code.peek(1))) {
                        currentChar = code.GetChar(1);
                        System.out.println(currentChar + " DIGIT");
                        break;
                    }
                    else {
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

        return TokenList;
    }

}
