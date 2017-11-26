/**
 * Created by paranoia on 2017/11/20.
 */

package parser;

import analyzer.Token;
import util.CodeId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Parser {
    private Queue queue;
    private Stack stack;
    private List<String> result;

    public String parse(List<Token> tokens) {
        this.queue = new Queue(tokens);
        this.stack = new Stack();
        this.result = new ArrayList<>();
        this.stack.push(new Token(CodeId.S, "S"));

        Token t1;
        Token t2;

        while(this.queue.getFirst().getType() != CodeId.END) {
            t1 = stack.get();
            t2 = queue.getFirst();

            if(t1.getType()>=50 && t1.getType()<=58) {
                if(!generate(t1, t2)) {
                    System.out.println("Error1!");
                    return "Error1!";
                }
            } else {
                if(t1.getType() == t2.getType()) {
                    stack.pop();
                    queue.dequeue();
                }else {
                    System.out.println("Error2!");
                    return "Error2!";
                }
            }
        }


        String output = "";
        Iterator<String> it = result.iterator();
        while(it.hasNext())
            output += it.next() + "\n";
        return output;
    }

    private boolean generate(Token t1, Token t2) {
        int n = refer(t1, t2);
        if(n < 0) {
            System.out.println("Error3!");
            return false;
        }
        result.add(PPT.generations.get(n));
        stack.pop();
        switch(n) {
            case 0:
                stack.push(new Token(CodeId.PUNCTUATION, ";"));
                stack.push(new Token(CodeId.A, "A"));
                stack.push(new Token(CodeId.OPERATOR, "="));
                stack.push(new Token(CodeId.IDENTIFIER, "id"));
                break;
            case 1:
                stack.push(new Token(CodeId.PUNCTUATION, "}"));
                stack.push(new Token(CodeId.S, "S"));
                stack.push(new Token(CodeId.PUNCTUATION, "{"));
                stack.push(new Token(CodeId.RESERVED_WORD, "else"));
                stack.push(new Token(CodeId.PUNCTUATION, "}"));
                stack.push(new Token(CodeId.S, "S"));
                stack.push(new Token(CodeId.PUNCTUATION, "{"));
                stack.push(new Token(CodeId.PUNCTUATION, ")"));
                stack.push(new Token(CodeId.D, "D"));
                stack.push(new Token(CodeId.PUNCTUATION, "("));
                stack.push(new Token(CodeId.RESERVED_WORD, "if"));
                break;
            case 2:
                stack.push(new Token(CodeId.PUNCTUATION, "}"));
                stack.push(new Token(CodeId.S, "S"));
                stack.push(new Token(CodeId.PUNCTUATION, "{"));
                stack.push(new Token(CodeId.PUNCTUATION, ")"));
                stack.push(new Token(CodeId.D, "D"));
                stack.push(new Token(CodeId.PUNCTUATION, "("));
                stack.push(new Token(CodeId.RESERVED_WORD, "while"));
                break;
            case 3:
                stack.push(new Token(CodeId.A0, "A'"));
                stack.push(new Token(CodeId.B, "B"));
                break;
            case 4:
                stack.push(new Token(CodeId.A0, "A'"));
                stack.push(new Token(CodeId.B, "B"));
                stack.push(new Token(CodeId.OPERATOR, "+"));
                break;
            case 5:
                break;
            case 6:
                stack.push(new Token(CodeId.B0, "B'"));
                stack.push(new Token(CodeId.C, "C"));
                break;
            case 7:
                stack.push(new Token(CodeId.B0,"B'"));
                stack.push(new Token(CodeId.C,"C"));
                stack.push(new Token(CodeId.OPERATOR,"*"));
                break;
            case 8:
                break;
            case 9:
                stack.push(new Token(CodeId.PUNCTUATION,")"));
                stack.push(new Token(CodeId.A,"A"));
                stack.push(new Token(CodeId.PUNCTUATION,"("));
                break;
            case 10:
                stack.push(new Token(CodeId.IDENTIFIER,"id"));
                break;
            case 11:
                stack.push(new Token(CodeId.NUMBER,"number"));
                break;
            case 12:
                stack.push(new Token(CodeId.D0,"D'"));
                stack.push(new Token(CodeId.E,"E"));
                break;
            case 13:
                stack.push(new Token(CodeId.D0,"D'"));
                stack.push(new Token(CodeId.E,"E"));
                stack.push(new Token(CodeId.OPERATOR,"||"));
                break;
            case 14:
                break;
            case 15:
                stack.push(new Token(CodeId.PUNCTUATION,")"));
                stack.push(new Token(CodeId.D,"D"));
                stack.push(new Token(CodeId.PUNCTUATION,"("));
                break;
            case 16:
                stack.push(new Token(CodeId.NUMBER,"number"));
                stack.push(new Token(CodeId.OPERATOR,"=="));
                stack.push(new Token(CodeId.IDENTIFIER,"id"));
                break;
            default:
                System.out.println("Error4!");
                return false;
        }
        return true;
    }

    private int refer(Token vn, Token vt) {
        if(vt.getType() == CodeId.IDENTIFIER) {
            vt = new Token(CodeId.IDENTIFIER, "id");
        } else if(vt.getType() == CodeId.NUMBER) {
            vt = new Token(CodeId.NUMBER, "number");
        }
        Map<Token, Integer> m = subRefer(vn);
        if(m != null) {
            Iterator<Map.Entry<Token, Integer>> it = m.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Token, Integer> entry = it.next();
                if(entry.getKey().getName().equals(vt.getName())) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }

    private  Map<Token, Integer> subRefer(Token t) {
        Iterator<Map.Entry<Token, Map<Token, Integer>>> it = PPT.table.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Token, Map<Token, Integer>> entry = it.next();
            if(entry.getKey().getName().equals(t.getName()))
                return entry.getValue();
        }
        return null;
    }

    private boolean isVt(Token t) {
        Iterator<Token> it = PPT.Vt.iterator();
        while(it.hasNext()) {
            Token temp = it.next();
            if (temp.getName().equals(t.getName()) && temp.getType() == t.getType())
                return true;
        }
        return false;
    }
}
