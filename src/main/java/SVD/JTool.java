package SVD;

/**
 * Created by jingle on 2015/8/16.
 */
public class JTool {
    /*
   数组转置
    */
    public static double[][] tanspose(double[][] a){
        int c=a[0].length;
        int r=a.length;
        double[][] b=new double[c][r];

        for(int i=0;i<r;i++)
            for(int j=0;j<c;j++)
                b[j][i]=a[i][j];
        return b;
    }
    /*
    矩阵相乘
     */
    public static double[][] MultiM(double[][] a,double[][] b){
        int rc=b.length;

        if(rc != a[0].length)
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        int ar=a.length;
        int bc=b[0].length;
        double[][] r=new double[ar][bc];

        double[] bj=new double[rc];

        for(int j=0;j<bc;j++){
            for(int k=0;k<rc;k++)
                bj[k]=b[k][j];

            for(int i=0;i<ar;i++){
                double s=0;
                for(int k=0;k<rc;k++)
                    s=s+a[i][k]*bj[k];
                r[i][j]=s;
            }
        }
        return  r;
    }
    /*
    short 数组转换为double数组
     */
    public static  double[][] StoD(short[][] a){
        int  n=a.length;
        int m=a[0].length;
        double[][] ud=new double[n][m];
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                ud[i][j]=a[i][j];
        return ud;
    }
    /*
    float 数组转换为double数组
     */
    public static  double[][] ftoD(float[][] a){
        int  n=a.length;
        int m=a[0].length;
        double[][] ud=new double[n][m];
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++)
                ud[i][j]=a[i][j];
        return ud;
    }
    public static short[][] BtoS_Bil(byte[] data,int pixel,int col,int bands){

        int row = pixel/col;

        short[][] sdata=new short[pixel][bands];

        int n=0;
        for(int i=0;i<row;i++){
            int p=i*col;
            for(int j=0;j<bands;j++)
                for(int k=0;k<col;k++){
                    sdata[p+k][j]=(short)((data[n++]&0xff) | (data[n++] <<8));
                }
        }
        return sdata;
    }
    public static short[][] BtoS_Bip(byte[] data,int pixel,int bands){

        short[][] sdata=new short[pixel][bands];

        int n=0;
        for(int i=0;i<pixel;i++){
            for(int j=0;j<bands;j++) {
                sdata[i][j]=(short)((data[n++]&0xff) | (data[n++] <<8));
            }
        }
        return sdata;
    }
    public static double hypot(double var0, double var2) {
        double var4;
        if(Math.abs(var0) > Math.abs(var2)) {
            var4 = var2 / var0;
            var4 = Math.abs(var0) * (double)Math.sqrt(1 + var4 * var4);
        } else if(var2 != 0) {
            var4 = var0 / var2;
            var4 = Math.abs(var2) * Math.sqrt(1 + var4 * var4);
        } else {
            var4 = 0;
        }

        return var4;
    }
}
