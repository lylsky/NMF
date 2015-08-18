package lyl.Matrix;

/**
 * Created by jingle on 2015/8/17.
 */

public class Matrix extends Jama.Matrix {
    public Matrix(int var1, int var2){
        super(var1, var2);
    }

    public Matrix(int var1, int var2, double var3){
        super(var1, var2, var3);
    }

    public Matrix(double[][] var1){
        super(var1);
    }

    public Matrix(double[][] var1, int var2, int var3){
        super(var1, var2, var3);
    }

    public Matrix(double[] var1, int var2){
        super(var1, var2);
    }

    public Matrix(int var1, double val){
        super(var1, var1);
        double[][] var2 = this.getArray();
        for (int i = 0; i < var1; i++) {
            var2[i][i] = val;
        }
    }

    public Matrix times(Matrix matrix) {
        return (Matrix)super.times(matrix);
    }
    public Matrix times(double val) {
        return (Matrix)super.times(val);
    }

    public Matrix arrayTimes(Matrix matrix) {
        return (Matrix)super.arrayTimes(matrix);
    }

    public Matrix T() {
        return (Matrix)super.transpose();
    }

    public Matrix plus(Jama.Matrix matrix) {
        return (Matrix)super.plus(matrix);
    }

    public Matrix minus(Jama.Matrix matrix) {
        return (Matrix)super.minus(matrix);
    }

    public Matrix inverse() {
        return (Matrix)super.inverse();
    }

    public Matrix div(double var1) {
        double[][] var2 = this.getArray();
        for (int i = 0; i < var2.length; i++) {
            for (int j = 0; j < var2[0].length; j++) {
                var2[i][j] /= var1;
            }
        }
        return this;
    }
    public Matrix minus(double var1) {
        double[][] var2 = this.getArray();
        for (int i = 0; i < var2.length; i++) {
            for (int j = 0; j < var2[0].length; j++) {
                var2[i][j] -= var1;
            }
        }
        return this;
    }
    public Matrix addCol(double val){
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        Matrix var1 = new Matrix(m, n + 1);
        var1.setMatrix(0, m - 1, 0, n - 1, this);
        double[][] var2 = var1.getArray();
        for (int i = 0; i < m; i++) {
            var2[i][n] = val;
        }
        return var1;
    }

    public Matrix addRow(double val) {
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        Matrix var1 = new Matrix(m + 1, n);
        var1.setMatrix(0, m - 1, 0, n - 1, this);
        double[][] var2 = var1.getArray();
        for (int i = 0; i < n; i++) {
            var2[m][i] = val;
        }
        return var1;
    }

    public Matrix setCol(double[] arr, int col){
        int m = this.getRowDimension();
        assert (m == arr.length && col < this.getColumnDimension());
        double[][] var1 = this.getArray();
        for (int i = 0; i < m; i++) {
            var1[i][col] = arr[i];
        }
        return this;
    }

    public Matrix setCol(double val, int col){
        int m = this.getRowDimension();
        assert col < this.getColumnDimension();
        double[][] var1 = this.getArray();
        for (int i = 0; i < m; i++) {
            var1[i][col] = val;
        }
        return this;
    }

    public Matrix setMatrix(double[] arr, int dim){
        assert (dim == 0 || dim == 1);
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        double[][] var1 = this.getArray();
        int k = arr.length;
        if(dim == 0) {
            assert m == k;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    var1[i][j] = arr[i];
                }
            }
        }
        else if(dim == 1){
            assert n == k;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    var1[i][j] = arr[j];
                }
            }
        }
        return this;
    }

    public Matrix maxM(double val) {
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        Matrix var1 = new Matrix(m, n);
        double[][] var2 = this.getArray();
        double[][] var3 = var1.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(var2[i][j] > val){
                    var3[i][j] = var2[i][j];
                }
                else{
                    var3[i][j] = val;
                }
            }
        }
        return var1;
    }

    public double sum() {
        double[][] var1 = this.getArray();
        double sum = 0;
        for (int i = 0; i < var1.length; i++) {
            for (int j = 0; j < var1[0].length; j++) {
                sum += var1[i][j];
            }
        }
        return sum;
    }

    public double[] sum(int dim) {
        assert (dim == 0 || dim == 1);
        int m = this.getRowDimension();
        int n = this.getColumnDimension();
        double[][] var1 = this.getArray();
        double[] arr = null;
        if(dim == 0){
            arr = new double[m];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    arr[i] += var1[i][j];
                }
            }
        }
        else if(dim == 1){
            arr = new double[n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    arr[j] += var1[i][j];
                }
            }
        }
        return arr;
    }

    public double[] mean(int dim) {
        assert (dim == 0 || dim == 1);
        int m = this.getColumnDimension();
        int n = this.getColumnDimension();
        //double[][] var1 = this.getArray();
        double[] arr = null;
        if(dim == 0) {
            arr = this.sum(0);
        }
        else if(dim == 1) {
            arr = this.sum(1);
        }
        int l = arr.length;
        for (int i = 0; i < l; i++) {
            arr[i] /= l;
        }
        return arr;
    }


}
