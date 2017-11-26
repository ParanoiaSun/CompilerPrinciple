
import analyzer.TextAnalyzer;
import analyzer.Token;
import parser.PPT;
import parser.Parser;
import util.CodeId;
import util.FileIO;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by paranoia on 2017/11/13.
 */

public class Main {

    public static void main(String[] args) {
        // 读取输入文件并进行词法分析，获得token序列
        File input = new File("input.txt");
        TextAnalyzer analyzer = new TextAnalyzer();
        List<Token> tokens = analyzer.analyze(input);

        // 将获得的token序列进行语法分析，获得产生式序列
        Parser parser = new Parser();
        String output = parser.parse(tokens);
//        testToken(tokens);
//        testPPT();

        // 将产生式文件输出到文件
        File result = new File("output.txt");
        FileIO.writeTo(result, output);
    }

    public static void testToken(List<Token> tokens) {
        Iterator<Token> it = tokens.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }
    }

    public static void testPPT() {
        Iterator<Map.Entry<Token, Map<Token, Integer>>> it1 = PPT.table.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<Token, Map<Token, Integer>> entry = it1.next();
            System.out.println(entry.getKey().getName());
            Iterator<Map.Entry<Token, Integer>> it2 = entry.getValue().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<Token, Integer> entry2 = it2.next();
                System.out.println("Key = " + entry2.getKey().getName() + ", Value = " + entry2.getValue());
            }

        }
    }
}
