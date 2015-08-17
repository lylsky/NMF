package SVD;

/**
 * Create by jingle on 2015/8/16.
 */

import java.io.Serializable;

public class SingularValueDecomposition implements Serializable {
    private double[][] U;
    private double[][] V;
    private double[] s;
    private int m;
    private int n;
    private static final long serialVersionUI = 1L;

    public SingularValueDecomposition(double[][] var2,int n) {
        //double[][] var2 = var1.getArrayCopy();
        this.m = var2.length;
//        this.n = var2[0].length;
        this.n = n;
        int var3 = Math.min(this.m, this.n);
        this.s = new double[Math.min(this.m + 1, this.n)];
        this.U = new double[this.m][var3];
        this.V = new double[this.n][this.n];
        double[] var4 = new double[this.n];
        double[] var5 = new double[this.m];
        boolean var6 = true;
        boolean var7 = true;
        int var8 = Math.min(this.m - 1, this.n);
        int var9 = Math.max(0, Math.min(this.n - 2, this.m));

        int var10;
        int var11;
        int var49;
        for(var10 = 0; var10 < Math.max(var8, var9); ++var10) {
            if(var10 < var8) {
                this.s[var10] = 0;

                for(var11 = var10; var11 < this.m; ++var11) {
                    this.s[var10] = JTool.hypot(this.s[var10], var2[var11][var10]);
                }

                if(this.s[var10] != 0) {
                    if(var2[var10][var10] < 0) {
                        this.s[var10] = -this.s[var10];
                    }

                    for(var11 = var10; var11 < this.m; ++var11) {
                        var2[var11][var10] /= this.s[var10];
                    }

                    ++var2[var10][var10];
                }

                this.s[var10] = -this.s[var10];
            }

            double var12;
            int var14;
            for(var11 = var10 + 1; var11 < this.n; ++var11) {
                if(var10 < var8 & this.s[var10] != 0) {
                    var12 = 0;

                    for(var14 = var10; var14 < this.m; ++var14) {
                        var12 += var2[var14][var10] * var2[var14][var11];
                    }

                    var12 = -var12 / var2[var10][var10];

                    for(var14 = var10; var14 < this.m; ++var14) {
                        var2[var14][var11] += var12 * var2[var14][var10];
                    }
                }

                var4[var11] = var2[var10][var11];
            }

            if(var6 & var10 < var8) {
                for(var11 = var10; var11 < this.m; ++var11) {
                    this.U[var11][var10] = var2[var11][var10];
                }
            }

            if(var10 < var9) {
                var4[var10] = 0;

                for(var11 = var10 + 1; var11 < this.n; ++var11) {
                    var4[var10] = JTool.hypot(var4[var10], var4[var11]);
                }

                if(var4[var10] != 0) {
                    if(var4[var10 + 1] < 0) {
                        var4[var10] = -var4[var10];
                    }

                    for(var11 = var10 + 1; var11 < this.n; ++var11) {
                        var4[var11] /= var4[var10];
                    }

                    ++var4[var10 + 1];
                }

                var4[var10] = -var4[var10];
                if(var10 + 1 < this.m & var4[var10] != 0) {
                    for(var11 = var10 + 1; var11 < this.m; ++var11) {
                        var5[var11] = 0;
                    }

                    for(var11 = var10 + 1; var11 < this.n; ++var11) {
                        for(var49 = var10 + 1; var49 < this.m; ++var49) {
                            var5[var49] += var4[var11] * var2[var49][var11];
                        }
                    }

                    for(var11 = var10 + 1; var11 < this.n; ++var11) {
                        var12 = -var4[var11] / var4[var10 + 1];

                        for(var14 = var10 + 1; var14 < this.m; ++var14) {
                            var2[var14][var11] += var12 * var5[var14];
                        }
                    }
                }

                if(var7) {
                    for(var11 = var10 + 1; var11 < this.n; ++var11) {
                        this.V[var11][var10] = var4[var11];
                    }
                }
            }
        }

        var10 = Math.min(this.n, this.m + 1);
        if(var8 < this.n) {
            this.s[var8] = var2[var8][var8];
        }

        if(this.m < var10) {
            this.s[var10 - 1] = 0;
        }

        if(var9 + 1 < var10) {
            var4[var9] = var2[var9][var10 - 1];
        }

        var4[var10 - 1] = 0;
        double var13;
        int var15;
        if(var6) {
            for(var11 = var8; var11 < var3; ++var11) {
                for(var49 = 0; var49 < this.m; ++var49) {
                    this.U[var49][var11] = 0;
                }

                this.U[var11][var11] = 1;
            }

            for(var11 = var8 - 1; var11 >= 0; --var11) {
                if(this.s[var11] != 0) {
                    for(var49 = var11 + 1; var49 < var3; ++var49) {
                        var13 = 0;

                        for(var15 = var11; var15 < this.m; ++var15) {
                            var13 += this.U[var15][var11] * this.U[var15][var49];
                        }

                        var13 = -var13 / this.U[var11][var11];

                        for(var15 = var11; var15 < this.m; ++var15) {
                            this.U[var15][var49] += var13 * this.U[var15][var11];
                        }
                    }

                    for(var49 = var11; var49 < this.m; ++var49) {
                        this.U[var49][var11] = -this.U[var49][var11];
                    }

                    ++this.U[var11][var11];

                    for(var49 = 0; var49 < var11 - 1; ++var49) {
                        this.U[var49][var11] = 0;
                    }
                } else {
                    for(var49 = 0; var49 < this.m; ++var49) {
                        this.U[var49][var11] = 0;
                    }

                    this.U[var11][var11] = 1;
                }
            }
        }

        if(var7) {
            for(var11 = this.n - 1; var11 >= 0; --var11) {
                if(var11 < var9 & var4[var11] != 0) {
                    for(var49 = var11 + 1; var49 < var3; ++var49) {
                        var13 = 0;

                        for(var15 = var11 + 1; var15 < this.n; ++var15) {
                            var13 += this.V[var15][var11] * this.V[var15][var49];
                        }

                        var13 = -var13 / this.V[var11 + 1][var11];

                        for(var15 = var11 + 1; var15 < this.n; ++var15) {
                            this.V[var15][var49] += var13 * this.V[var15][var11];
                        }
                    }
                }

                for(var49 = 0; var49 < this.n; ++var49) {
                    this.V[var49][var11] = 0;
                }

                this.V[var11][var11] = 1;
            }
        }

        var11 = var10 - 1;
        var49 = 0;
        var13 = (double)Math.pow(2, -52);
        double var50 = (double)Math.pow(2, -966);

        while(true) {
            label349:
            while(var10 > 0) {
                int var17;
                for(var17 = var10 - 2; var17 >= -1 && var17 != -1; --var17) {
                    if(Math.abs(var4[var17]) <= var50 + var13 * (Math.abs(this.s[var17]) + Math.abs(this.s[var17 + 1]))) {
                        var4[var17] = 0;
                        break;
                    }
                }

                byte var18;
                int var19;
                if(var17 == var10 - 2) {
                    var18 = 4;
                } else {
                    for(var19 = var10 - 1; var19 >= var17 && var19 != var17; --var19) {
                        double var20 = (var19 != var10?Math.abs(var4[var19]):0) + (var19 != var17 + 1?Math.abs(var4[var19 - 1]):0);
                        if(Math.abs(this.s[var19]) <= var50 + var13 * var20) {
                            this.s[var19] = 0;
                            break;
                        }
                    }

                    if(var19 == var17) {
                        var18 = 3;
                    } else if(var19 == var10 - 1) {
                        var18 = 1;
                    } else {
                        var18 = 2;
                        var17 = var19;
                    }
                }

                ++var17;
                int var21;
                double var22;
                double var24;
                double var26;
                int var28;
                double var51;
                switch(var18) {
                    case 1:
                        var51 = var4[var10 - 2];
                        var4[var10 - 2] = 0;
                        var21 = var10 - 2;

                        for(; var21 >= var17; --var21) {
                            var22 = JTool.hypot(this.s[var21], var51);
                            var24 = this.s[var21] / var22;
                            var26 = var51 / var22;
                            this.s[var21] = var22;
                            if(var21 != var17) {
                                var51 = -var26 * var4[var21 - 1];
                                var4[var21 - 1] = var24 * var4[var21 - 1];
                            }

                            if(var7) {
                                for(var28 = 0; var28 < this.n; ++var28) {
                                    var22 = var24 * this.V[var28][var21] + var26 * this.V[var28][var10 - 1];
                                    this.V[var28][var10 - 1] = -var26 * this.V[var28][var21] + var24 * this.V[var28][var10 - 1];
                                    this.V[var28][var21] = var22;
                                }
                            }
                        }
                        break;
                    case 2:
                        var51 = var4[var17 - 1];
                        var4[var17 - 1] = 0;
                        var21 = var17;

                        while(true) {
                            if(var21 >= var10) {
                                continue label349;
                            }

                            var22 = JTool.hypot(this.s[var21], var51);
                            var24 = this.s[var21] / var22;
                            var26 = var51 / var22;
                            this.s[var21] = var22;
                            var51 = -var26 * var4[var21];
                            var4[var21] = var24 * var4[var21];
                            if(var6) {
                                for(var28 = 0; var28 < this.m; ++var28) {
                                    var22 = var24 * this.U[var28][var21] + var26 * this.U[var28][var17 - 1];
                                    this.U[var28][var17 - 1] = -var26 * this.U[var28][var21] + var24 * this.U[var28][var17 - 1];
                                    this.U[var28][var21] = var22;
                                }
                            }

                            ++var21;
                        }
                    case 3:
                        var51 = Math.max(Math.max(Math.max(Math.max(Math.abs(this.s[var10 - 1]), Math.abs(this.s[var10 - 2])), Math.abs(var4[var10 - 2])), Math.abs(this.s[var17])), Math.abs(var4[var17]));
                        double var52 = this.s[var10 - 1] / var51;
                        double var23 = this.s[var10 - 2] / var51;
                        double var25 = var4[var10 - 2] / var51;
                        double var27 = this.s[var17] / var51;
                        double var29 = var4[var17] / var51;
                        double var31 = ((var23 + var52) * (var23 - var52) + var25 * var25) / 2;
                        double var33 = var52 * var25 * var52 * var25;
                        double var35 = 0;
                        if(var31 != 0 | var33 != 0) {
                            var35 = (double)Math.sqrt(var31 * var31 + var33);
                            if(var31 < 0) {
                                var35 = -var35;
                            }

                            var35 = var33 / (var31 + var35);
                        }

                        double var37 = (var27 + var52) * (var27 - var52) + var35;
                        double var39 = var27 * var29;

                        for(int var41 = var17; var41 < var10 - 1; ++var41) {
                            double var42 = JTool.hypot(var37, var39);
                            double var44 = var37 / var42;
                            double var46 = var39 / var42;
                            if(var41 != var17) {
                                var4[var41 - 1] = var42;
                            }

                            var37 = var44 * this.s[var41] + var46 * var4[var41];
                            var4[var41] = var44 * var4[var41] - var46 * this.s[var41];
                            var39 = var46 * this.s[var41 + 1];
                            this.s[var41 + 1] = var44 * this.s[var41 + 1];
                            int var48;
                            if(var7) {
                                for(var48 = 0; var48 < this.n; ++var48) {
                                    var42 = var44 * this.V[var48][var41] + var46 * this.V[var48][var41 + 1];
                                    this.V[var48][var41 + 1] = -var46 * this.V[var48][var41] + var44 * this.V[var48][var41 + 1];
                                    this.V[var48][var41] = var42;
                                }
                            }

                            var42 = JTool.hypot(var37, var39);
                            var44 = var37 / var42;
                            var46 = var39 / var42;
                            this.s[var41] = var42;
                            var37 = var44 * var4[var41] + var46 * this.s[var41 + 1];
                            this.s[var41 + 1] = -var46 * var4[var41] + var44 * this.s[var41 + 1];
                            var39 = var46 * var4[var41 + 1];
                            var4[var41 + 1] = var44 * var4[var41 + 1];
                            if(var6 && var41 < this.m - 1) {
                                for(var48 = 0; var48 < this.m; ++var48) {
                                    var42 = var44 * this.U[var48][var41] + var46 * this.U[var48][var41 + 1];
                                    this.U[var48][var41 + 1] = -var46 * this.U[var48][var41] + var44 * this.U[var48][var41 + 1];
                                    this.U[var48][var41] = var42;
                                }
                            }
                        }

                        var4[var10 - 2] = var37;
                        ++var49;
                        break;
                    case 4:
                        if(this.s[var17] <= 0) {
                            this.s[var17] = this.s[var17] < 0?-this.s[var17]:0;
                            if(var7) {
                                for(var19 = 0; var19 <= var11; ++var19) {
                                    this.V[var19][var17] = -this.V[var19][var17];
                                }
                            }
                        }

                        for(; var17 < var11 && this.s[var17] < this.s[var17 + 1]; ++var17) {
                            var51 = this.s[var17];
                            this.s[var17] = this.s[var17 + 1];
                            this.s[var17 + 1] = var51;
                            if(var7 && var17 < this.n - 1) {
                                for(var21 = 0; var21 < this.n; ++var21) {
                                    var51 = this.V[var21][var17 + 1];
                                    this.V[var21][var17 + 1] = this.V[var21][var17];
                                    this.V[var21][var17] = var51;
                                }
                            }

                            if(var6 && var17 < this.m - 1) {
                                for(var21 = 0; var21 < this.m; ++var21) {
                                    var51 = this.U[var21][var17 + 1];
                                    this.U[var21][var17 + 1] = this.U[var21][var17];
                                    this.U[var21][var17] = var51;
                                }
                            }
                        }

                        var49 = 0;
                        --var10;
                }
            }

            return;
        }
    }

