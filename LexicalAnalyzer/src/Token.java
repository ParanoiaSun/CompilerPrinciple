import java.util.HashMap;

/**
 * Created by paranoia on 2017/10/24.
 * 用于最后输出的Token
 */

public class Token {
    private String type;
    private String name;
    private String error;

    HashMap<Integer, String> m = new HashMap<Integer, String>(){{
        put(1, "IDENTIFIER"); put(2, "POSITIVE_NUMBER"); put(3, "PUNCTUATION"); put(4, "OPERATOR"); put(100, "OTHER");
        put(5, "COMMENT"); put(6, "STRING"); put(7, "CHARACTER"); put(8, "RESERVED_WORD"); put(9, "NEGATIVE_NUMBER");
    }};

    public Token(int type, String code){
        this.type= m.get(type);
        this.name=code;
        this.error=null;
    }

    public Token(int type,String code,String error){
        this.type=null;
        this.name=code;
        this.error=error;
    }

    @Override
    public String toString() {
        if(this.error == null)
            return "<TYPE=" + this.type + ", " + this.name + " >";
        else
            return "<ERROR=" + this.error + ", " + this.name + " >";
    }
}
