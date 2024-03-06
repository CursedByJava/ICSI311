import java.util.LinkedList;

public class ProgramNode {
    private LinkedList<LinkedList<Token>> expressions = new LinkedList<>();

    void addExpression(LinkedList<Token> expression) {
        expressions.add(expression);
    }

    @Override
    public String toString() {

        return "ProgramNode{" +
                "expressions=" + expressions +
                '}';
    }

    public void addStatements(StatementsNode statements) {
    }
}
