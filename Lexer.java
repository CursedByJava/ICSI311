import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.text.Style;
import java.security.Key;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.ToLongBiFunction;

class Lexer {

    //Holds different values of states in the state machine
    enum states {
        LETTER, DIGIT, INITIAL, SPACE, DECIMAL, STRINGLITERAL, SYMBOL,
    }

    //Hashmap to hold the words
    HashMap<String, Token.TokenType> knownWords = new HashMap<>();
    //Hashmap to hold single symbols
    HashMap<String, Token.TokenType> singleSymbols = new HashMap<>();
    //Hashmap to hold double symbols
    HashMap<String, Token.TokenType> doubleSymbols = new HashMap<>();

    //Linked list holds token name, value, line number and position.
    LinkedList<Token> TokenList = new LinkedList<>();
    //Imports the fileIndex from CodeHandler
    int index = CodeHandler.fileIndex();
    //Takes in characters to build a string and output a word when string is interrupted
    Token ProcessWord(String process) {
        //CHECKS IF IT CONTAINS THE WORD
        if (knownWords.containsKey(process)) {
//            System.out.println("YES");
            return new Token(knownWords.get(process),lineNumber,position);
        }
        else {
            return new Token(Token.TokenType.IDENTIFIER,lineNumber,position,process);
        }
    }
    //Takes in characters to build a number and output a number when string is interrupted
    Token ProcessNumber(String number) {
        return new Token(Token.TokenType.NUMBER,lineNumber,position,number);
    }
    //CHECKS FOR STRING LITERALS
    Token HandleStringLiteral(String quotes) {
        return new Token(Token.TokenType.STRINGLITERAL,lineNumber,position,quotes);
    }
    //Initializes buffer for both the numbers and the words
    String wordBuffer = "";
    String stringBuffer = "";
    String numberBuffer = "";
    String symbolBuffer = "";

    //Initializes the linenumber to start at 1 and the character position to start at 0
    int lineNumber = 1;
    int position = 0;

    int quickFix = 0;

