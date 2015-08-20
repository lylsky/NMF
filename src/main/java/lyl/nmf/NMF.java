package lyl.nmf;

import com.mathworks.toolbox.javabuilder.MWException;
import lyl.context.NMFContext;

/**
 * Created by lyl on 2015/8/18.
 */

/**
 * NMF��������
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
        try {
            mvcnmf.NMFiter();
        } catch (MWException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        NMF nmf = new NMF();

        nmf.read("E:\\HSR\\URBANbil");
        nmf.process();


    }
}
