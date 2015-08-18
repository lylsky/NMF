package lyl.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by chenyf on 15-7-9.
 */
public class HSIRead {
    private int pixels;
    private int datatype;
    private int bands;
    private int rows;
    private int cols;
    private String interleave;
    private float[][] fdata = null;
    private short[][] idata = null;
    private float[][] data = null;
    private String filename;

    public HSIRead(String filename){
        HSIhdr header = new HSIhdr(filename, "");
        this.filename = filename;
        cols = header.getSamples();
        rows = header.getLines();
        pixels = cols * rows;
        datatype = header.getDatatype();
        bands = header.getBands();
        interleave = header.getInterleave().toLowerCase();
        //data = new float[pixels][bands];
    }

    public void read() throws IOException{

        int inter = 0;
        if(interleave.equals("bil")){
            inter = 1;
        }else if(interleave.equals("bip")){
            inter = 2;
        }else if(interleave.equals("bsq")){
            inter = 3;
        }
        switch (inter){
            case 1:
                readBIL();
                break;
            case 2:
                readBIP();
                break;
            case 3:
                readBSQ();
                break;
            default:
                System.out.println("不支持该格式！");
        }
    }
    public void readBIL() throws IOException{
//        byte[] tmp = new byte[pixels * bands * datatype];
//        InputStream is = new FileInputStream(filename);
//        BufferedInputStream bis = new BufferedInputStream(is);
//        bis.read(tmp);
        RandomAccessFile file = new RandomAccessFile(filename, "r");
        FileChannel ch = file.getChannel();
        MappedByteBuffer imgbuf = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

//        int n = 0;
        data = new float[pixels][bands];
        switch (datatype){
            case 2:
//                idata = new short[pixels][bands];
                for(int i = 0; i < rows; i++){
                    int p = i * cols;
                    for(int j = 0; j < bands; j++){
                        for(int k = 0; k < cols; k++){
                            data[p + k][j] = (float)((imgbuf.get() & 0xff) | (imgbuf.get() << 8));
                        }
                    }
                }
                break;
            case 4:
//                fdata = new float[pixels][bands];
                for(int i = 0; i < rows; i++){
                    int p = i * cols;
                    for(int j = 0; j < bands; j++){
                        for(int k = 0; k < cols; k++){
                            int t=(int)((imgbuf.get()&0xff)|(imgbuf.get()<<8)&0xffff |
                                    (imgbuf.get()<<16)&0xffffff | (imgbuf.get()<<24));
                            data[p+k][j]=Float.intBitsToFloat(t);
                        }
                    }
                }
                break;
            default:
                System.out.println("不支持该格式！");
        }

    }

    public void readBIP() throws IOException{

    }

    public void readBSQ() throws IOException{

    }

    public float[][] getfData(){
        return fdata;
    }
    public short[][] getiData() { return idata; }
    public float[][] getData() { return data; }

}
