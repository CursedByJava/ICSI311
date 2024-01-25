public class Basic {
    public static void main(String[] args) throws Exception {
        //Main must ensure there's only one argument.
        //If more than 1, print error message and exit.
        if (args.length != 1) {
            //Error message
            throw new Exception("Error: more than one argument!");
        }
        else {
            //RUN CODE
            System.out.println("Hello World");
        }
     }
}
