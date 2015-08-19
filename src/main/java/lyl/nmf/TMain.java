package lyl.nmf;

import lyl.io.HSIRead;
import org.jblas.DoubleMatrix;
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
        DoubleMatrix matrix = new DoubleMatrix(data);
        DoubleMatrix s = Singular.SVDValues(matrix);

        long t2 = System.currentTimeMillis();
        double[] sv = s.data;
        System.out.println(sv.length+":"+sv[0]);
        System.out.println("time:"+(t2-t1));
        System.out.println("");
    }
}
