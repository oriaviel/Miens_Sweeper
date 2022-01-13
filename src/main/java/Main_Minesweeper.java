
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author Ori Aviel
 */
public class Main_Minesweeper {

    /**
     * The function creat the game board with the sizes the user gave.
     * @param rowsNumber - The number of rows for the board game.
     * @param columnsNumber - The number of columns for the board game.
     * @return - return the board that the users see.
     */
    private static char[][] Board(int rowsNumber,int columnsNumber) {
        char[][] arr1 = new char[rowsNumber][columnsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                arr1[i][j] = '*';
            }
        }
        return arr1;
    }
 /**
  * The function creat the "unsee board".
  * puts randomly the mines and count how many mine near all squre.
  * @param rowsNumber - The number of rows for the board game.
  * @param columnsNumber - The number of columns for the board game.
  * @param mine - The number of mines for the board game.
  * @return - the board with mines and numbers.
  */
    private static int[][] Unseeboard(int rowsNumber,int columnsNumber, int mine) {
        //creat array in size of the bord. 
        int[][] arr2 = new int[rowsNumber][columnsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                arr2[i][j] = 0;
            }
        }
        //puts in random mines, mines = 9.
        for (int i = 0; i < mine; i++) {
            int M = (int) (Math.random() * rowsNumber);
            int N = (int) (Math.random() * columnsNumber);
            if (arr2[M][N] == 9) {
                mine += 1;
            }
            arr2[M][N] = 9;
        }
        //count the number of mines next any squre.
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                if (arr2[i][j] == 9) {
                    for (int r = i - 1; r <= i + 1; r++) {
                        for (int c = j - 1; c <= j + 1; c++) {
                            if (r < 0 || r >= rowsNumber || c < 0 || c >= columnsNumber) {
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
/**
 * The function print the board for the user.
 * @param rowsNumber - The number of rows for the board game.
 * @param columnsNumber - The number of columns for the board game.
 * @param arr1 - The baord that the users see.
 */
    private static void Pbord(int rowsNumber, int columnsNumber, char arr1[][]) {
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                System.out.print(arr1[i][j] + " ");
            }
            System.out.println();
        }
    }
/**
 * The function do all the options that posibaley can happend in the game
 * like: click on mine, click on numbers, click on zero.
 * @param cont - while cont is true the game continue. (cont short of continue) 
 * @param arr2 - the "unsee" board.
 * @param arr1 - game board.
 * @param rowsNumber - The number of rows for the board game.
 * @param columnsNumber - The number of columns for the board game.
 * @param row - The number of rows the users give.
 * @param column - The number of columns the users give.
 * @return cont.
 */
    private static boolean Move(boolean cont, int arr2[][], char arr1[][], int rowsNumber,
            int columnsNumber,int row, int column) {
        
        int i, j;
        // cheaking if the users click on mine 
        if (arr2[row][column] == 9) {
            System.out.println("Game over");
            for (i = 0; i < rowsNumber; i++) {
                for (j = 0; j < columnsNumber; j++) {
                    if (arr2[i][j] == 9) {
                        arr1[i][j] = 'M';
                    }
                }
            }
            Pbord(rowsNumber,columnsNumber, arr1);
            cont = false;
        } else if (arr2[row][column] > 0) { //open the squre.
            arr1[row][column] = (char) (arr2[row][column] + 48);
            Pbord(rowsNumber,columnsNumber ,arr1);
        } else { //open empty squres.
            for (i = row - 1; i <= row + 1; i++) {
                for (j = column - 1; j <= column + 1; j++) {
                    if (i < 0 || i >= rowsNumber || j < 0 || j >= columnsNumber) {
                        continue;
                    }
                    arr1[i][j] = (char) (arr2[i][j] + 48);
                    if (arr2[i][j] == 0) {
                        zero(arr2, arr1, rowsNumber, columnsNumber, i, j);
                    }
                }
            }
            Pbord(rowsNumber,columnsNumber, arr1);
        }

        return cont;
    }
/**
 * The function do recorcy and open all the empty squres.    
 * @param arr2 - the "unsee" board.
 * @param arr1 - game board.
 * @param rowsNumbers - The number of rows for the board game.
 * @param columsNnumber - The number of columns for the board game.
 * @param row - The number of rows the users give.
 * @param column - The number of columns the users give.
 * 
 */
    private static void zero(int arr2[][], char arr1[][], int rowsNumbers,int columsNnumber,
            int row, int column) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i < 0 || i >= rowsNumbers || j < 0 || j >= columsNnumber) {
                    continue;
                }
                if (arr1[i][j] != '*') {
                    continue;
                }
                arr1[i][j] = (char) (arr2[i][j] + 48);
                if (arr1[i][j] == '0') {
                    zero(arr2, arr1, rowsNumbers,columsNnumber, i, j);
                }

            }
        }
    }
/**
 * The function checking if the user win
 * @param cont - while cont is true the game continue. (cont short of continue) 
 * @param arr1 - game board.
 * @param rowsNumber - The number of rows for the board game.
 * @param columnsNumber - The number of columns for the board game.
 * @param mine - The number of mines.
 * @return cont
 * 
 */
    private static boolean Winning(boolean cont, char arr1[][], int rowsNumber,
            int columnsNumber, int mine) {
        int count = 0;
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                if (arr1[i][j] != '*') {
                    count++;
                }
            }
        }
        if (count == rowsNumber * columnsNumber - mine) {
            System.out.println("You win");
            cont = false;
        }
        return cont;
    }

    public static void main(String[] args) {
        int rowsNumber,columnsNumber, mine;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of rows for the board");
        rowsNumber = sc.nextInt();
        System.out.println("Enter the number of columns for the board");
        columnsNumber = sc.nextInt();
        System.out.println("Enter the mine");
        mine = sc.nextInt();
        Board(rowsNumber, columnsNumber);

        char arr1[][] = new char[rowsNumber][columnsNumber];
        int arr2[][] = new int[rowsNumber][columnsNumber];
        arr1 = Board(rowsNumber, columnsNumber);
        Pbord(rowsNumber, columnsNumber, arr1);
        arr2 = Unseeboard(rowsNumber, columnsNumber, mine);

        boolean cont = true;

        while (cont) {
            int row = -1, column = -1;

            do {
                row = -1;
                column = -1;
                System.out.println("Enter row");
                while (row < 0 || row >= rowsNumber) {
                    row = sc.nextInt() - 1;
                }

                System.out.println("Enter column");
                while (column < 0 || column >= columnsNumber) {
                    column = sc.nextInt() - 1;
                }
            } while (arr1[row][column] != '*');
            cont = Move(cont, arr2, arr1, rowsNumber, columnsNumber, row, column);
            cont = Winning(cont, arr1, rowsNumber, columnsNumber, mine);
        }
    }
}