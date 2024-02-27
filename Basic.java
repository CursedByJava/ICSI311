public class Basic {

    public static String arguments;
    public static void main(String[] args) throws Exception {

        //Main must ensure there's only one argument.
        //If more than 1, print error message and exit.
        if (args.length != 1) {
            //Error message
            throw new Exception("Error: more than one argument: " + args);
        }
        else {
            //sets arguments to the filename
            arguments = args[0];
            //RUN CODE
            Lexer lexer = new Lexer();
            lexer.lex(arguments);
        }
     }
}
