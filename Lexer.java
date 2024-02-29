import java.util.LinkedList;

public class Lexer {

    enum states {
        LETTER, DIGIT, INITIAL, SPACE, DECIMAL,
    }

    public LinkedList<Token> TokenList = new LinkedList<>();
    public int index = CodeHandler.fileIndex();

    public Token newToken = null;

    Token ProcessWord(String process) {
        position++;
        return new Token(Token.TokenType.WORD,lineNumber,position,process);
    }
    Token ProcessNumber(String number) {
        position++;
        return new Token(Token.TokenType.NUMBER,lineNumber,position,number);
    }
    public String wordBuffer = "";
    public String numberBuffer = "";

    public int lineNumber = 1;
    public int position = 0;

    public LinkedList lex(String filename) {


        System.out.println("============================\nFILE NAME:" + filename + "\n============================");

        //Holds the line number and character position (duh)
        int characterPosition;

        CodeHandler code = new CodeHandler();
        String file = code.file();
        char currentChar = file.charAt(index);
        states currentState = states.INITIAL;

        int decimalCount = 0;


        //State machine HERE
        while (!code.IsDone()) {
            switch (currentState) {
                case INITIAL:
                    if (Character.isAlphabetic(currentChar)) {
//                        System.out.println(currentChar + " LETTER" + " INDEX: " + CodeHandler.publicIndex);
                        currentState = states.LETTER;
                        wordBuffer += currentChar;
                        break;
                    }
                    else if (Character.isDigit(currentChar)) {
//                        System.out.println(currentChar);
                        currentState = states.DIGIT;
                        numberBuffer += currentChar;
                        break;
                    }
                    else if (Character.isSpaceChar(currentChar) | currentChar == '\t') {
//                        System.out.println("SPACE OR TAB");
                        currentState = states.SPACE;
                        break;
                    }
                    else if (currentChar == '\n') {
                        System.out.println("NEWLINE");
                        //Same as processword with no value
                        position++;
                        TokenList.add(new Token(Token.TokenType.ENDOFLINE, lineNumber, position));
                        lineNumber++;
                        position = 0;
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
                    if (Character.isAlphabetic(code.peek(1)) | Character.isDigit(code.peek(1)) | (code.peek(1) == '$') | (code.peek(1) == '_') | code.peek(1) == '$') {
                        currentChar = code.GetChar(1);
//                        System.out.println(currentChar + " LETTER");
                        wordBuffer += currentChar;
                        break;
                    }
                    else {
                        TokenList.add(ProcessWord(wordBuffer));
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        System.out.println("WORD: " + wordBuffer);
                        wordBuffer = "";
                        break;
                    }
                case DIGIT:
                    if (Character.isDigit(code.peek(1))) {
                        currentChar = code.GetChar(1);
//                        System.out.println(currentChar);
                        numberBuffer += currentChar;
                        break;
                    }
                    else if ((code.peek(1) == '.') & (decimalCount < 1)) {
                        decimalCount++;
                        currentChar = code.GetChar(1);
//                        System.out.println(currentChar);
                        numberBuffer += currentChar;
                        break;
                    }
                    else {
                        TokenList.add(ProcessNumber(numberBuffer));
                        decimalCount = 0;
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        System.out.println("NUMBER: " + numberBuffer);
                        numberBuffer = "";
                        break;
                    }
                case SPACE:
                    currentChar = code.GetChar(1);
                    currentState = states.INITIAL;
                    break;
            }
        }
        //Prints out last word if the buffer isn't empty
        if (wordBuffer != "") {
            position++;
            TokenList.add(new Token(Token.TokenType.WORD, lineNumber, position, wordBuffer));
            System.out.println("WORD: " + wordBuffer);
        }
        else if (numberBuffer != "") {
            position++;
            TokenList.add(new Token(Token.TokenType.NUMBER, lineNumber, position, numberBuffer));
            System.out.println("NUMBER: " + numberBuffer);
        }

        System.out.println(TokenList.toString());
        return TokenList;
    }

}
