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
        char currentChar = code.peek(0);
        states currentState = states.INITIAL;
        StringBuilder testString = new StringBuilder();

        StringBuilder charString = new StringBuilder();

        //State machine HERE
        if (!code.IsDone()) {
            for (int i=0; i<filename.length();i++) {

                switch (currentState) {

                    //DONE
                    case INITIAL:

                        System.out.println(currentChar);
                        if(Character.isLetter(currentChar)){
                            //change the state to letter
                            currentState = states.LETTER;
                            charString.append(currentChar);
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
//                        else {
//                            currentChar = code.GetChar(1);
//                        }

                        //DONE
                    case LETTER:
                        //add the letter to currentchar, then if its not a letter move on
                        if(Character.isLetter(code.peek(1)) | (code.peek(1) == '_') | (code.peek(1) == '$') | (code.peek(1) == '%')) {
                            currentChar = code.GetChar(1);
                            charString.append(currentChar);
                        }
                        else {
                            currentChar = code.GetChar(1);
                            TokenList.add(ProcessWord(charString.toString()));
                            charString = new StringBuilder();
                            currentState = states.INITIAL;
                            break;
                        }

                    case DIGIT:
                        if(Character.isDigit(code.peek(1))) {

                        }
                        else {
                            currentState = states.INITIAL;
                        }

                    case SPACE, TAB:
                        if((Character.isSpaceChar(code.peek(1)) | (code.peek(1) == '\t'))) {
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

    static Token ProcessWord(String processString) {
        return new Token(Token.TokenType.WORD, Token.lineNumber, Token.characterPosition, processString);
    }
}
