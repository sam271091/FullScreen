package sample;

import java.io.*;

public class ReadFromFile {

    public static String ReadFile(String filePath) {
        File file = new File(filePath);



        BufferedReader br = null;
        try {
//
            if (file.exists()){

                FileInputStream fis = new FileInputStream(file);

                InputStreamReader iSr = new InputStreamReader(fis,"UTF-8");

                br = new BufferedReader(iSr);
                StringBuilder sb = new StringBuilder();
                String st;
                while ((st = br.readLine()) != null) {
                    sb.append(st.toString());
//
                }

                fis.close();
                iSr.close();
                br.close();

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


