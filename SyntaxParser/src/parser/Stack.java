/**
 * Created by paranoia on 2017/11/21.
 */

package parser;

import analyzer.Token;
import util.CodeId;

import java.util.ArrayList;
import java.util.List;

public class Stack {

    private List<Token> stack;

    public Stack() {
        this.stack = new ArrayList<>();
        this.stack.add(new Token(CodeId.END, "$"));
    }

    public void push(Token token) {
        this.stack.add(token);
    }

    public void pop() {
        this.stack.remove(this.stack.size() - 1);
    }

    public Token get() {
        return this.stack.get(this.stack.size() - 1);
    }

    private void print() {
        for(int i = this.stack.size() - 1; i >= 0; i--) {
            System.out.println(this.stack.get(i));
        }
    }
}
