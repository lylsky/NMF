package lyl.nmf;

//import Jama.Matrix;
//import SVD.SingularValueDecomposition;
import lyl.Matrix.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by lyl on 2015/8/15.
 */
public class NMF1 {
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

    Matrix A = null;
    Matrix S = null;
    Matrix X = null;




    public NMF1(String filename) throws IOException {
        HSIhdr hsi = new HSIhdr(filename, "");
        cols = hsi.getSamples();
        rows = hsi.getLines();
        bands = hsi.getBands();
        datatype = hsi.getDatatype();
        HSIRead hr = new HSIRead(filename);
        hr.read();
        X = new Matrix(Mat.FtoD(hr.getData()));

        A = new Matrix(J, bands);
        S = new Matrix(cols * rows * NN,J);
//        A = new Matrix(bands, J);
//        S = new Matrix(J, cols * rows * NN);
    }

    public void initAS() throws IOException {

        //A B*J
        BufferedReader br = new BufferedReader(new FileReader("InitA.txt"));
        double[][] v_A = A.getArray();
        for (int i = 0; i < J; i++) {
            for (int j = 0; j < bands; j++) {
                v_A[i][j] = Double.parseDouble(br.readLine());
            }
        }
        //S J*P
        br = new BufferedReader(new FileReader("InitS.txt"));
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

    public void linearMax(double[][] X, double lmin, double lmax) {
        int rows = X.length;
        int cols = X[0].length;
        int length = rows * cols;
        //Math.
    }

    public void In_PCA(double[][] U, double[][] X, int k){

    }

    public void In_S(Matrix eVptX, Matrix eVptA, Matrix s, Matrix d_S, double muS){
        int J = A.getRowDimension();
    }

    public void In_A(){

    }

    public void MVCMNF() {
        double muA = 3;
        double muS = 3;
        double tauA = (double)0.005;
        double stu = 2;
        double tol = (double) 1e-4;
        int maxiter = 500;
        double lambdaA = 5;

        int P = X.getRowDimension();
        int B = X.getColumnDimension();

        Matrix Up; //double[P][J];
        Matrix D; //double[J][J];
        Matrix Vp; //double[B][J];
        Matrix XVp; //double[P][J];
        Matrix UptX; //double[J][B];
        Matrix eXVp; //double[P][J + 1];
        Matrix eX; //double[P][B + 1];
        Matrix SSUM = new Matrix(P, J);

        double[] Ssum;
        double eX2, Xscale, Rscale, sqrnorm;

        Jama.SingularValueDecomposition svd = X.svd();

        //SingularValueDecomposition svd = new SingularValueDecomposition(X, 1);
        Up = (Matrix)svd.getU();
        Vp = (Matrix)svd.getV();
        D = (Matrix)svd.getS();
        XVp = Up.times(D);
        UptX = D.times(Vp.T());
        eXVp = XVp.addCol(stu);
        eX = X.addCol(stu);

        eX2 = eX.arrayTimes(eX).sum();
        Xscale = X.sum();
        Rscale = A.times(S).sum();
        sqrnorm = Math.sqrt(Rscale / Xscale);
        A = A.div(sqrnorm);
        S = S.div(sqrnorm);
        Ssum = S.sum(0);
        SSUM.setMatrix(Ssum, 0);
        for (int i = 0; i < J; i++) {
            SSUM.setCol(Ssum, i);
        }

        Matrix s; //= new double[P][J];
        Matrix d_S; //= new double[P][J];
        Matrix a; // = new double[J][B];
        Matrix d_A; // = new double[J][B];
        Matrix oldS, oldA, BUt;
        Matrix U = new Matrix(J - 1, P); // = new double[J - 1][P];
        Matrix C = new Matrix(J, J); // = new double[J][J];
        Matrix B_ = new Matrix(J - 1, J); // = new double[J - 1][J];
        Matrix uOnet = new Matrix(J, B); // = new double[J][B];
        double[] u;

        oldS = S;
        oldA = A;
        //In_PCA(U, X, J - 1);
        u = X.mean(1);
        C.setCol(1, 0);
        for (int i = 0; i < J - 1; i++) {
            B_.set(i, i + 1, 1);
        }
        BUt = U.T().times(B_); //P*J
        uOnet.setMatrix(u, 0);

        int iter = 0;
        for (; iter < maxiter; iter++) {
            Matrix Z, g, A0;
            Z = C.minus(A.minus(uOnet).times(BUt));
            g = Z.inverse().times(BUt.T());
            A0 = A;

            for (int loop = 0; loop < 50; loop++) {
                Matrix eA;
                double f0, f0_val, f0_quad;
                double f, f_val, f_quad;
                eA = A.addCol(stu);
                f0 = 0.5 * (eX2 - 2 * eX.times(eA.T()).arrayTimes(S).sum()
                            + eA.times(eA.T()).arrayTimes(S.T().times(S)).sum());
                Z = C.minus(A.minus(uOnet).times(BUt));
                f0_val = f0 + lambdaA * Math.log(Math.abs(Z.det()));
                f0_quad = f0 + lambdaA * (g.arrayTimes(A.minus(A0)).sum())
                            +0.5 * tauA * A.minus(A0).arrayTimes(A.minus(A0)).sum();

                for (int inner = 0; inner < 10; inner++) {
                    Matrix eVptA;
                    eVptA = Vp.T().times(A).addCol(stu);
                    //In_S(eVptA, eVptA, S, s, d_S, muS);
                    //In_A(XUp, A, S*Up, a, d_A, muA, A0, g, lambdaA, tauA);
                }
                eA = A.addCol(stu);
                f = 0.5 * (eX2 - 2 * eX.times(eA.T()).arrayTimes(S).sum()
                        + eA.times(eA.T()).arrayTimes(S.T().times(S)).sum());
                Z = C.minus(A.minus(uOnet).times(BUt));
                f_val = f + lambdaA * Math.log(Math.abs(Z.det()));
                f_quad = f + lambdaA * (g.arrayTimes(A.minus(A0)).sum())
                        +0.5 * tauA * A.minus(A0).arrayTimes(A.minus(A0)).sum();
                if(f0_quad >= f_quad){
                    int It = 0;
                    while (f0_val < f_val){
                        A = A.plus(A0).div(2);
                        eA = A.addCol(stu);
                        Z = C.minus(A.minus(uOnet).times(BUt));
                        f = 0.5 * (eX2 - 2 * eX.times(eA.T()).arrayTimes(S).sum()
                                + eA.times(eA.T()).arrayTimes(S.T().times(S)).sum());
                        f_val = f + lambdaA * Math.log(Math.abs(Z.det()));
                        if (++It > 10){
                            break;
                        }
                    }
                    break;
                }
            }

            double dtol_S = Math.sqrt(oldS.minus(S).arrayTimes(oldS.minus(S)).sum())
                            / oldS.arrayTimes(oldS).sum();
            double dtol_A = Math.sqrt(oldA.minus(A).arrayTimes(oldA.minus(A)).sum())
                            / oldA.arrayTimes(oldA).sum();

            if (dtol_S < tol && dtol_A < tol){
                break;
            }

            oldA = A;
            oldS = S;
            if(iter % 100 == 0){
                System.out.println("iter = " + iter);
            }

        }
        System.out.println("iter = " + iter);
        S = S.maxM(0);
        A = A.maxM(0);

    }

    public static void main(String[] args){
        System.out.print("dddd");

    }
}
