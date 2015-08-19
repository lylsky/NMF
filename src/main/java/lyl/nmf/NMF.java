package lyl.nmf;

import lyl.context.NMFContext;

/**
 * Created by lyl on 2015/8/18.
 */

/**
 * NMFµÄÖ÷º¯Êý
 */
public class NMF {
    private NMFContext context;
    private MVCNMF mvcnmf;
    public NMF(){
        context = NMFContext.getContext();
    }

    public void read(String filename){
        context.setHsiData(filename);
    }

    public void process(){

        mvcnmf = new MVCNMF();
        mvcnmf.linearMax();
        mvcnmf.NMFiter();

    }

    public static void main(String[] args){
        NMF nmf = new NMF();

        nmf.read("E:\\HSR\\URBANbil");
        nmf.process();


    }
}