    public double[][] getU() {
        return U;
    }

    public double[][] getU(int var1){
        double[][] U1 = new double[this.m][var1];
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < var1; j++) {
                U1[i][j] = U[i][j];
            }
        }
        return U1;
    }

    public double[][] getV() {
        return V;
    }

    public double[][] getV(int var1){
        double[][] V1 = new double[this.n][var1];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < var1; j++) {
                V1[i][j] = V[i][j];
            }
        }
        return V1;
    }

    public double[] getSingularValues() {
        return this.s;
    }

    public double[][] getS() {
        //Matrix var1 = new Matrix(this.n, this.n);
        double[][] var2 = new double[this.n][this.n];

        for(int var3 = 0; var3 < this.n; ++var3) {
            for(int var4 = 0; var4 < this.n; ++var4) {
                var2[var3][var4] = 0;
            }

            var2[var3][var3] = this.s[var3];
        }

        return var2;
    }

    public double[][] getS(int var1) {
        //Matrix var1 = new Matrix(this.n, this.n);
        double[][] var2 = new double[var1][var1];

        for(int var3 = 0; var3 < var1; ++var3) {
            for(int var4 = 0; var4 < var1; ++var4) {
                var2[var3][var4] = 0;
            }

            var2[var3][var3] = this.s[var3];
        }

        return var2;
    }

    public double norm2() {
        return this.s[0];
    }

    public double con() {
        return this.s[0] / this.s[Math.min(this.m, this.n) - 1];
    }

    public int rank() {
        double var1 = (double)Math.pow(2, -52);
        double var3 = (double)Math.max(this.m, this.n) * this.s[0] * var1;
        int var5 = 0;

        for(int var6 = 0; var6 < this.s.length; ++var6) {
            if(this.s[var6] > var3) {
                ++var5;
            }
        }

        return var5;
    }
}
