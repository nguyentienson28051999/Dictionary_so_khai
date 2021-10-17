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

        String[] list = content.split("\n");
        final String[] tu = new String[list.length];
        final String[] nghia = new String[list.length];
        for(int i=0; i< list.length; i++){
            String[] line = list[i].split("\t");
            tu[i] = line[0];
            nghia[i] = line[line.length - 1];
        }
        String[][] data = {tu,nghia};
        return data;
    }
    public String[][] dictionaryLookup(String[] word, String[] mean, String search){
        if (search.equals("")){
            return new String[][]{word, mean};
        }
        else{
            String all_word = "";
            String all_mean = "";
            for (int i = 0; i< word.length; i++){
                if(word[i].contains(search)){
                    all_word += word[i] +"!";
                    all_mean += mean[i] +"!";
                }
            }
            return new String[][]{all_word.split("!"), all_mean.split("!")};
        }
    }
}
