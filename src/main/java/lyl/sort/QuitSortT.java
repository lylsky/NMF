package lyl.sort;

/**
 * Created by lyl on 2015/8/16.
 */
public class QuitSortT<T extends Comparable<T>> {
    private static double Pmin = 0.5;
    private static double Pmax = 0.8;
    private T min;
    private T max;
    public int Min_partiion(T[] input,int low,int high){
        T a = input[low];

        while(low < high){
            while(low<high && input[high].compareTo(a) >= 0) high--;
            input[low] = input[high];
            while (low <high && input[low].compareTo(a) <=0 ) low++;
            input[high] = input[low];
        }
        input[low] = a;
        return low;
    }

    public int Top_partiion(T[] input,int low,int high){
        T a = input[low];

        while(low < high){
            while(low<high && input[high].compareTo(a) <= 0) high--;
            input[low] = input[high];
            while (low <high && input[low].compareTo(a) >=0 ) low++;
            input[high] = input[low];
        }
        input[low] = a;
        return low;
    }

    public T TopK(T[] input ,int high){
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
    public T MinK(T[] input,int low){
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

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public  void MaxMin(T[] input){
        int len = input.length;
        int high =len - (int) (len *Pmax);

        max = TopK(input,high);

        int low = (int) (len *Pmin);
        min = MinK(input,low-1);
    }
    public static void main(String[] args){
        Integer[] input={20,34,4,142,124,12};


        QuitSortT<Integer> quitSort = new QuitSortT();
        long t1 =System.currentTimeMillis();
        quitSort.MaxMin(input);


        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        System.out.println(quitSort.getMax());
        System.out.println(quitSort.getMin());
    }

}
