package lyl.io;

import lyl.Matrix.Matrix;
import lyl.context.NMFContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by lyl on 2015/8/18.
 */
public class NMFRead extends HSIRead{

    public NMFRead(String filename) throws IOException {
        super(filename);
    }
    public void InitAS() throws IOException {
        NMFContext context = NMFContext.getContext();
        Matrix A = context.getA();
        Matrix S = context.getS();
        int J = context.getJ();
        int bands = getBands();
        int rows = getRows();
        int cols = getCols();
        int NN = context.getNN();
        //A J*BANDS
        BufferedReader br = new BufferedReader(new FileReader("Data/InitA.txt"));
        double[][] v_A = A.getArray();
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < bands; j++) {
                v_A[i][j] = Double.parseDouble(br.readLine());
            }
        }
        A = A.T();
        //S L*J
        br = new BufferedReader(new FileReader("Data/InitS.txt"));
        double[][] v_S = S.getArray();
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < rows * cols; j++) {
                v_S[j][i] = Double.parseDouble(br.readLine());
                for (int k = 1; k < NN; k++) {
                    v_S[rows * cols * k + j][i] = v_S[j][i];
                }
            }
        }

        br.close();
    }

}
