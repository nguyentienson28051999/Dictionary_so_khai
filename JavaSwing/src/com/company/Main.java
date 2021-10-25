package com.company;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import org.json.JSONArray;
public class Main {
    public static void main(String[] args) {
        Main main = new Main();


        final JFrame[] f = {new JFrame()};
        JPanel panel = new JPanel();
        JFrame frame_add = new JFrame("Thêm từ mới");
        JFrame frame_fix = new JFrame("Sửa từ");
        JLabel themtu = new JLabel();

        final int[] index_this_word = {0};

        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        String[][] all_data = dictionaryManagement.insertFromFile("src\\com\\company\\dictionaries.txt");
        Word[] all_word = new Word[all_data[0].length];
        final Word[][] all_word_present = {new Word[all_data[0].length]};
        for (int i = 0; i< all_data[0].length; i++){
            Word temp_word = new Word(all_data[0][i], all_data[1][i]);
            all_word[i] = temp_word;
            all_word_present[0][i] = temp_word;
        }

        themtu.setText("Từ mới");
        themtu.setBounds(70,50,120,50);
        frame_add.add(themtu);
        frame_add.setSize(400, 400);
        frame_add.setLayout(new FlowLayout());
        JTextField add_tumoi = new JTextField(10);
        add_tumoi.setBounds(50,100,120,50);
        frame_add.add(add_tumoi);
        JLabel themnghia = new JLabel();
        themnghia.setText("Nghĩa mới");
        themnghia.setBounds(220,50,120,50);
        frame_add.add(themnghia);
        JTextField add_nghiamoi = new JTextField(10);
        add_nghiamoi.setBounds(200,100,120,50);
        frame_add.add(add_nghiamoi);
        JButton add_tumoi_xn = new JButton("Xác nhận");
        add_tumoi_xn.setBounds(120,200,120,50);
        frame_add.add(add_tumoi_xn);
        // Hết Frame thêm từ
        JButton d = new JButton("Sửa");// tạo thể hiện của JButton
        d.setBounds(200, 300, 100, 40);// trục x , y , width, height
        f[0].add(d);
        JLabel suatu = new JLabel();// Sửa từ
        suatu.setText("Từ sửa");
        suatu.setBounds(70,50,120,50);
        frame_fix.add(suatu);
        frame_fix.setSize(400, 400);
        frame_fix.setLayout(new FlowLayout());
        JTextField fix_tu = new JTextField(10);
        fix_tu.setBounds(50,100,120,50);
        frame_fix.add(fix_tu);
        JLabel fixnghia = new JLabel();
        fixnghia.setText("Nghĩa mới");
        fixnghia.setBounds(220,50,120,50);
        frame_fix.add(fixnghia);
        JTextField fix_nghiamoi = new JTextField(10);
        fix_nghiamoi.setBounds(200,100,120,50);
        frame_fix.add(fix_nghiamoi);
        JButton fix_tu_xn = new JButton("Xác nhận");
        fix_tu_xn.setBounds(120,200,120,50);
        frame_fix.add(fix_tu_xn);
        d.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame_fix.setLayout(null);
                frame_fix.setVisible(true);
                String tu = all_word_present[0][index_this_word[0]].word ;
                String nghia_moi = all_word_present[0][index_this_word[0]].mean ;
                fix_tu.setText(tu);
                fix_nghiamoi.setText(nghia_moi);
            }
        });// Hết sửa từ
        JTextField jTextField = new JTextField(10);
        panel.add(jTextField);
        panel.setBounds(50,50,120,50);
        f[0].add(panel);
        JScrollPane scrollPane = new JScrollPane();
        JTextArea txtMain = new JTextArea();
        txtMain.setWrapStyleWord(true);
        txtMain.setLineWrap(true);
        scrollPane.setViewportView(txtMain);
        scrollPane.setBounds(200,50,120,60);
        f[0].add(scrollPane);
        main.get_all_word(all_word_present[0]);
        JList[] jlist = {new JList(main.get_all_word(all_word_present[0]))};
        JScrollPane scrollList = new JScrollPane();
        scrollList.setViewportView(jlist[0]);
        scrollList.setBounds(50,100,120,300);
        f[0].add(scrollList, BorderLayout.CENTER);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 1) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        txtMain.setText(all_word_present[0][index].mean);
                        index_this_word[0] = index;
                    }
                }
            }
        };
        jlist[0].addMouseListener(mouseListener);
        jTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String a = jTextField.getText();
                if (a.length()>0){
                    Word[] all_data = dictionaryManagement.dictionaryLookup(all_word,jTextField.getText());
                    all_word_present[0] = all_data;
                }
                else{
                    all_word_present[0] = all_word;
                }
                JList[] jlist = {new JList(main.get_all_word(all_word_present[0]))};
                jlist[0].addMouseListener(mouseListener);
                scrollList.setViewportView(jlist[0]);
            }
        });
        JButton b = new JButton("Dịch online");// tạo thể hiện của JButton
        b.setBounds(200, 150, 100, 40);// trục x , y , width, height
        f[0].add(b);// thêm button vào JFrame
        JButton a = new JButton("Dịch offline");// tạo thể hiện của JButton
        a.setBounds(200, 200, 100, 40);// trục x , y , width, height
        f[0].add(a);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String google_dich = null;
                try {
                    google_dich = main.callTranslate("en", "vi",jTextField.getText());
                } catch (Exception ex) {
                    return;
                }
                txtMain.setText(google_dich);
            }
        });
        a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = jTextField.getText();
                for (int i = 0; i< all_word_present[0].length; i++){
                    if (all_word_present[0][i].word.equals(word)){
                        txtMain.setText(all_word_present[0][i].mean);
                        return;
                    }
                }
                txtMain.setText("Không tìm thấy từ phù hợp!");
            }
        });
        JButton c = new JButton("Thêm ");// tạo thể hiện của JButton
        c.setBounds(200, 250, 100, 40);// trục x , y , width, height
        c.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame_add.setLayout(null);
                frame_add.setVisible(true);
            }
        });
        f[0].add(c);
        JButton e = new JButton("Xoá");// tạo thể hiện của JButton
        e.setBounds(200, 350, 100, 40);// trục x , y , width, height
        f[0].add(e);
        e.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bạn không thể xoá Tuấn khỏi trái tim S!", "Thông báo xoá từ!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JButton g = new JButton("Voice");// tạo thể hiện của JButton
        g.setBounds(340, 50, 80, 40);// trục x , y , width, height
        f[0].add(g);
        g.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String test = jTextField.getText();
                if(!jTextField.getText().equals("")){
                    main.speak(jTextField.getText());
                }
            }
        });
        JLabel m = new JLabel("");
        m.setText("TỪ ĐIỂN ANH-VIỆT");
        m.setBounds(200,0,230,40);
        f[0].add(m);
        f[0].setTitle("TỪ ĐIỂN ANH-VIỆT");
        f[0].add(b);// thêm button vào JFrame

        add_tumoi_xn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tu_moi = add_tumoi.getText() ;
                String nghia_moi = add_nghiamoi.getText() ;
                all_word_present[0] = main.insert(all_word_present[0],tu_moi,nghia_moi);
                JList[] jlist = {new JList(main.get_all_word(all_word_present[0]))};
                scrollList.setViewportView(jlist[0]);
                jlist[0].addMouseListener(mouseListener);
                main.write_file(all_word_present);
                frame_add.dispose();
            }
        });
        fix_tu_xn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                all_word_present[0][index_this_word[0]].word = fix_tu.getText() ;
                all_word_present[0][index_this_word[0]].mean = fix_nghiamoi.getText();
                JList[] jlist = {new JList(main.get_all_word(all_word_present[0]))};
                scrollList.setViewportView(jlist[0]);
                jlist[0].addMouseListener(mouseListener);
                main.write_file(all_word_present);
                frame_fix.dispose();
            }
        });
        e.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Word[] new_arr = new Word[all_word_present[0].length-1];
                for (int i =0;i<all_word_present[0].length;i++){
                    if (i < index_this_word[0]){
                        new_arr[i] = all_word_present[0][i];
                    }
                    else if(i > index_this_word[0]){
                        new_arr[i-1] = all_word_present[0][i];
                    }
                }
                all_word_present[0] = new_arr;
                JList[] jlist = {new JList(main.get_all_word(all_word_present[0]))};
                scrollList.setViewportView(jlist[0]);
                jlist[0].addMouseListener(mouseListener);
                main.write_file(all_word_present);
                frame_fix.dispose();
