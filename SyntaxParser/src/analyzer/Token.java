package analyzer;

import java.util.HashMap;

/**
 * Created by paranoia on 2017/11/13.
 * 用于词法分析的Token
 */

public class Token {
    private String type;
    private String name;
    private String error;
    private int typeID;

    HashMap<Integer, String> m = new HashMap<Integer, String>(){{
        put(1, "IDENTIFIER");
        put(2, "POSITIVE_NUMBER");
        put(3, "PUNCTUATION");
        put(4, "OPERATOR");
        put(5, "COMMENT");
        put(6, "STRING");
        put(7, "CHARACTER");
        put(8, "RESERVED_WORD");
        put(9, "NEGATIVE_NUMBER");

        put(50, "S");
        put(51, "A");
        put(52, "A0");
        put(53, "B");
        put(54, "B0");
        put(55, "C");
        put(56, "D");
        put(57, "D0");
        put(58, "E");

        put(99, "END");
        put(100, "OTHER");
    }};

    public Token(int type, String code){
        this.typeID = type;
        this.type = m.get(type);
        this.name = code;
        this.error = null;
    }

    public Token(int type,String code,String error){
        this.typeID = type;
        this.type = null;
        this.name = code;
        this.error = error;
    }

    @Override
    public String toString() {
        if(this.error == null)
            return "<TYPE=" + this.type + ", " + this.name + " >";
        else
            return "<ERROR=" + this.error + ", " + this.name + " >";
    }

    public int getType() {
        return this.typeID;
    }

    public String getName() {
        return this.name;
    }
}
