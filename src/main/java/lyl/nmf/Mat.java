package lyl.nmf;

/**
 * Created by jingle on 2015/8/16.
 */
public class Mat {

    public static double[][] FtoD(float[][] mat1){
        int m = mat1.length;
        int n = mat1[0].length;
        double[][] mat = new double[m][n];
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                mat[i][j] = (double)mat1[i][j];
            }
        }
        return mat;
    }

    public static double[][] add(double[][] mat1, double[][] mat2){
        int m = mat1.length;
        int n = mat1[0].length;
        assert (m == mat2.length && n == mat2[0].length);
        double[][] mat = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = mat1[i][j] + mat2[i][j];
            }
        }
        return mat;
    }

    public static double[][] sub(double[][] mat1, double[][] mat2){
        int m = mat1.length;
        int n = mat1[0].length;
        assert (m == mat2.length && n == mat2[0].length);
        double[][] mat = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = mat1[i][j] - mat2[i][j];
            }
        }
        return mat;
    }

    public static double[][] mult(double[][] mat1, double[][] mat2) {
        int m = mat1.length;
        int l = mat1[0].length;
        if(l != mat2.length) {
            System.out.println("维数错误！");
            return new double[0][0];
        }
        int n = mat2[0].length;
        double[][] mul = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double t = 0;
                for (int k = 0; k < l; k++) {
                    t += mat1[i][k] * mat2[k][j];
                }
                mul[i][j] = t;

            }
        }

        return mul;
    }

    public static void mult(double[][] mat1, double var1) {
        int m = mat1.length;
        int n = mat1[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat1[i][j] *= var1;

            }
        }
    }

    public static double[][] elemMult(double[][] mat1, double[][] mat2){
        int m = mat1.length;
        int n = mat1[0].length;
        double[][] mat = null;
        if(m != mat2.length || n != mat2[0].length){
            System.out.println("Dimension error.");
            return mat;
        }
        mat = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = mat1[i][j] * mat2[i][j];
            }
        }
        return mat;
    }

    public static double[][] multTr(double[][] mat1, double[][] mat2) {
        int m = mat1.length;
        int l = mat1[0].length;
        if(l != mat2[0].length) {
            System.out.println("Dimension error.");
            return new double[0][0];
        }
        int n = mat2.length;
        double[][] mul = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double t = 0;
                for (int k = 0; k < l; k++) {
                    t += mat1[i][k] * mat2[j][k];
                }
                mul[i][j] = t;
            }
        }
        return mul;
    }


    public static double[][] trMult(double[][] mat1, double[][] mat2) {
        int m = mat1[0].length;
        int l = mat1.length;
        if(l != mat2.length) {
            System.out.println("Dimension error.");
            return new double[0][0];
        }
        int n = mat2[0].length;
        double[][] mul = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double t = 0;
                for (int k = 0; k < l; k++) {
                    t += mat1[k][i] * mat2[k][j];
                }
                mul[i][j] = t;
            }
        }
        return mul;
    }

    /**
     * 矩阵除以var1
     * @param mat1
     * @param var1
     * @return
     */
    public static double[][] div(double[][] mat1, double var1){
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                mat1[i][j] /= var1;
            }
        }
        return mat1;
    }

    /**
     * mat1元素除以mat2的对应元素，返回mat1
     * @param mat1
     * @param mat2
     * @return
     */
    public static double[][] elemDiv(double[][] mat1, double[][] mat2){
        int m = mat1.length;
        int n = mat1[0].length;
        assert m == mat2.length;
        assert n == mat2[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat1[i][j] /= mat2[i][j];
            }
        }
        return mat1;
    }

    /**
     * 对矩阵所有元素求和
     * @param mat1
     * @return
     */
    public static double sum(double[][] mat1){
        double sum = 0;
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                sum += mat1[i][j];
            }
        }
        return sum;
    }

    public static double[] sum1(double[][] mat1, int flag){
        double[] arr = null;
        if(flag == 0) {  //求行和
            int m = mat1.length;
            arr = new double[m];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < mat1[0].length; j++) {
                    arr[i] += mat1[i][j];
                }
            }
        }
        else if(flag == 1) {  //求列和
            int n = mat1[0].length;
            arr = new double[n];
            for (int i = 0; i < mat1.length; i++) {
                for (int j = 0; j < n; j++) {
                    arr[j] += mat1[i][j];
                }
            }
        }

        return arr;
    }

    /**
     * 求矩阵mean
     * @param mat1
     * @param dim 方向：列或行
     * @return
     */
    public static double[] mean(double[][] mat1, int dim){
        assert (dim == 0 || dim == 1);
        double[] arr = null;
        if(dim == 0){  // mat的列向量的mean
            arr = sum1(mat1, 0);
        }
        else if(dim == 1){  // mat的行向量的mean
            arr = sum1(mat1, 1);
        }
        int m = arr.length;
        for (int i = 0; i < m; i++) {
            arr[i] /= m;
        }
        return arr;
    }


    public static double[][] addRow(double[][] mat1, double var1){
        int m = mat1.length;
        int n = mat1[0].length;
        double[][] mat = new double[m + 1][n];
        copy(mat, mat1);
        for (int i = 0; i < n; i++) {
            mat[m][i] = var1;
        }
        return mat;
    }

    public static void setRow(double[][] mat1, double[] arr1, int row){
        int m = mat1[0].length;
        assert m == arr1.length;
        for (int i = 0; i < m; i++) {
            mat1[row][i] = arr1[i];
        }
    }

    public static double[][] addCol(double[][] mat1, double var1){
        int m = mat1.length;
        int n = mat1[0].length;
        double[][] mat = new double[m][n + 1];
        copy(mat, mat1);
        for (int i = 0; i < m; i++) {
            mat[i][n] = var1;
        }
        return mat;
    }

    /**
     * 为矩阵某一列设值
     * @param mat1
     * @param arr1
     * @param col 列号
     */
    public static void setCol(double[][] mat1, double[] arr1, int col){
        int m = mat1.length;
        if(m != arr1.length){
            System.out.println("Dimension error.");
            return;
        }
        for (int i = 0; i < m; i++) {
            mat1[i][col] = arr1[i];
        }

    }

    /**
     * 为某一列赋值
     * @param mat1
     * @param val 单值
     * @param col 列号
     */
    public static void setCol(double[][] mat1, double val, int col){
        for (int i = 0; i < mat1.length; i++) {
            mat1[i][col] = val;
        }
    }

    /**
     * 矩阵复制：将mat2复制给mat1
     * @param mat1
     * @param mat2
     */
    public static void copy(double[][] mat1, double[][] mat2){
        int m = mat2.length;
        int n = mat2[0].length;
        if(m > mat1.length || n > mat1[0].length){
            System.out.println("Failed to copy.");
            return;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat1[i][j] = mat2[i][j];
            }
        }
    }

}
