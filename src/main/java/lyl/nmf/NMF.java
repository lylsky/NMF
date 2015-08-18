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

    public NMF(){
        context = NMFContext.getContext();
    }

    public void Read(String filename){
        context.setHsiData(filename);
    }

    public static void main(String[] args){

    }
}
