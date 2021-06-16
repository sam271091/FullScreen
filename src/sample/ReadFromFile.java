package sample;

import java.io.*;

public class ReadFromFile {

    public static String ReadFile(String filePath) {
        File file = new File(filePath);


        BufferedReader br = null;
        try {
//
            if (file.exists()){
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String st;
                while ((st = br.readLine()) != null) {
                    sb.append(st.toString());
//
                }

                return sb.toString().trim();
            } else {
                return null;
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    }


