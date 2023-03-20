package Lab1_MartinCarlaIulia;

public class Arrays {
    public static void Multiply(int[][] M1, int[][] M2){
        int prod[][] = new int[3][3];
        for ( int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                prod[i][j] = 0;

                for (int k = 0; k < 3; k++)
                    prod[i][j]  += M1[i][k] * M2[k][j];
                System.out.print(prod[i][j]+ " ");
            }
            System.out.println();
        }
    }

    public static void Sum(int[][] M1, int[][] M2) {
        int sum[][] = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                sum[i][j] = 0;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum[i][j] = M1[i][j] + M2[i][j];
                System.out.print(sum[i][j] + " ");
            }
            System.out.println(" ");
        }

    }
    public static  void main(String[] args) {
        int[] R1 = {2, 3, 1};
        int[] R2 = {7, 1, 6};
        int[] R3 = {9, 2, 4};

        int[] F1 = {8, 5, 3};
        int[] F2 = {3, 9, 2};
        int[] F3 = {2, 7, 3};

        int[][] M1 = {R1, R2, R3};
        int[][] M2 = {F1, F2, F3};

        System.out.println("The sum is ");

        Sum(M1, M2);

        System.out.println("The product is: ");


        Multiply(M1, M2);


    }
}