/**
 * Created by paranoia on 2017/11/25.
 */

package parser;

import analyzer.Token;
import util.CodeId;

import java.util.*;

public class PPT {

    public static List<String> generations = new ArrayList<String>(){{
        add("S->id=A;");
        add("S->if(D){S}else{S}");
        add("S->while(C){S}");
        add("A->TA'");
        add("A'->+TA'");
        add("A'->ε");
        add("B->CB'");
        add("B'->*CB'");
        add("B'->ε");
        add("C->(A)");
        add("C->id");
        add("C->number");
        add("D->ED'");
        add("D'->||DC'");
        add("D'->ε");
        add("E->(D)");
        add("E->id==number");
    }};

    public static List<Token> Vn = new ArrayList<Token>(){{
        add(new Token(CodeId.S, "S"));
        add(new Token(CodeId.A, "A"));
        add(new Token(CodeId.A0, "A'"));
        add(new Token(CodeId.B, "B"));
        add(new Token(CodeId.B0, "B'"));
        add(new Token(CodeId.C, "C"));
        add(new Token(CodeId.D, "D"));
        add(new Token(CodeId.D0, "D'"));
        add(new Token(CodeId.E, "E"));
    }};
    public static List<Token> Vt = new ArrayList<Token>() {{
        add(new Token(CodeId.IDENTIFIER, "id"));
        add(new Token(CodeId.RESERVED_WORD, "if"));
        add(new Token(CodeId.RESERVED_WORD, "while"));
        add(new Token(CodeId.RESERVED_WORD, "else"));
        add(new Token(CodeId.OPERATOR, "+"));
        add(new Token(CodeId.OPERATOR, "*"));
        add(new Token(CodeId.OPERATOR, "||"));
        add(new Token(CodeId.OPERATOR, "="));
        add(new Token(CodeId.OPERATOR, "=="));
        add(new Token(CodeId.PUNCTUATION, ";"));
        add(new Token(CodeId.PUNCTUATION, "("));
        add(new Token(CodeId.PUNCTUATION, ")"));
        add(new Token(CodeId.PUNCTUATION, "{"));
        add(new Token(CodeId.PUNCTUATION, "}"));
        add(new Token(CodeId.NUMBER, "number"));
        add(new Token(CodeId.END, "$"));
    }};
    public static Map<Token, Map<Token, Integer>> table = new HashMap<Token, Map<Token, Integer>>(){{
        Map<Token, Integer> temp;
        //S, 对应：id(0), if(1), while(2)
        temp = new HashMap<>();
        temp.put(find(Vt, "id"), 0);
        temp.put(find(Vt, "if"), 1);
        temp.put(find(Vt, "while"), 2);
        put(find(Vn, "S"), temp);

        //A, 对应：id(3), ( (3), number(3)
        temp = new HashMap<>();
        temp.put(find(Vt, "id"), 3);
        temp.put(find(Vt, "("), 3);
        temp.put(find(Vt, "number"), 3);
        put(find(Vn, "A"), temp);

        //A', 对应：+(4), ;(5), )(5)
        temp = new HashMap<>();
        temp.put(find(Vt, "+"), 4);
        temp.put(find(Vt, ";"), 5);
        temp.put(find(Vt, ")"), 5);
        put(find(Vn, "A'"), temp);

        //B, 对应：id(6), ( (6), number(6)
        temp = new HashMap<>();
        temp.put(find(Vt, "id"), 6);
        temp.put(find(Vt, "("), 6);
        temp.put(find(Vt, "number"), 6);
        put(find(Vn, "B"), temp);

        //B', 对应：+(8), *(7), ;(8), ) (8)
        temp = new HashMap<>();
        temp.put(find(Vt, "+"), 8);
        temp.put(find(Vt, "*"), 7);
        temp.put(find(Vt, ";"), 8);
        temp.put(find(Vt, ")"), 8);
        put(find(Vn, "B'"), temp);

        //C, 对应：id(10), ( (9), number(11)
        temp = new HashMap<>();
        temp.put(find(Vt, "id"), 10);
        temp.put(find(Vt, "("), 9);
        temp.put(find(Vt, "number"), 11);
        put(find(Vn, "C"), temp);

        //D, 对应：id(12), ( (12)
        temp = new HashMap<>();
        temp.put(find(Vt, "id"), 12);
        temp.put(find(Vt, "("), 12);
        put(find(Vn, "D"), temp);

        //D', 对应：||(13), ) (14)
        temp = new HashMap<>();
        temp.put(find(Vt, "||"), 13);
        temp.put(find(Vt, ")"), 14);
        put(find(Vn, "D'"), temp);

        //E, 对应：id(16), ( (15)
        temp = new HashMap<>();
        temp.put(find(Vt, "id"), 16);
        temp.put(find(Vt, "("), 15);
        put(find(Vn, "E"), temp);
    }};

    public static Token find(List<Token> tokens, String s) {
        Iterator<Token> it = tokens.iterator();
        while(it.hasNext()) {
            Token t = it.next();
            if(t.getName().equals(s))
                return t;
        }
        return null;
    }
}
