package lyl.context;

import lyl.Matrix.Matrix;
import lyl.io.NMFRead;

import java.io.IOException;

/**
 * Created by lyl on 2015/8/18.
 */
public class NMFContext {
    private static NMFContext  context = new NMFContext();
    private static int NN =1;
    private Matrix A;
    private Matrix S;
    private Matrix X;

    private NMFRead hsiData =null;

    private int J;

    public static int getNN() {
        return NN;
    }

    public static void setNN(int NN) {
        NMFContext.NN = NN;
    }

    public int getJ() {

        return J;
    }

    public void setJ(int j) {
        J = j;
    }

    private NMFContext(){}

    public static NMFContext getContext(){
        return context;
    }
    public void setHsiData(String filename) {

        try {
            this.hsiData = new NMFRead(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hsiData.Read();
        init();
    }

    private void init(){
        A = new Matrix(J,hsiData.getBands());
        S = new Matrix(hsiData.getRows()*hsiData.getCols()*NN,J);
        X = new Matrix(hsiData.getData());
        J = 5;
    }

    public Matrix getX() {
        return X;
    }

    public void setX(Matrix x) {
        X = x;
    }

    public Matrix getS() {
        return S;
    }

    public void setS(Matrix s) {
        S = s;
    }

    public Matrix getA() {
        return A;
    }

    public void setA(Matrix a) {
        A = a;
    }
}
