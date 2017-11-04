import utils.FileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by paranoia on 2017/10/24.
 * 这个类控制着整个分析过程
 */

public class TextProcessor {
    // java的关键字
    private static String[] reservedWord = {"abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum",
            "extends", "final", "finally", "float", "for", "if", "implements", "import", "instanceof",
            "int", "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
            "throws", "transient", "try", "void", "volatile", "while", "true", "false", "String"};

    private char[] textContent;
    private String result;

    public static final int ERROR = 0;  //错误
    public static final int IDENTIFIER = 1;  //标识符
    public static final int NUMBER = 2;  //数字
    public static final int PUNCTUATION = 3;  //标点
    public static final int OPERATOR = 4;  //运算符
    public static final int COMMENT = 5;  //注释
    public static final int STRING = 6;  //字符串
    public static final int CHARACTER = 7;  //字符
    public static final int RESERVED_WORD = 8;  //关键字
    public static final int NEGATIVE_NUMBER = 9;  //数字
    public static final int OTHER = 100;  //其他

    // 作用：分析流程
    public void analyze(File file) {
        textContent = (FileIO.readIn(file) + '\0').toCharArray();
        scan();
        File output = new File("output.txt");
        FileIO.writeTo(output, result);
    }

    public void scan() {
        int textPtr = 0;
        int strPtr = 0;
        char[] str = new char[1000]; char c;
        List<Token> list = new ArrayList<>();
        while(textPtr < textContent.length && ((c = textContent[textPtr]) != '\0')){
            if(checkIsLetter(c)) {
                while((checkIsLetter(textContent[textPtr]))||(checkIsDigit(textContent[textPtr]))) {
                    str[strPtr] = textContent[textPtr];
                    textPtr++;
                    strPtr++;
                }
                if(checkReservedWord(getString(str, strPtr)))
                    list.add(new Token(RESERVED_WORD, getString(str, strPtr)));
                else
                    list.add(new Token(IDENTIFIER, getString(str, strPtr)));
                textPtr--;
            } else if (checkIsDigit(c)) {
                while(checkIsDigit(textContent[textPtr])) {
                    str[strPtr] = textContent[textPtr];
                    textPtr++;
                    strPtr++;
                }
                if(textContent[textPtr] == '.') {
                    str[strPtr] = textContent[textPtr];
                    textPtr++; strPtr++;
                    if(!checkIsDigit(textContent[textPtr]))
                        list.add(new Token(ERROR, getString(str, strPtr), "Unknown Input"));
                    else {
                        while(checkIsDigit(textContent[textPtr])) {
                            str[strPtr] = textContent[textPtr];
                            textPtr++; strPtr++;
                        }
                        list.add(new Token(NUMBER, getString(str, strPtr)));
                    }
                    textPtr--;
                } else {
                    list.add(new Token(NUMBER, getString(str, strPtr)));
                    textPtr--;
                }
            } else {
                str[strPtr] = c; strPtr++;
                switch (c) {
                    case '{': case '}': case ':': case '[': case ']': case '(': case ')': case ',': case ';': case'.':
                        list.add(new Token(PUNCTUATION, getString(str, strPtr)));
                        break;
                    case '+': //加号，包括运算符 +, +=, ++
                        if(textContent[textPtr + 1] == '=' || textContent[textPtr + 1] == '+') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '-': //减号，包括运算符 -, -=, --
                        if(textContent[textPtr + 1] == '=' || textContent[textPtr + 1] == '-') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            list.add(new Token(OPERATOR, getString(str, strPtr)));
                        } else if (checkIsDigit(textContent[textPtr + 1])) {
                            textPtr++;
                            while(checkIsDigit(textContent[textPtr])) {
                                str[strPtr] = textContent[textPtr];
                                textPtr++;
                                strPtr++;
                            }
                            if(textContent[textPtr] == '.') {
                                str[strPtr] = textContent[textPtr];
                                textPtr++;
                                strPtr++;
                                if(!checkIsDigit(textContent[textPtr])) {
                                    Token t = new Token(ERROR, getString(str, strPtr), "Unknown Input");
                                    list.add(t);
                                }else {
                                    while(checkIsDigit(textContent[textPtr])) {
                                        str[strPtr] = textContent[textPtr];
                                        textPtr++;
                                        strPtr++;
                                    }
                                    list.add(new Token(NEGATIVE_NUMBER, getString(str, strPtr)));
                                }
                                textPtr--;
                            } else {
                                list.add(new Token(NEGATIVE_NUMBER, getString(str, strPtr)));
                                textPtr--;
                            }
                        } else {
                            list.add(new Token(OPERATOR, getString(str, strPtr)));
                        }
                        break;
                    case '*': //乘号，包括运算符 *, *=
                        if(textContent[textPtr + 1] == '=') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '%': //取余，包括运算符 %, %=
                        if(textContent[textPtr + 1] == '=') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '=': //等号，包括运算符 =, ==
                        if(textContent[textPtr + 1] == '=') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '!': //非，包括运算符 =, !=
                        if(textContent[textPtr + 1] == '=') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '<': //小于号，包括运算符 <, <=, <<
                        if(textContent[textPtr + 1] == '=' || textContent[textPtr + 1] == '<') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '>': //大于号，包括运算符 >, >=, >>
                        if(textContent[textPtr + 1] == '=' || textContent[textPtr + 1] == '>') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '&': //与，包括运算符 &, &&
                        if(textContent[textPtr + 1] == '&') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '|': //或，包括运算符 |, ||
                        if(textContent[textPtr + 1] == '|') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                        }
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '~': case '^':
                        list.add(new Token(OPERATOR, getString(str, strPtr)));
                        break;
                    case '/': //除号，包括运算符 /, /=, 以及注释符号 // /*...*/
                        if(textContent[textPtr + 1] == '*') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            textPtr++;
                            boolean hasFinish = false;
                            while(textContent[textPtr] != '\0' && (textContent[textPtr] != '*' && textContent[textPtr+1] != '/')) {
                                str[strPtr] = textContent[textPtr];
                                strPtr++; textPtr++;
                            }
                            if(textContent[textPtr] == '\0') {
                                strPtr--;
                                list.add(new Token(ERROR, getString(str, strPtr), "Unknown Input"));
                                textPtr--;
                            } else if (textContent[textPtr] == '*') {
                                str[strPtr] = textContent[textPtr];
                                textPtr++;
                                strPtr++;
                                str[strPtr] = textContent[textPtr];
                                strPtr++;
                                list.add(new Token(COMMENT, getString(str, strPtr)));
                            }
                        } else if (textContent[textPtr + 1] == '/') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            textPtr++;
                            while (textContent[textPtr] != '\0' && textContent[textPtr] != '\n') {
                                str[strPtr] = textContent[textPtr];
                                strPtr++;
                                textPtr++;
                            }
                            list.add(new Token(COMMENT, getString(str, strPtr)));
                            textPtr--;
                        } else if (textContent[textPtr + 1] == '=') {
                            textPtr++;
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            list.add(new Token(OPERATOR, getString(str, strPtr)));
                        } else {
                            list.add(new Token(OPERATOR, getString(str, strPtr)));
                        }
                        break;
                    case '\"': //双引号
                        textPtr++;
                        while(textContent[textPtr] != '\0' && textContent[textPtr] != '\"') {
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            textPtr++;
                        }
                        if(textContent[textPtr] == '\0') {
                            strPtr--;
                            list.add(new Token(ERROR, getString(str, strPtr), "Unknown Input"));
                        } else if (textContent[textPtr] == '\"') {
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            list.add(new Token(STRING, getString(str, strPtr)));
                        }
                        break;
                    case '\'': //单引号
                        textPtr++;
                        while(textContent[textPtr] != '\0' && textContent[textPtr] != '\'') {
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            textPtr++;
                        }
                        if(textContent[textPtr] == '\0') {
                            strPtr--;
                            list.add(new Token(ERROR, getString(str, strPtr), "Unknown Input"));
                        } else if (textContent[textPtr] == '\'') {
                            str[strPtr] = textContent[textPtr];
                            strPtr++;
                            if(getString(str, strPtr).length() == 3)
                                list.add(new Token(CHARACTER, getString(str, strPtr)));
                            else
                                list.add(new Token(ERROR, getString(str, strPtr), "Length can only be 1"));
                        }
                        break;
                }
            }
            clearArray(str); strPtr = 0;
            textPtr++;
        }

        Iterator it = list.iterator();
        result = "";
        while(it.hasNext()) {
            result += it.next().toString() + "\n";
        }
    }

    public char[] clearArray (char[] c) {
        for(int i = 0; i < c.length; i++)
            c[i] = '\0';
        return c;
    }

    public String getString (char[] c, int ptr) {
        String s = "";
        for(int i = 0; i < ptr; i++) {
            s = s + c[i];
        }
        return s;
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

}
