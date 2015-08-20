package lyl.nmf;

/**
 * Created by lyl on 2015/8/18.
 */

import SVD.SingularValueDecomposition;
import Svds.Svds;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import lyl.Matrix.Matrix;
import lyl.context.NMFContext;
import lyl.sort.QuitSort;



/**
 * NMF��ʵ������
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
    int P = X.getRowDimension();  //��Ԫ����
    int B = X.getColumnDimension(); //������
    Matrix Up = new Matrix(B,J);
    Matrix D = new Matrix(J,J);
    Matrix Vp = new Matrix(P,J);

    Matrix XVp;  // B*J
    Matrix UptX;
    Matrix eXVp;

    Matrix eX;
    Matrix SSUM = new Matrix(P, J);




    Matrix s= new Matrix(P,J); //= new double[P][J];
    Matrix d_S= new Matrix(P,J); //= new double[P][J];
    Matrix a = new Matrix(B,J); // = new double[J][B];
    Matrix d_A = new Matrix(B,J); // = new double[J][B];
    Matrix oldS, oldA, BUt;
    Matrix U = new Matrix(B,J - 1); // = new double[J - 1][P];
    Matrix C = new Matrix(J, J); // = new double[J][J];
    Matrix B_ = new Matrix(J , J-1); // = new double[J - 1][J];
    Matrix uOnet = new Matrix(B,J); // = new double[J][B];



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

    public void In_S(Matrix eVptA){
        int J = eVptA.getColumnDimension();
        S = (eVptA.T().times(eVptA).plus(new Matrix(J, muS))).inverse().times(eXVp.times(eVptA).plus(s.plus(d_S).times(muS)));
        s = S.minus(d_S).maxM(0);
        d_S = d_S.minus(S.minus(s));
    }
    public void In_A(Matrix S, Matrix A0, Matrix g){
        int J = S.getColumnDimension();
        Matrix F, b;
        F = (S.T().times(S).plus(new Matrix(J, lambdaA*tauA+muA))).inverse();
        b = eXVp.timesT(S);
        A = ((a.plus(d_A)).times(muA).plus(b).plus(A0.times(lambdaA * tauA)).minus(g.times(lambdaA))).times(F);
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
        dataFilter();
    }

    public void In_PCA(Matrix U,Matrix X) throws MWException {
        int N = U.getRowDimension();
        double[] mx;
        Matrix mXX = new Matrix(N,X.getColumnDimension());
        mx = X.mean(1);
        mXX.setMatrix(mx,1);
        mXX = X.minus(mXX);
        mXX = mXX.times(mXX.T()).div(N);
        Svds svd = new Svds();
        Object[] ss = svd.svds(J-1,mXX.getArray());
        MWNumericArray u = (MWNumericArray) ss[2];
        double[][] du = U.getArray();
        du= (double[][]) u.toDoubleArray();
    }

    /**
     * 将奇异值分解的结果赋到变量中
     * @param usv
     * @param Up
     * @param D
     * @param V
     */
    public void USV(Object[] usv,Matrix Up,Matrix D,Matrix V){
        MWNumericArray mup = (MWNumericArray)usv[0];
        double[][] up = Up.getArray();
        up = (double[][]) mup.toDoubleArray();

        /**
         * 这步可以优化，D可以为一维矩阵
         */
        MWNumericArray md = (MWNumericArray)usv[1];
        double[][] d = D.getArray();
        d= (double[][]) md.toDoubleArray();

        MWNumericArray mv = (MWNumericArray)usv[2];
        double[][] v = D.getArray();
        v = (double[][]) mv.toDoubleArray();
    }

    public void NMFiter() throws MWException {

        A = context.getA();    //J*B
        S = context.getS();   //P*J

        double[] Ssum;
        double eX2, Xscale, Rscale, sqrnorm;

        Svds svd = new Svds();
        Object[] usv = svd.svds(3,X.getArray(),J);
        USV(usv,Vp,D,Up);

        XVp = Up.times(D);  //
        UptX = Vp.times(D);
        eXVp = UptX.addCol(stu);
        eX = X.addCol(stu);

        eX2 = eX.arrayTimes(eX).sum();
        Xscale = X.sum();
        Rscale = A.timesT(S).sum();
        sqrnorm = Math.sqrt(Rscale / Xscale);
        A = A.div(sqrnorm);
        S = S.div(sqrnorm);
        Ssum = S.sum(0);
        SSUM.setMatrix(Ssum, 0);

        S.arrayLeftDivideEquals(SSUM);


        double[] u;   //new double[B]

        oldS = S;
        oldA = A;
        In_PCA(U, X);
        u = X.mean(1);
        C.setRow(1, 0);
        for (int i = 0; i < J - 1; i++) {
            B_.set(i+1, i , 1);
        }
        BUt = B_.timesT(U); // BUT [J][B]
        uOnet.setMatrix(u, 1);

        int iter = 0;
        for (; iter < maxiter; iter++) {
            Matrix Z, g, A0;
            Z = C.plus(BUt.times(A.minus(uOnet)));
//            g = Z.inverse().times(BUt.T());
            g=BUt.T().timesT(Z.inverse());
            A0 = A;

            for (int loop = 0; loop < 50; loop++) {
                Matrix eA;
                double f0, f0_val, f0_quad;
                double f, f_val, f_quad;
                eA = A.addRow(stu);
                f0 = 0.5 * (eX2 - 2 * eX.times(eA).arrayTimes(S).sum()
                        + eA.times(eA.T()).arrayTimes(S.T().times(S)).sum());
                Z = C.plus(BUt.times(A.minus(uOnet)));
                f0_val = f0 + lambdaA * Math.log(Math.abs(Z.det()));
                f0_quad = f0 + lambdaA * (g.arrayTimes(A.minus(A0)).sum())
                        +0.5 * tauA * A.minus(A0).arrayTimes(A.minus(A0)).sum();

                for (int inner = 0; inner < 10; inner++) {
                    Matrix eVptA;
                    eVptA = Up.T().times(A).addRow(stu);
                    In_S(eVptA);
                    In_A(Vp.times(S),A0, g);
                }
                eA = A.addRow(stu);
                f = 0.5 * (eX2 - 2 * eX.times(eA).arrayTimes(S).sum()
                        + eA.times(eA.T()).arrayTimes(S.T().times(S)).sum());
                Z = C.plus(BUt.times(A.minus(uOnet)));
                f_val = f + lambdaA * Math.log(Math.abs(Z.det()));
                f_quad = f + lambdaA * (g.arrayTimes(A.minus(A0)).sum())
                        +0.5 * tauA * A.minus(A0).arrayTimes(A.minus(A0)).sum();
                if(f0_quad >= f_quad){
                    int It = 0;
                    while (f0_val < f_val){
                        A = A.plus(A0).div(2);
                        eA = A.addRow(stu);
                        f = 0.5 * (eX2 - 2 * eX.times(eA).arrayTimes(S).sum()
                                + eA.times(eA.T()).arrayTimes(S.T().times(S)).sum());
                        Z = C.plus(BUt.times(A.minus(uOnet)));
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
