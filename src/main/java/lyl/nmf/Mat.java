package lyl.nmf;

/**
 * Created by jingle on 2015/8/16.
 */
public class Mat {
    public static float[][] mult(float[][] mat1, float[][] mat2) {
        int m = mat1.length;
        int l = mat1[0].length;
        if(l != mat2.length) {
            System.out.println("维数错误！");
            return new float[0][0];
        }
        int n = mat2[0].length;
        float[][] mul = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                float t = 0;
                for (int k = 0; k < l; k++) {
                    t += mat1[i][k] * mat2[k][j];
                }
                mul[i][j] = t;

            }
        }

        return mul;
    }

    public static void mult(float[][] mat1, float var1) {
        int m = mat1.length;
        int n = mat1[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat1[i][j] *= var1;

            }
        }
    }

    public static float[][] elemMult(float[][] mat1, float[][] mat2){
        int m = mat1.length;
        int n = mat1[0].length;
        float[][] mat = null;
        if(m != mat2.length || n != mat2[0].length){
            System.out.println("Dimension error.");
            return mat;
        }
        mat = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = mat1[i][j] * mat2[i][j];
            }
        }
        return mat;
    }

    public static float[][] multTr(float[][] mat1, float[][] mat2) {
        int m = mat1.length;
        int l = mat1[0].length;
        if(l != mat2[0].length) {
            System.out.println("维数错误！");
            return new float[0][0];
        }
        int n = mat2.length;
        float[][] mul = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                float t = 0;
                for (int k = 0; k < l; k++) {
                    t += mat1[i][k] * mat2[j][k];
                }
                mul[i][j] = t;
            }
        }
        return mul;
    }

    public static float[][] trMult(float[][] mat1, float[][] mat2) {
        int m = mat1[0].length;
        int l = mat1.length;
        if(l != mat2.length) {
            System.out.println("维数错误！");
            return new float[0][0];
        }
        int n = mat2[0].length;
        float[][] mul = new float[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                float t = 0;
                for (int k = 0; k < l; k++) {
                    t += mat1[k][i] * mat2[k][j];
                }
                mul[i][j] = t;
            }
        }
        return mul;
    }

    public static float[][] div(float[][] mat1, float var1){
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                mat1[i][j] /= var1;
            }
        }
        return mat1;
    }
    
    
    public static float sum(float[][] mat1){
        float sum = 0;
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                sum += mat1[i][j];
            }
        }
        return sum;
    }



    public static float[][] addRow(float[][] mat1, float var1){
        int m = mat1.length;
        int n = mat1[0].length;
        float[][] mat = new float[m + 1][n];
        copy(mat, mat1);
        for (int i = 0; i < n; i++) {
            mat[m][i] = var1;
        }
        return mat;
    }

    public static float[][] addCol(float[][] mat1, float var1){
        int m = mat1.length;
        int n = mat1[0].length;
        float[][] mat = new float[m][n + 1];
        copy(mat, mat1);
        for (int i = 0; i < m; i++) {
            mat[i][n] = var1;
        }
        return mat;
    }

    public static void copy(float[][] mat1, float[][] mat2){
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
