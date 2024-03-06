import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParserTest {

    @Test
    void parsePrintStatement() {
        // Test parsing a simple print statement: PRINT expression
        LinkedList<Token> tokens = new LinkedList<>();

        Parser parser = new Parser(tokens);
        ProgramNode programNode = parser.Parse();

    }

    @Test
    void parseAssignmentStatement() {

        LinkedList<Token> tokens = new LinkedList<>();

        Parser parser = new Parser(tokens);
        ProgramNode programNode = parser.Parse();
    }

    @Test
    void parsePrintAndAssignmentStatements() {

        LinkedList<Token> tokens = new LinkedList<>();

        Parser parser = new Parser(tokens);
        ProgramNode programNode = parser.Parse();
    }
}
