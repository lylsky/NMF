package lyl.nmf;

import Jama.Matrix;
import SVD.SingularValueDecomposition;
import lyl.io.HSIRead;
import lyl.io.HSIhdr;

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

    private double lmax;
    private double lmin;
//    private int inter;


//    private Matrix A = null;
//    private Matrix S = null;

    private double[][] A = null;
    private double[][] S = null;
    private double[][] X = null;




    public NMF(String filename) throws IOException {
        HSIhdr hsi = new HSIhdr(filename, "");
        cols = hsi.getSamples();
        rows = hsi.getLines();
        bands = hsi.getBands();
        datatype = hsi.getDatatype();
        HSIRead hr = new HSIRead(filename);
        hr.read();
        X = Mat.FtoD(hr.getData());

        A = new double[J][bands];
        S = new double[cols * rows * NN][J];
//        A = new Matrix(bands, J);
//        S = new Matrix(J, cols * rows * NN);
    }

    public void initAS() throws IOException {

        //A B*J
        BufferedReader br = new BufferedReader(new FileReader("InitA.txt"));
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < bands; j++) {
                A[i][j] = Double.parseDouble(br.readLine());
            }
        }
        //S J*P
        br = new BufferedReader(new FileReader("InitS.txt"));
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < rows * cols; j++) {
                S[j][i] = Double.parseDouble(br.readLine());
                for (int k = 1; k < NN; k++) {
                    S[rows * cols * k + j][i] = S[j][i];
                }
            }
        }

        br.close();
    }

    public void linearMax(double[][] X, double lmin, double lmax) {
        int rows = X.length;
        int cols = X[0].length;
        int length = rows * cols;
        //Math.
    }

    public void In_PCA(double[][] U, double[][] X, int k){

    }

    public void MVCMNF() {
        double muA = 3;
        double muS = 3;
        double tauA = (double)0.005;
        double stu = 2;
        double tol = (double) 1e-4;
        int maxiter = 500;
        double lambdaA = 5;

        int P = X.length;
        int B = X[0].length;

        double[][] Up; //double[P][J];
        double[][] D; //double[J][J];
        double[][] Vp; //double[B][J];
        double[][] XVp; //double[P][J];
        double[][] UptX; //double[J][B];
        double[][] eXVp; //double[P][J + 1];
        double[][] eX; //double[P][B + 1];
        double[][] SSUM = new double[P][J];

        double[] Ssum;
        double eX2, Xscale, Rscale, sqrnorm;

        SingularValueDecomposition svd = new SingularValueDecomposition(X, 1);
        Up = svd.getU();
        Vp = svd.getV();
        D = svd.getS();
        XVp = Mat.mult(Up, D);
        UptX = Mat.multTr(D, Vp);
        eXVp = Mat.addCol(XVp, stu);
        eX = Mat.addCol(X, stu);

        eX2 = Mat.sum(Mat.elemMult(eX, eX));
        Xscale = Mat.sum(X);
        Rscale = Mat.sum(Mat.mult(A, S));
        sqrnorm = (double)Math.sqrt(Rscale / Xscale);
        A = Mat.div(A, sqrnorm);
        S = Mat.div(S, sqrnorm);
        Ssum = Mat.sum1(S, 0);
        for (int i = 0; i < J; i++) {
            Mat.setCol(SSUM, Ssum, i);
        }

        double[][] s = new double[P][J];
        double[][] d_S = new double[P][J];
        double[][] a = new double[J][B];
        double[][] d_A = new double[J][B];
        double[][] oldS, oldA, BUt;
        double[][] U = new double[J - 1][P];
        double[][] C = new double[J][J];
        double[][] B_ = new double[J - 1][J];
        double[][] uOnet = new double[J][B];
        double[] u;

        oldS = S;
        oldA = A;
        In_PCA(U, X, J - 1);
        u = Mat.mean(X, 1);
        Mat.setCol(C, 1D, 0);
        for (int i = 0; i < J - 1; i++) {
            B_[i][i + 1] = 1;
        }
        BUt = Mat.trMult(U, B_); //P*J
        for (int i = 0; i < J; i++) {
            Mat.setRow(uOnet, u, i);
        }

        for (int iter = 0; iter < maxiter; iter++) {
            double[][] Z, g, A0;
            Z = Mat.sub(C, Mat.mult(Mat.sub(A, uOnet), BUt));
            g = new Matrix(Z).inverse().times(new Matrix(BUt).transpose()).getArray();
            A0 = A;

            for (int loop = 0; loop < 50; loop++) {
                double[][] eA;
                double f0, f0_val, f0_quad;
                double f, f_val, f_quad;
                eA = Mat.addCol(A, stu);
//                f0 = 0.5 *
            }

        }




    }

    public static void main(String[] args){
        System.out.print("dddd");

    }
}
