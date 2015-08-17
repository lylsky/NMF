package lyl.nmf;

import Jama.Matrix;
import SVD.JTool;
//import SVD.SingularValueDecomposition;
import  Jama.SingularValueDecomposition;
import java.io.*;
/**
 * Created by jingle on 2015/8/16.
 */
public class test {

    public static void main(String[] args) throws IOException{
//        float[][] A = {{1,2,3},{2,2,1},{3,2,1},{1,1,3}};
        float[][] A = null;
        HSIRead hr = new HSIRead("E:\\HSR\\URBANbil");
        hr.read();
        A = hr.getData();
        long t1 = System.currentTimeMillis();
        Matrix matrix = new Matrix(JTool.ftoD(A));
        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
//        SingularValueDecomposition svd = new SingularValueDecomposition(JTool.ftoD(A),10);
        long t2 = System.currentTimeMillis();
        System.out.println("time:"+ (t2-t1));
        double[][] U = svd.getU().getArray();
        double[][] V = svd.getV().getArray();
        double[][] S = svd.getS().getArray();
//        double[][] U = svd.getU();
//        double[][] V = svd.getV();
//        double[][] S = svd.getS();
        FileWriter fw = new FileWriter("H:\\jingle\\Data\\U.txt");
        for (int i = 0; i < U.length; i++) {
            for (int j = 0; j < 5; j++) {
                fw.write(U[i][j] + "\t");
//                System.out.print(U[i][j] + "\t");
            }
            fw.write('\n');
        }

        fw = new FileWriter("H:\\jingle\\Data\\V.txt");

        for (int i = 0; i < V.length; i++) {
            for (int j = 0; j < 5; j++) {
                fw.write(V[i][j] + "\t");
//                System.out.print(V[i][j] + "\t");
            }
            fw.write("\n");
//            System.out.println();
        }
        fw.close();

        fw = new FileWriter("H:\\jingle\\Data\\S.txt");

        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < 5; j++) {
                fw.write(S[i][j] + "\t");
//                System.out.print(V[i][j] + "\t");
            }
            fw.write("\n");
//            System.out.println();
        }
        fw.close();
    }


}
