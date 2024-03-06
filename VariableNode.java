class VariableNode extends Node {

    // Holds the name of the variable
    private String variableName;

    // Constructor to initialize the VariableNode with a variable name
    VariableNode(String name) {
        variableName = name;
    }

    // Accessor method to get the variable name
    public String getVariableName() {
        return variableName;
    }

    @Override
    public String toString() {
        // TODO: Implement toString for VariableNode
        return null;
    }
}
