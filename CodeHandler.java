public class CodeHandler {
    //codeHandler holds the BASIC file
    private String codeHandler;
    //index holds the position
    private int index;

    char peek(int i) {//looks “i” characters ahead and returns that character; doesn’t move the index
       return codeHandler.charAt(i + index);
    }
    String peekString(int i){//returns a string of the next “i” characters but doesn’t move the index
        String peekStringBuffer = "";
        for (int j=0;j<i;j++)  {
            //peekstring buffer takes the number input, makes j count up to it
            //and adds the character at j + the current index to the string
            //then returns the new string
            peekStringBuffer += codeHandler.charAt(index + j);
        }
        return peekStringBuffer;
    }
    char GetChar(int i){//returns the next character and moves the index
        index += 1;
        return codeHandler.charAt(index);
    }
    void Swallow(int i){//moves the index ahead “i” positions
        index += i;
    }
    boolean IsDone(){//returns true if we are at the end of the document
        if (index == codeHandler.length()){
            return true;
        }
        else {
            return false;
        }
    }
    String Remainder() {//returns the rest of the document as a string
        String restOfFile = "";
        for (int i=0;i<codeHandler.length();i++){
            restOfFile += codeHandler.charAt(index + i);
        }
        return restOfFile;
    }

}
