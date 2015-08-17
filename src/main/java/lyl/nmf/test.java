package lyl.nmf;

import SVD.SingularValueDecomposition;

import java.io.*;

/**
 * Created by jingle on 2015/8/16.
 */
public class test {

    public static void main(String[] args) throws IOException{
//        float[][] A = {{1,2,3},{2,2,1},{3,2,1},{1,1,3}};
        float[][] A = null;
        HSIRead hr = new HSIRead("H:\\jingle\\Data\\URBANbil");
        hr.read();
        A = hr.getData();
        SingularValueDecomposition svd = new SingularValueDecomposition(A);
        float[][] U = svd.getU(5);
        float[][] V = svd.getV(5);
        float[][] S = svd.getS(5);

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