    LinkedList lex(String filename) {

        //Adding default values to hashmap
        knownWords.put("PRINT", Token.TokenType.PRINT);
        knownWords.put("READ", Token.TokenType.READ);
        knownWords.put("INPUT", Token.TokenType.INPUT);
        knownWords.put("DATA", Token.TokenType.DATA);
        knownWords.put("GOSUB", Token.TokenType.GOSUB);
        knownWords.put("FOR", Token.TokenType.FOR);
        knownWords.put("TO", Token.TokenType.TO);
        knownWords.put("STEP", Token.TokenType.STEP);
        knownWords.put("NEXT", Token.TokenType.NEXT);
        knownWords.put("RETURN", Token.TokenType.RETURN);
        knownWords.put("IF", Token.TokenType.IF);
        knownWords.put("THEN", Token.TokenType.THEN);
        knownWords.put("FUNCTION", Token.TokenType.FUNCTION);
        knownWords.put("WHILE", Token.TokenType.WHILE);
        knownWords.put("END", Token.TokenType.END);

        //Adding single symbols
        singleSymbols.put("<", Token.TokenType.LESSTHAN);
        singleSymbols.put(">", Token.TokenType.GREATERTHAN);
        singleSymbols.put("(", Token.TokenType.LEFTPARENTHESIS);
        singleSymbols.put(")", Token.TokenType.RIGHTPARENTHESIS);
        singleSymbols.put("=", Token.TokenType.EQUALS);
        singleSymbols.put("+", Token.TokenType.PLUS);
        singleSymbols.put("-", Token.TokenType.MINUS);
        singleSymbols.put("*", Token.TokenType.TIMES);
        singleSymbols.put("/", Token.TokenType.FORWARDSLASH);
        singleSymbols.put("$", Token.TokenType.DOLLARSIGN);

        //Adding double symbols
        doubleSymbols.put("<=", Token.TokenType.LESSTHANEQUALTO);
        doubleSymbols.put(">=", Token.TokenType.GREATERTHANEQUALTO);
        doubleSymbols.put("<>", Token.TokenType.NOTEQUALS);
        doubleSymbols.put("==", Token.TokenType.DOUBLEEQUALS);
        doubleSymbols.put("&&", Token.TokenType.DOUBLEAND);
        doubleSymbols.put("++", Token.TokenType.DOUBLEPLUS);
        doubleSymbols.put("+=", Token.TokenType.PLUSEQUALS);

        //Displays the file path
        System.out.println("============================\nFILEPATH:" + filename);

        String didntMakeIt = "";
        //Holds the line number and character position (duh)
        int characterPosition;
        //Makes new codehandler
        CodeHandler code = new CodeHandler();
        //Stores the file under name "File"
        String file = code.file();
        //Initiates the current character at the current index
        char currentChar = file.charAt(index);
        //Initializes the state at the inital point
        states currentState = states.INITIAL;
        //Initializes a decimal to start at zero so we can count up to 1 if there is more than 1
        int decimalCount = 0;
        //State machine HERE
        while (!code.IsDone()) {
            switch (currentState) {
                //DEFAULT INITIAL STATE
                case INITIAL:
                    //CHECKS FOR LETTER
                    if (Character.isAlphabetic(currentChar)) {
//                        System.out.println(currentChar + " LETTER" + " INDEX: " + CodeHandler.publicIndex);
                        currentState = states.LETTER;
                        wordBuffer += currentChar;
                        break;
                    }
                    //CHECKS DIGIT
                    else if (Character.isDigit(currentChar)) {
//                        System.out.println(currentChar);
                        currentState = states.DIGIT;
                        numberBuffer += currentChar;
                        break;
                    }
                    //CHECKS FOR SPACE
                    else if (Character.isSpaceChar(currentChar) | currentChar == '\t') {
//                        System.out.println("SPACE OR TAB");
                        currentState = states.SPACE;
                        break;
                    }
                    //CHECKS FOR NEWLINE
                    else if (currentChar == '\n') {
                        //Same as processword with no value
                        position++;
                        TokenList.add(new Token(Token.TokenType.ENDOFLINE, lineNumber, position));
                        lineNumber++;
                        position = 0;
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    }
                    //IF ITS QUOTES, ADD TO STRING LITERAL
                    else if (currentChar == 8220) {
//                        System.out.println("STRING");
                        currentState = states.STRINGLITERAL;
                        break;
                    }
                    //IF NOTHING ELSE, PRINTS THE CHARACTER
                    else if (currentChar == '\r') {
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    }
                    else if(singleSymbols.containsKey(String.valueOf(currentChar)) | (currentChar == '&')) {
                        if(doubleSymbols.containsKey(code.peekString(2))) {
                            position++;
                            TokenList.add(new Token(doubleSymbols.get(code.peekString(2)), lineNumber, position, code.peekString(2)));
                            if (code.Remainder().length() > 2) {
                                currentChar = code.GetChar(2);
                            }
                            else {
                                currentChar = code.GetChar(1);
                            }
                            break;
                        }
                        else {
                            if (currentChar == '&') {
                                currentChar = code.GetChar(1);
                                break;
                            }
                            else {
                                position++;
                                TokenList.add(new Token(singleSymbols.get(String.valueOf(currentChar)),lineNumber,position,String.valueOf(currentChar)));
                                currentChar = code.GetChar(1);
                                break;
                            }
                        }
                    }
                    else {
                        currentChar = code.GetChar(1);
                        break;
                    }
                case STRINGLITERAL:
                    if ((code.peek(1) == 8221) & (currentChar != 92)) {
                        stringBuffer += currentChar;
                        position++;
                        TokenList.add(HandleStringLiteral(stringBuffer));
                        stringBuffer = "";
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        break;
                    } else if (currentChar == 92) {
                        currentChar = code.GetChar(1);
                        break;
                    } else if (currentChar != 8220) {
                        stringBuffer += currentChar;
                        currentChar = code.GetChar(1);
                        break;
                    } else {
                        currentChar = code.GetChar(1);
                        break;
                    }
                case LETTER:
                    //CHECKS FOR LETTER AGAIN
                    if (Character.isAlphabetic(code.peek(1)) | Character.isDigit(code.peek(1)) | (code.peek(1) == '$') | (code.peek(1) == '_') | code.peek(1) == '$') {
                        currentChar = code.GetChar(1);
//                        System.out.println(currentChar + " LETTER");
                        wordBuffer += currentChar;
                        break;
                    }
                    else if (code.peek(1)== ':') {
                        position++;
                        currentChar = code.GetChar(1);
                        wordBuffer += currentChar;
                        TokenList.add(new Token(Token.TokenType.LABEL,lineNumber,position,wordBuffer));
                        currentState = states.INITIAL;
                        wordBuffer = "";
                        break;
                    }
                    //IF NOT LETTER, GOES TO INITIAL STATE
                    else {
                        position++;
                        TokenList.add(ProcessWord(wordBuffer));
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        wordBuffer = "";
                        break;
                    }
                case DIGIT:
                    //CHECKS FOR DIGIT
                    if (Character.isDigit(code.peek(1))) {
                        currentChar = code.GetChar(1);
//                        System.out.println(currentChar);
                        numberBuffer += currentChar;
                        break;
                    }
                    //IF IS A DECIMAL, INCREASES DECIMAL COUNT BY 1
                    else if ((code.peek(1) == '.') & (decimalCount < 1)) {
                        decimalCount++;
                        currentChar = code.GetChar(1);
//                        System.out.println(currentChar);
                        numberBuffer += currentChar;
                        break;
                    }
                    //IF WE ALREADY HAVE A DECIMAL IT ADDS THE NUMBER AND CARRIES ON
                    else {
                        position++;
                        TokenList.add(ProcessNumber(numberBuffer));
                        decimalCount = 0;
                        currentChar = code.GetChar(1);
                        currentState = states.INITIAL;
                        currentState = states.INITIAL;
                        numberBuffer = "";
                        break;
                    }
                    //CHECKS FOR SPACE CHARACTER
                case SPACE:
                    currentChar = code.GetChar(1);
                    currentState = states.INITIAL;
                    break;
            }
        }

        //Prints out last word if the buffer isn't empty
        if (wordBuffer != "") {
            position++;
            TokenList.add(new Token(Token.TokenType.IDENTIFIER, lineNumber, position, wordBuffer));
        }
        else if (numberBuffer != "") {
            position++;
            TokenList.add(new Token(Token.TokenType.NUMBER, lineNumber, position, numberBuffer));
        }
        else if (stringBuffer != "") {
            position++;
            TokenList.add(new Token(Token.TokenType.STRINGLITERAL, lineNumber, position, stringBuffer));
        }
        else if (!(doubleSymbols.containsKey(file.charAt(file.length()-2) + "" + (code.Remainder())))){
            position++;
            TokenList.add(new Token(singleSymbols.get(code.Remainder()),lineNumber,position,code.Remainder()));
        }

        System.out.println("=========================" + "\n FILE IS DONE \n" +  "=========================");
        System.out.println(TokenList.toString());
        return TokenList;
    }

}
