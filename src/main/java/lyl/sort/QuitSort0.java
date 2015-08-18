package lyl.sort;

/**
 * Created by lyl on 2015/8/16.
 */
/**
 * 备用
 */
public class QuitSort0 {
    private static double Pmin = 0.5;
    private static double Pmax = 0.8;
    private int min;
    private int max;
    public int Min_partiion(int[] input,int low,int high){
        int a = input[low];

        while(low < high){
            while(low<high && input[high] >=a) high--;
            input[low] = input[high];
            while (low <high && input[low] <= a ) low++;
            input[high] = input[low];
        }
        input[low] = a;
        return low;
    }

    public int Top_partiion(int[] input,int low,int high){
        int a = input[low];

        while(low < high){
            while(low<high && input[high]<= a) high--;
            input[low] = input[high];
            while (low <high && input[low] >= a) low++;
            input[high] = input[low];
        }
        input[low] = a;
        return low;
    }

    public int TopK(int[] input ,int high){
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
    public int MinK(int[] input,int low){
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

    public  void MaxMin(int[] input){
        int len = input.length;
        int high =len - (int) (len *Pmax);

        max = TopK(input,high);

        int low = (int) (len *Pmin);
        min = MinK(input,low-1);
    }
    public static void main(String[] args){
        int[] input={20,34,4,142,124,12};


        QuitSort0 quitSort = new QuitSort0();
        long t1 =System.currentTimeMillis();
        quitSort.MaxMin(input);


        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        System.out.println(quitSort.getMax());
        System.out.println(quitSort.getMin());
    }

}
