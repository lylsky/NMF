package lyl.nmf;

import Jama.SingularValueDecomposition;
import lyl.Matrix.Matrix;
import lyl.io.HSIRead;
import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;
import org.jblas.Singular;


import java.io.IOException;

/**
 * Created by lyl on 2015/8/18.
 */
public class TMain {
    public static void main(String[] args) throws IOException {
        HSIRead hsiRead = new HSIRead("E:\\HSR\\URBANbil");
        hsiRead.Read();
        double[][] data = hsiRead.getData();
        System.out.println("len:"+data.length+",:"+data[0].length);

        long l= 122500*192;

        long t1 =  System.currentTimeMillis();
        FloatMatrix matrix = new FloatMatrix(new Matrix(data).DTF());
        Singular.fullSVD(matrix);
    //    SingularValueDecomposition svd = new Matrix(data).svd();
        long t2 = System.currentTimeMillis();
        float[] sv = null;
        System.out.println("time:"+(t2-t1));
        System.out.println("");
    }
}
