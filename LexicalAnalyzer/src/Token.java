import java.util.HashMap;
import java.util.List;

/**
 * Created by paranoia on 2017/11/3.
 */

public class Token {
    private String type;
    private String name;
    private String error;

    HashMap<Integer, String> m = new HashMap<Integer, String>(){{
        put(1, "IDENTIFIER"); put(2, "NUMBER"); put(3, "OTHER");
    }};

    public Token(){
    }

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
            return "<TYPE=" + this.type + ", " + this.name + ">";
        else
            return "<ERROR=" + this.error + ", " + this.name + ">";
    }
}
