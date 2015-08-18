package lyl.sort;

/**
 * Created by lyl on 2015/8/16.
 */
public class QuitSort {
    private static double Pmin = 0.5;
    private static double Pmax = 0.8;
    private int min;
    private int max;
    public int Min_partiion(double[] input,int low,int high){
        double a = input[low];

        while(low < high){
            while(low<high && input[high] >=a) high--;
            input[low] = input[high];
            while (low <high && input[low] <= a ) low++;
            input[high] = input[low];
        }
        input[low] = a;
        return low;
    }

    public int Top_partiion(double[] input,int low,int high){
        double a = input[low];

        while(low < high){
            while(low<high && input[high]<= a) high--;
            input[low] = input[high];
            while (low <high && input[low] >= a) low++;
            input[high] = input[low];
        }
        input[low] = a;
        return low;
    }

    public double TopK(double[] input ,int high){
        int start =0;
        int end = input.length-1;
        int index = Top_partiion(input, start, end);
        while(index != high){
            if(index > high){
                end = index - 1;
                index = Top_partiion(input, start, end);
            }else {
                start = index +1;
                index = Top_partiion(input, start, end);
            }
        }
        return  input[high];
    }
    public double MinK(double[] input,int low){
        int start =0;
        int end = input.length-1;
        int index = Min_partiion(input, start, end);
        while(index != low){
            if(index > low){
                end = index - 1;
                index = Min_partiion(input, start, end);
            }else {
                start = index +1;
                index = Min_partiion(input, start, end);
            }
        }
        return  input[low];

    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }



}
