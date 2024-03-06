class MathOpNode extends Node {

    enum MathOperation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, NEGATE
    }

    MathOperation operation;
    Node leftOperand;
    Node rightOperand;

    MathOpNode(MathOperation operation, Node leftOperand, Node rightOperand) {
        this.operation = operation;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    MathOperation getOperation() {
        return operation;
    }

    Node getLeftOperand() {
        return leftOperand;
    }

    Node getRightOperand() {
        return rightOperand;
    }

    @Override
    public String toString() {
        return "(" + leftOperand.toString() + " " + operation + " " + rightOperand.toString() + ")";
    }
}
