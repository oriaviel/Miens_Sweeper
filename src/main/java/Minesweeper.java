
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author admin
 */
public class Minesweeper {

    private static char[][] Bord(int size) {
        char[][] arr1 = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr1[i][j] = '*';
            }
        }
        return arr1;
    }

    private static int[][] Unseebord(int size, int mine) {
        int[][] arr2 = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr2[i][j] = 0;
            }
        }
        for (int i = 0; i < mine; i++) {
            int M = (int) (Math.random() * size);
            int N = (int) (Math.random() * size);
            if (arr2[M][N] == 9) {
                mine += 1;
            }
            arr2[M][N] = 9;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr2[i][j] == 9) {
                    for (int r = i - 1; r <= i + 1; r++) {
                        for (int c = j - 1; c <= j + 1; c++) {
                            if (r < 0 || r >= size || c < 0 || c >= size) {
                                continue;
                            }
                            if (arr2[r][c] != 9) {
                                arr2[r][c]++;
                            }
                        }
                    }
                }
            }
        }
        return arr2;
    }

    private static void Pbord(int size, char arr1[][]) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(arr1[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean Move(boolean cont, int arr2[][], char arr1[][], int size,
            int r, int c) {
        int i, j;

        if (arr2[r][c] == 9) {
            System.out.println("Game over");
            for (i = 0; i < size; i++) {
                for (j = 0; j < size; j++) {
                    if (arr2[i][j] == 9) {
                        arr1[i][j] = 'M';
                    }
                }
            }
            Pbord(size, arr1);
            cont = false;
        } else if (arr2[r][c] > 0) {
            arr1[r][c] = (char) (arr2[r][c] + 48);
            Pbord(size, arr1);
        } else {
            for (i = r - 1; i <= r + 1; i++) {
                for (j = c - 1; j <= c + 1; j++) {
                    if (i < 0 || i >= size || j < 0 || j >= size) {
                        continue;
                    }
                    arr1[i][j] = (char) (arr2[i][j] + 48);
                    if (arr2[i][j] == 0) {
                        zero(arr2, arr1, size, i, j);
                    }
                }
            }
            Pbord(size, arr1);
        }

        return cont;
    }

    private static void zero(int arr2[][], char arr1[][], int size, int r, int c) {
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i < 0 || i >= size || j < 0 || j >= size) {
                    continue;
                }
                if (arr1[i][j] != '*') {
                    continue;
                }
                arr1[i][j] = (char) (arr2[i][j] + 48);
                if (arr1[i][j] == '0') {
                    zero(arr2, arr1, size, i, j);
                }

            }
        }
    }

    private static boolean Winning(boolean cont, char arr1[][], int size, int mine) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr1[i][j] != '*') {
                    count++;
                }
            }
        }
        if (count == size * size - mine) {
            System.out.println("You win");
            cont = false;
        }
        return cont;
    }

    public static void main(String[] args) {
        int size, mine;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size");
        size = sc.nextInt();
        System.out.println("Enter the mine");
        mine = sc.nextInt();
        Bord(size);

        char arr1[][] = new char[size][size];
        int arr2[][] = new int[size][size];
        arr1 = Bord(size);
        Pbord(size, arr1);
        arr2 = Unseebord(size, mine);

        boolean cont = true;

        while (cont) {
            int r = -1, c = -1;

            do {
                r = -1;
                c = -1;
                System.out.println("Enter row");
                while (r < 0 || r >= size) {
                    r = sc.nextInt() - 1;
                }

                System.out.println("Enter column");
                while (c < 0 || c >= size) {
                    c = sc.nextInt() - 1;
                }
            } while (arr1[r][c] != '*');
            cont = Move(cont, arr2, arr1, size, r, c);
            cont = Winning(cont, arr1, size, mine);
        }
    }
}
