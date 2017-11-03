package utils;

import java.io.*;

/**
 * Created by paranoia on 2017/10/30.
 * 这个类用作文件的读取和写入
 */

public class FileIO {

    // 作用：读取txt文件内容
    public static String readIn(File file) {
        String code = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while((line = br.readLine()) != null)
                code += line + '\n';
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    // 作用：将内容覆盖写入txt中
    public static void writeTo(File file, String content) {
        checkFileExistence(file);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 作用：在txt结尾处添加内容
    public static void addLineTo(File file, String content) {
        checkFileExistence(file);
        String newContent = readIn(file) + content;
        writeTo(file, newContent);
    }

    public static void checkFileExistence(File file) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
