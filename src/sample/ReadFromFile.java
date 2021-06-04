package sample;

import java.io.*;

public class ReadFromFile {

    public static String ReadFile(String filePath) {
        File file = new File(filePath);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String st;
            while ((st = br.readLine()) != null) {
                sb.append(st.toString() + "\n");
//
            }

            return sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    }


