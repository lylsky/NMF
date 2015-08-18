package lyl.io;

import java.io.*;

/**
 * Created by chenyf on 15-7-9.
 */
public class HSIhdr {
    private String name;
    private String path;
    private int samples;
    private int lines;
    private int bands;
    private int datatype;
    private String interleave;

    public HSIhdr(String filename, String path){
        String[] tname = filename.split("\\.");
        name = tname[0] + ".hdr";
        this.path = path;
        try {
            readInfo();
        }catch (IOException e){
            System.out.println("failed to read!");
            e.printStackTrace();
        }

    }

    public void readInfo() throws IOException{
        InputStream is = new FileInputStream(path + name);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        String strleft,strright;
        while ((line = br.readLine()) != null){
            int pos = line.indexOf("=");
            if(pos > 0){
                strleft = line.substring(0, pos).trim();
                strright = line.substring(pos + 1).trim();
                if(strleft.equals("samples")){
                    samples = Integer.parseInt(strright);
                    continue;
                }
                if(strleft.equals("lines")){
                    lines = Integer.parseInt(strright);
                    continue;
                }
                if(strleft.equals("bands")){
                    bands = Integer.parseInt(strright);
                    continue;
                }
                if(strleft.equals("data type")){
                    datatype = Integer.parseInt(strright);
                    continue;
                }
                if(strleft.equals("interleave")){
                    interleave = strright;
                    break;
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getSamples() {
        return samples;
    }

    public int getLines() {
        return lines;
    }

    public int getDatatype() {
        return datatype;
    }

    public String getInterleave() {
        return interleave;
    }

    public int getBands() {
        return bands;
    }


}