//                    frame_add.dispatchEvent(new WindowEvent(frame_add, WindowEvent.WINDOW_CLOSING));
            }
        });

        f[0].setSize(500, 500);// thiết lập kích thước cho của sổ
        f[0].setLayout(null);// không sử dụng trình quản lý bố cục
        f[0].setVisible(true);// hiển thị cửa s
        f[0].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // Sắp xếp sau khi thêm từ mới vào mảng
    public Word[] sap_sep_mang(Word[] str){
        Word temp;
        for (int i = 0; i < str.length; i++)
        {
            for (int j = i + 1; j < str.length; j++) {
                if (str[i].word.compareTo(str[j].word)>0)
                {
                    temp = str[i];
                    str[i] = str[j];
                    str[j] = temp;
                }
            }
        }
        return str;
    }
    // Thêm từ mới vào mảng
    public static Word [] insert(Word [] arr, String tu_moi, String nghia_moi ) {
        int len = arr.length;
        Main main = new Main();
        Word [] tempArr = new Word [len + 1];
        for (int i = 0; i<len; i++){
            tempArr[i+1] = arr[i];
        }
        tempArr[0] = new Word(tu_moi,nghia_moi);
        return main.sap_sep_mang(tempArr);
    }
    // Lấy toàn bộ từ vựng để thêm vào jlist
    public String[] get_all_word( Word[] word){
        String[] data = new String[word.length];
        for(int i = 0;i<word.length;i++){
            data[i] = word[i].word;
        }
        return data;
    }
    // Gọi đến api của google để lấy nghĩa của từ
    public String callTranslate(String langFrom, String langTo, String word) throws Exception {

        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return parseResult(response.toString());
    }
    // Sử lý json để chuyển thành string
    public String parseResult(String inputJson) throws Exception {
        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
        return jsonArray3.get(0).toString();
    }
    // hàm dùng thư viện để phát âm từ tiếng anh
    public void speak(String word){
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            synthesizer.speakPlainText(
                    word, null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);

            // Deallocate the Synthesizer.
            synthesizer.deallocate();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Sau khi thêm và
    public void write_file(Word[][] all_word_present){
        try {
            String all_data = "";
            FileWriter myWriter = new FileWriter("src\\com\\company\\dictionaries.txt");
            for (int i=0;i< all_word_present[0].length;i++){
                if (i<all_word_present[0].length -1){
                    all_data += all_word_present[0][i].word+"\t"+all_word_present[0][i].mean+"\n";
                }
                else{
                    all_data += all_word_present[0][i].word+"\t"+all_word_present[0][i].mean;
                }
            }
            myWriter.write(all_data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException x) {
            System.out.println("An error occurred.");
            x.printStackTrace();
        }
    }
}