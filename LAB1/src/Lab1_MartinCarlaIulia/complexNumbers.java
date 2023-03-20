package Lab1_MartinCarlaIulia;

import java.util.Scanner;

public class complexNumbers{

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("Enter the real of the first number");
        int re1 = read.nextInt();

        System.out.println("Enter the imaginary of the first number");
        int im1 = read.nextInt();
        System.out.println("Enter the real of the second number");
        int re2 = read.nextInt();

        System.out.println("Enter the imaginary of the second number");
        int im2 = read.nextInt();

        int imSum = im1 +im2;
        int reSum =re1 + re2;

        int imProd = im1 * im2;
        int reProd = re1 * re2;
        System.out.println("The sum is " +imSum + "i+" +reSum);
        System.out.println("The product is " +(re1 * re2 - (im1 * im2) ) +  "+" +((re1 * im2) + (im1 * re2))+ "i" );


    }
}
