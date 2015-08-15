package lyl.nmf;

import Jama.Matrix;
import org.apache.commons.math3.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by lyl on 2015/8/15.
 */
public class NMF {
    private int J = 5;
    private int NN = 1;
    private int cols;
    private int rows;
    private int bands;
    private int datatype;

    private float lmax;
    private float lmin;
//    private int inter;


//    private Matrix A = null;
//    private Matrix S = null;

    private float[][] A = null;
    private float[][] S = null;
    private float[][] X = null;




    public NMF(String filename) throws IOException {
        HSIhdr hsi = new HSIhdr(filename, "");
        cols = hsi.getSamples();
        rows = hsi.getLines();
        bands = hsi.getBands();
        datatype = hsi.getDatatype();
        HSIRead hr = new HSIRead(filename);
        hr.read();
        X = hr.getData();

        A = new float[bands][J];
        S = new float[J][cols * rows * NN];
//        A = new Matrix(bands, J);
//        S = new Matrix(J, cols * rows * NN);
    }

    public void initAS() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("InitA.txt"));
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < bands; j++) {
                A[j][i] = Float.parseFloat(br.readLine());
            }
        }

        br = new BufferedReader(new FileReader("InitS.txt"));
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < rows * cols; j++) {
                S[i][j] = Float.parseFloat(br.readLine());
                for (int k = 1; k < NN; k++) {
                    S[i][rows * cols * k + j] = S[i][j];
                }
            }
        }

        br.close();
    }

    public void linearMax(float[][] X, float lmin, float lmax) {
        int rows = X.length;
        int cols = X[0].length;
        int length = rows * cols;
        //Math.
    }

    public static void main(String[] args){
        System.out.print("dddd");

    }
}
