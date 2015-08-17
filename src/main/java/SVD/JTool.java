package SVD;

/**
 * Created by jingle on 2015/8/16.
 */
public class JTool {
    public static float hypot(float var0, float var2) {
        float var4;
        if(Math.abs(var0) > Math.abs(var2)) {
            var4 = var2 / var0;
            var4 = Math.abs(var0) * (float)Math.sqrt(1 + var4 * var4);
        } else if(var2 != 0) {
            var4 = var0 / var2;
            var4 = Math.abs(var2) * (float)Math.sqrt(1 + var4 * var4);
        } else {
            var4 = 0;
        }

        return var4;
    }
}
