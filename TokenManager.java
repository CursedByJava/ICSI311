import java.util.LinkedList;
import java.util.Optional;

class TokenManager {

    private LinkedList<Token> tokenList;

    TokenManager(LinkedList<Token> inputTokenList) {
        this.tokenList = inputTokenList;
    }

    Optional<Token> peek(int i) {
        if (i < 0 || i >= tokenList.size()) {
            return Optional.empty();
        }
        return Optional.of(tokenList.get(i));
    }

    boolean moreTokens() {
        return !tokenList.isEmpty();
    }

    Optional<Token> matchAndRemove(Token.TokenType t) {
        if (!tokenList.isEmpty() && tokenList.getFirst().tokenType == t) {
            return Optional.of(tokenList.removeFirst());
        } else {
            return Optional.empty();
        }
    }
}
