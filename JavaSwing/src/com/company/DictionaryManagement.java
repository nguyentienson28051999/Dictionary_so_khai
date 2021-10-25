package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DictionaryManagement {
    public String[][] insertFromFile(String name_file){
        File file = new File(name_file);
        String content = null;
        try {
            try (InputStream in = new FileInputStream(file))
            {
                byte[] bytes  = new byte[(int)file.length()];

                int offset = 0;
                while (offset < bytes.length)
                {
                    int result = in.read(bytes, offset, bytes.length - offset);
                    if (result == -1) {
                        break;
                    }
                    offset += result;
                }
                content = new String(bytes, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Chia file thành các dòng
        String[] list = content.split("\n");
        final String[] tu = new String[list.length];
        final String[] nghia = new String[list.length];
        for(int i=0; i< list.length; i++){
            // Chia nghĩa và từ theo đấu tab
            String[] line = list[i].split("\t");
            //Kiểm tra nếu không có dữ liệu thì để rỗng
            if (line.length==2){
                tu[i] = line[0];
                nghia[i] = line[line.length - 1];
            }
            else {
                tu[i] = "";
                nghia[i] = "";
            }
        }
        String[][] data = {tu,nghia};
        return data;
    }

    // Tìm kiếm các từ tương tự có chứa chuỗi kí tự ở ô tìm kiếm
    public Word[] dictionaryLookup(Word[] word, String search){
        String all_word = "";
        String all_mean = "";
        for (int i = 0; i< word.length; i++){
            if(word[i].word.contains(search)){
                all_word += word[i].word +"!";
                all_mean += word[i].mean +"!";
            }
        }
        String[] arr_word = all_word.split("!");
        String[] arr_mean = all_mean.split("!");
        Word[] new_word = new Word[arr_word.length];
        for (int i=0;i<arr_word.length;i++){
            new_word[i] = new Word(arr_word[i],arr_mean[i]);
        }
        return new_word;
    }
}