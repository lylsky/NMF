package lyl.nmf;

/**
 * Created by lyl on 2015/8/18.
 */

import lyl.Matrix.Matrix;
import lyl.context.NMFContext;
import lyl.sort.QuitSort;
import org.jblas.DoubleMatrix;
import org.jblas.Singular;

/**
 * NMF的实际运算
 */
public class MVCNMF {
    private double lmin;
    private double lmax;

    double muA = 3;
    double muS = 3;
    double tauA = (double)0.005;
    double stu = 2;
    double tol = (double) 1e-4;
    int maxiter = 500;
    double lambdaA = 5;

    private NMFContext context = NMFContext.getContext();
    Matrix A =null;
    Matrix S = null;
    Matrix X = NMFContext.getContext().getX();
    int J = context.getJ();

    private void dataFilter(){
        double[][] array = X.getArray();
        int len = array.length;
        int band = array[0].length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < band; j++) {
                if(array[i][j] <lmin) array[i][j]=lmin;
                if(array[i][j] >lmax) array[i][j]=lmax;
            }
        }
        X.minus(lmin);
        X.div(lmax);
    }

    public void In_S(Matrix eVptX, Matrix eVptA, Matrix s, Matrix d_S){
        int J = eVptA.getRowDimension();
        S = (eVptX.times(eVptA.T()).plus(s.plus(d_S).times(muS))).times((eVptA.times(eVptA.T()).plus(new Matrix(J, muS))).inverse());
        s = S.minus(d_S).maxM(0);
        d_S = d_S.minus(S.minus(s));
    }

    public void In_A(Matrix X, Matrix S, Matrix a, Matrix d_A, Matrix A0, Matrix g){
        int J = S.getColumnDimension();
        Matrix F, b;
        F = (S.times(S.T()).plus(new Matrix(J, lambdaA*tauA+muA))).inverse();
        b = S.T().times(X);
        A = F.times((a.plus(d_A)).times(muA).plus(b).plus(A0.times(lambdaA * tauA)).minus(g.times(lambdaA)));
        a = A.minus(d_A).maxM(0);
        d_A = d_A.minus(A.minus(a));
    }

    public void linearMax() {
        double Pmax = 0.9999;
        double Pmin = 0.00001;
        double[] data = X.getColumnPackedCopy();
        int len = data.length;

        QuitSort qs = new QuitSort();

        int high =len - (int) (len *Pmax);
        lmax = qs.TopK(data, high);

        int low = (int) (len *Pmin);
        lmin = qs.MinK(data, low - 1);
    }

    public void In_PCA(Matrix U,Matrix X){
        int N = U.getRowDimension();
        double[] mx;
        Matrix mXX = new Matrix(X.getColumnDimension(),X.getRowDimension());
        mx = X.mean(1);
        for (int i = 0; i < N; i++) {
            mXX.setCol(mx,i);
        }
        mXX = X.minus(mXX);
        mXX = mXX.times(mXX.T()).div(N);
        DoubleMatrix matrix = new DoubleMatrix(mXX.getArray());
        DoubleMatrix[] s = Singular.fullSVD(matrix);
        U = new Matrix(s[0].data,s[0].getColumns());
        int k  =J-1;

    }

    public void NMFiteror(){
        int P = X.getRowDimension();
        int B = X.getColumnDimension();


        A = context.getA();
        S = context.getS();

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

        Matrix s= new Matrix(P,J); //= new double[P][J];
        Matrix d_S= new Matrix(P,J); //= new double[P][J];
        Matrix a = new Matrix(J,B); // = new double[J][B];
        Matrix d_A = new Matrix(J,B); // = new double[J][B];
        Matrix oldS, oldA, BUt;
        Matrix U = new Matrix(J - 1, P); // = new double[J - 1][P];
        Matrix C = new Matrix(J, J); // = new double[J][J];
        Matrix B_ = new Matrix(J - 1, J); // = new double[J - 1][J];
        Matrix uOnet = new Matrix(J, B); // = new double[J][B];
        double[] u;

        oldS = S;
        oldA = A;
        In_PCA(U, X);
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
                    In_S(eVptA, eVptA, s, d_S);
                    In_A(XVp, Vp.times(S), a, d_A, A0, g);
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



}
