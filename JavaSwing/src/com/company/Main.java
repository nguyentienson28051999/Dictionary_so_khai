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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        File file = new File("src\\com\\company\\dictionaries.txt");
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
        final String[][] tu = {new String[list.length]};
        final String[][] nghia = {new String[list.length]};
        for(int i=0; i< list.length; i++){
            String[] line = list[i].split("\t");
            tu[0][i] = line[0];
            nghia[0][i] = line[line.length - 1];
        }
        JFrame f = new JFrame();// tạo thể hiện của JFrame
        JPanel panel = new JPanel();
        JFrame frame_add = new JFrame("Thêm từ mới");
        JLabel themtu = new JLabel();
        themtu.setText("Từ mới");
        themtu.setBounds(50,50,120,50);
        frame_add.add(themtu);
        frame_add.setSize(400, 400);
        frame_add.setLayout(new FlowLayout());
        JTextField add_tumoi = new JTextField(10);
        add_tumoi.setBounds(50,100,50,50);
        frame_add.add(add_tumoi);
        JTextField add_nghiamoi = new JTextField(10);
        add_nghiamoi.setBounds(200,100,50,50);
        frame_add.add(add_nghiamoi);
        JButton add_tumoi_xn = new JButton("Xác nhận");
        add_tumoi_xn.setBounds(50,250,50,50);
        frame_add.add(add_tumoi_xn);
        JTextField jTextField = new JTextField(10);
        //
        panel.add(jTextField);
        panel.setBounds(50,50,120,50);
        f.add(panel);
        JScrollPane scrollPane = new JScrollPane();
        JTextArea txtMain = new JTextArea();
        txtMain.setWrapStyleWord(true);
        txtMain.setLineWrap(true);
        scrollPane.setViewportView(txtMain);
        scrollPane.setBounds(200,50,120,60);
        f.add(scrollPane);
        JList[] jlist = {new JList(tu[0])};
        JScrollPane scrollList = new JScrollPane();
        scrollList.setViewportView(jlist[0]);
        scrollList.setBounds(50,100,120,300);
        f.add(scrollList, BorderLayout.CENTER);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 1) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        txtMain.setText(nghia[0][index]);
                    }
                }
            }
        };
        jlist[0].addMouseListener(mouseListener);
        jTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (jTextField.getText().equals("")){
                    JList[] jlist = {new JList(tu[0])};
                    scrollList.setViewportView(jlist[0]);
                }
                else{
                    String word = jTextField.getText();
                    String all = "";
                    for (int i = 0; i< tu[0].length; i++){
                        if(tu[0][i].contains(word)){
                            all += tu[0][i] +"!";
                        }
                    }
                    String[] tu_tim_kiem = all.split("!");
                    JList[] jlist = {new JList(tu_tim_kiem)};
                    scrollList.setViewportView(jlist[0]);
                }
            }
        });
        JButton b = new JButton("Dịch online");// tạo thể hiện của JButton
        b.setBounds(200, 150, 100, 40);// trục x , y , width, height
        f.add(b);// thêm button vào JFrame
        JButton a = new JButton("Dịch offline");// tạo thể hiện của JButton
        a.setBounds(200, 200, 100, 40);// trục x , y , width, height
        f.add(a);
        a.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = jTextField.getText();
                for (int i = 0; i< tu[0].length; i++){
                    if (tu[0][i].equals(word)){
                        txtMain.setText(nghia[0][i]);
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
        f.add(c);

        JButton d = new JButton("Sửa");// tạo thể hiện của JButton
        d.setBounds(200, 300, 100, 40);// trục x , y , width, height
        f.add(d);
        JButton e = new JButton("Xoá");// tạo thể hiện của JButton
        e.setBounds(200, 350, 100, 40);// trục x , y , width, height
        f.add(e);
        JButton g = new JButton("V");// tạo thể hiện của JButton
        g.setBounds(340, 50, 40, 40);// trục x , y , width, height
        f.add(g);
        JLabel m = new JLabel("");
        m.setText("TỪ ĐIỂN ANH-VIỆT");
        m.setBounds(200,0,230,40);
        f.add(m);
        f.setTitle("TỪ ĐIỂN ANH-VIỆT");
        f.add(b);// thêm button vào JFrame

        add_tumoi_xn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tu_moi = add_tumoi.getText() ;
                String nghia_moi = add_nghiamoi.getText() ;
                if (main.kiem_tra_trung(tu[0],tu_moi) > -1){
                    JOptionPane.showMessageDialog(null, "Từ này đã tồn tại!", "Thông báo lỗi!", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    tu[0] = main.insert(tu[0],tu_moi);
                    nghia[0] = main.insert(nghia[0],nghia_moi);
                    JList[] jlist = {new JList(tu[0])};
                    scrollList.setViewportView(jlist[0]);
                    jlist[0].addMouseListener(mouseListener);
                    try {
                        String all_data = "";
                        FileWriter myWriter = new FileWriter("src\\com\\company\\dictionaries.txt");
                        for (int i=0;i<tu[0].length;i++){
                            all_data += "\n"+tu[0][i]+"\t"+nghia[0][i];
                        }
                        myWriter.write(all_data);
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException x) {
                        System.out.println("An error occurred.");
                        x.printStackTrace();
                    }
                    frame_add.dispose();
//                    frame_add.dispatchEvent(new WindowEvent(frame_add, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        f.setSize(500, 500);// thiết lập kích thước cho của sổ
        f.setLayout(null);// không sử dụng trình quản lý bố cục
        f.setVisible(true);// hiển thị cửa s
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String[] sap_sep_mang(String[] str){
        String temp;
        for (int i = 0; i < str.length; i++)
        {
            for (int j = i + 1; j < str.length; j++) {
                if (str[i].compareTo(str[j])>0)
                {
                    temp = str[i];
                    str[i] = str[j];
                    str[j] = temp;
                }
            }
        }
        return str;
    }
    public int kiem_tra_trung(String[] array, String a){
        String temp;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i].equals(a)){
                return i;
            }
        }
        return -1;
    }
    public static String [] insert(String [] arr, String k) {
        int len = arr.length;
        Boolean check = false;
        String [] tempArr = new String [len + 1];
        for (int i = 0; i<len; i++){
            tempArr[i+1] = arr[i];
        }
        tempArr[0] = k;
        return tempArr;
    }
}