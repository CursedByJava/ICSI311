class AssignmentNode extends Node {

    //Holds a variable node and an expression node for assignment
    private VariableNode variableNode;
    private Node expressionNode;

    //Constructor to initialize the AssignmentNode with a variable and an expression
    AssignmentNode(VariableNode variable, Node expression) {
        variableNode = variable;
        expressionNode = expression;
    }

    //Accessor methods to get the variable and expression nodes
    public VariableNode getVariableNode() {
        return variableNode;
    }

    public Node getExpressionNode() {
        return expressionNode;
    }

    @Override
    public String toString() {
        // TODO: Implement toString for AssignmentNode
        return null;
    }
}
