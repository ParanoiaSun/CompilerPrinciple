/**
 * Created by paranoia on 2017/11/20.
 */

package parser;

import analyzer.Token;
import util.CodeId;

import java.util.List;

public class Queue {

    private List<Token> tokens;

    public Queue(List<Token> tokens) {
        this.tokens = tokens;
        this.tokens.add(new Token(CodeId.END, "$"));
    }

    public Token getFirst() {
        return this.tokens.get(0);
    }

    public void dequeue() {
        this.tokens.remove(0);
    }

    public void enqueue(Token token) {
        this.tokens.add(token);
    }
}
