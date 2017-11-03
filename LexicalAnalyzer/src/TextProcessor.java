import utils.FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by paranoia on 2017/10/28.
 * 这个类控制着整个分析过程，主要用作
 */

public class TextProcessor {
    private static FileIO io;
    private static String[] reservedWord = {"private", "public", "protected", "void", "static",
            "int", "char", "float", "double", "string", "if", "else", "do",
            "while", "try", "catch", "switch", "case" ,"for", "class"};

    private char[] textContent;

    public static final int ERROR = 0;
    public static final int IDENTIFIER = 1;
    public static final int NUMBER = 2;
    public static final int OTHER = 3;

    // 作用：分析流程
    public void analyze(File file) {
        textContent = io.readIn(file).toCharArray();
        System.out.println(textContent);
//        File output = new File("output.txt");
//        io.writeTo(output, "Hello, World!");
        scan();
    }

    public void scan() {
        int textPtr = 0;
        int strPtr = 0;
        char[] str = new char[20]; char c;
        List<Token> list = new ArrayList<>();
//        while((c = textContent[textPtr]) != '\0'){
//            System.out.print(c);
//            if(checkIsLetter(c)) {
//                while((checkIsLetter(textContent[textPtr]))||(checkIsDigit(textContent[textPtr]))) {
//                    str[strPtr] = textContent[textPtr];
//                    textPtr++; strPtr++;
//                }
//                if(textContent[textPtr] == ' ') {
//                    list.add(new Token(IDENTIFIER, str.toString()));
//                }
//            } else {
//                switch(c) {
//                    case ' ':
//
//                }
//            }
//        }

        Iterator it = list.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    // 作用：检查是否为关键字
    public boolean checkReservedWord(String s) {
        for(String w : reservedWord)
            if(s.equals(w))
                return true;
        return false;
    }

    // 作用：检查是否为字母
    public boolean checkIsLetter(char c) {
        if((c >= 'a' && c <= 'z')||(c >= 'A' && c <= 'Z'))
            return true;
        return false;
    }

    public boolean checkIsDigit(char c) {
        if(c >= '0' && c <= '9')
            return true;
        return false;
    }

//    public String

}
