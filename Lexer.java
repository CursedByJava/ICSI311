import java.util.LinkedList;

public class Lexer {

    public static LinkedList lex(String filename) {

        LinkedList<Token> TokenList = new LinkedList<Token>();
        System.out.println("FILE NAME: " + filename);

        //Holds the line number and character position (duh)
        int lineNumber;
        int characterPosition;

        CodeHandler code = new CodeHandler();
        char currentChar = code.peek(0);


        String currentState = "initial";

        while (!code.IsDone()) {



            switch (currentState) {

                case "initial":
                    if (Character.isLetter(currentChar)) {
                        currentState = "letter";
                        System.out.println(currentChar + " " + currentState);
                    }
                    else if (Character.isDigit(currentChar)) {
                        currentState = "number";
                        System.out.println(currentChar + " " + currentState);
                    }
                    else if (currentChar == '\n') {
                        currentState = "EOL";
                    }
                    else if ((currentChar == ' ') | currentChar == '\t') {
                        currentState = "space";
                        System.out.println("SPACE " + currentChar + currentState);
                    }
                    else {
                        System.out.println("BREAK");
                    }
                    break;

                case "space":

                case "letter":
                    TokenList.add(ProcessWord(currentChar));
                    currentChar = code.GetChar(1);
                    currentState = "initial";

                case "number":
                    System.out.println("HERE");

                case "EOL":


            }
        }
        return null;
    }

    static Token ProcessWord(char convertedChar) {
        String charString = "";

        boolean isLetter = Character.isLetter(convertedChar);
        boolean isNumber = Character.isDigit(convertedChar);
        boolean isUnderscore = (convertedChar == '_');
        boolean isMoneySign =  (convertedChar == '$');
        boolean isPercentage = (convertedChar == '%');

        if (isLetter | isNumber |isUnderscore | isMoneySign | isPercentage) {
            charString += convertedChar;
        }
        else {
           return new Token(Token.TokenType.WORD, Token.lineNumber, Token.characterPosition, charString);
        }

        return null;
    }
}
