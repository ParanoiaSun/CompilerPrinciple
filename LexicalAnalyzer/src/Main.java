import java.io.File;

/**
 * Created by paranoia on 2017/10/18.
 */


public class Main {
    public static void main(String[] args) {
        TextProcessor tp = new TextProcessor();
        File text = new File("SourceCode.txt");
        tp.analyze(text);
    }
}
