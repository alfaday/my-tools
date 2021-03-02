package fund;

public class FundTest {

    static int times = 3;
    static int initPrice = 100;
    static double inputOnce = 100;
    static int stepSize = 10;

    public static void main(String[] args) {
        mode1();
        System.out.println("================");
        mode2();

    }

    //左侧加码
    public static void mode1(){

        double sumShares = 0;
        for(int i = 0; i <= times; i++){
            sumShares = sumShares + (inputOnce/(initPrice - i*stepSize));
            if(i == times){
                System.out.println("lowest price="+(initPrice - i*stepSize));
            }
        }

        double sumInvest = inputOnce*(times + 1);
        double ratio = (sumShares*initPrice - sumInvest)/sumInvest;
        System.out.println(ratio);
    }

    //左右侧同时加码
    public static void mode2(){

        double sumShares = 0;
        double lowest = 0;
        for(int i = 0; i <= times; i++){
            double onceShare = (inputOnce/(initPrice - i*stepSize));
            sumShares = sumShares + onceShare;
            if(i == times){
                lowest = onceShare;
                System.out.println("lowest price="+(initPrice - i*stepSize));
            }
        }
        sumShares = sumShares*2 - lowest;

        double sumInvest = inputOnce*(times*2 + 1);
        double ratio = (sumShares*initPrice - sumInvest)/sumInvest;
        System.out.println(ratio);
    }

    public static void mode3(){
        int totalInvest = 1000;

    }
}
