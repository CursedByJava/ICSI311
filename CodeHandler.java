import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class CodeHandler {

    //filepath which grabs the filename in the arguments
    Path filePath = Paths.get(Basic.arguments);
    //codeHandler holds the BASIC file


    private String codeHandler;
    //Tries to read from file and put the data in the codehandler.
    {
        try {
            codeHandler = new String(Files.readAllBytes(filePath));
        } catch (IOException fileException) {
            System.out.println(fileException);
            throw new RuntimeException(fileException);
        }
    }

    String file() {
        return codeHandler;
    }

    static int fileIndex(){
        return index;
    }

    //index holds the position
    private static int index;

    int Index = index;

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
        index += i;
        return codeHandler.charAt(index);
    }
    void Swallow(int i){//moves the index ahead “i” positions
        index += i;
    }
    boolean IsDone(){//returns true if we are at the end of the document
        if (index == codeHandler.length() - 1){
            return true;
        }
        else {
//            System.out.println("FILE IS !DONE" + index);
            return false;
        }
    }
    String Remainder() {//returns the rest of the document as a string
        String restOfFile = "";
        for (int i=0;i<codeHandler.length()-index;i++){
            restOfFile += codeHandler.charAt(index + i);
        }
        return restOfFile;
    }

}
