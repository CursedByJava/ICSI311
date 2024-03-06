import java.util.LinkedList;

class PrintNode extends Node {

    //Holds a list of nodes to print
    private LinkedList<Node> printNodes;

    //Constructor to initialize the PrintNode with a list of nodes
    PrintNode(LinkedList<Node> nodes) {
        printNodes = nodes;
    }

    //Accessor method to get the list of nodes to print
    public LinkedList<Node> getPrintNodes() {
        return printNodes;
    }

    @Override
    public String toString() {
        //TODO: Implement toString for PrintNode
        return null;
    }
}
