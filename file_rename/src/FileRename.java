package file_rename;

import java.util.Scanner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.*;

import java.net.URLDecoder;

// 正則表達式
import java.util.regex.Pattern;
import java.util.regex.Matcher;
// import java.util.regex.*;

// ******************************
// *
// * author   : 施冠年
// * date     : 2019/10/28
// * mail     : Co6901251@gmail.com
// *
// * function :
// *
// * 移除迅雷下載的殘留檔名(.bt.xltd)
// *
// * 用法 :
// * 輸入資料夾路徑，自動更名
// *
// ******************************

public class FileRename {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // * Input directory path

        System.out.print("Input directory path : ");
        Scanner input   = new Scanner(System.in);
        String dir_path = input.nextLine();
        input.close();

        File files = null;
        try {
            files = new File(URLDecoder.decode(dir_path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!files.isDirectory()) {
            System.out.println(dir_path + " is not a Directory !");
            return;
        }

        FileRename fileRename = new FileRename();

        fileRename.ThunderDownloader(dir_path, files);
    }

    // 迅雷下載，移除 .bt.xltd
    void ThunderDownloader(String dir_path, File files) {

        if (files == null) { return; }

        int rename_cnt = 0;
        int files_cnt  = 0;

        for (String old_file_name : files.list()) { // 檔案清單

            if (Pattern.matches(".+\\.bt\\.xltd", old_file_name)) {

                // Remove ".bt.xltd"
                String new_file_name = old_file_name.replace(".bt.xltd", "");
                System.out.println(old_file_name + " -> " + new_file_name);

                // Rename
                File rename_file = new File(dir_path + "/" + old_file_name);
                rename_file.renameTo(new File(dir_path + "/" + new_file_name));

                ++rename_cnt;
            }

            ++files_cnt;
        }

        System.out.println("Rename finish ! ( " + rename_cnt + " / " + files_cnt + " )");
    }
}