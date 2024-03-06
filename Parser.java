import java.util.LinkedList;

class Parser {

    private TokenManager tokenManager;

    // Constructor accepting a collection of Tokens
    Parser(LinkedList<Token> tokenList) {
        this.tokenManager = new TokenManager(tokenList);
    }

    // Helper method to accept separators (EndOfLine)
    private boolean AcceptSeparators() {
        boolean foundSeparator = false;

        // Keeps accepting separators as long as there are consecutive EndOfLine tokens
        while (tokenManager.peek(0).isPresent() && tokenManager.peek(0).get().tokenType == Token.TokenType.ENDOFLINE) {
            tokenManager.matchAndRemove(Token.TokenType.ENDOFLINE);
            foundSeparator = true;
        }

        return foundSeparator;
    }

    // Parse method that returns a ProgramNode
    ProgramNode Parse() {
        ProgramNode programNode = new ProgramNode();

        // Call Statements() instead of Expression()
        StatementsNode statements = Statements();

        if (statements != null) {
            programNode.addStatements(statements);
        }

        return programNode;
    }

    // Statements() method that accepts any number of statements
    private StatementsNode Statements() {
        LinkedList<StatementsNode> statementsList = new LinkedList<>();

        while (true) {
            StatementsNode statement = Statement();

            if (statement != null) {
                statementsList.add(statement);
            } else {
                break;
            }
        }

        return new StatementsNode(statementsList);
    }

    private StatementsNode Statement() {
        return null;
    }
    private Node Factor() {

        if (tokenManager.peek(0).isPresent() && tokenManager.peek(0).get().tokenType == Token.TokenType.WORD) {
            String variableName = tokenManager.peek(0).get().tokenValue;
            tokenManager.matchAndRemove(Token.TokenType.WORD);
            return new VariableNode(variableName);
        }
        return null;
    }

}
