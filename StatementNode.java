import java.util.LinkedList;

class StatementsNode extends Node {
    private final LinkedList<StatementsNode> statementsList;

    StatementsNode(LinkedList<StatementsNode> statementsList) {
        this.statementsList = statementsList;
    }

    public LinkedList<StatementsNode> getStatementsList() {
        return statementsList;
    }

    @Override
    public String toString() {
        return "StatementsNode(" + statementsList.toString() + ")";
    }
}
