package xyz.misterkozo.flooditarium.Utilities;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Utilities {
    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    public static int firstOccurrence(String str, Character chr) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == chr) {
                return i;
            }
        }
        return -1;
    }

    public static String deflateBoard(int[][] board) {

        String flat = "";
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                flat += String.valueOf(board[y][x]);
            }
        }
        return flat;

    }

    public static int[][] inflateBoard(String flat) {

	/*System.out.print(flat+"\n");
        int size = (int) Math.sqrt(flat.length())/2;
        int[][] board = new int[size][size];

        int row, col;
        for (int i = 0; i < flat.length(); i++) {
            row = i / size;
            col = i - (row * size);
            System.out.print(flat.charAt(i)+"\n");
            board[row][col] = Integer.valueOf(flat.charAt(i));
        }

        return board;*/

        String[] rows = flat.split(",");
        int[][] board = new int[rows.length][rows[0].length()];

        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < rows[0].length(); x++) {
                String cur = String.valueOf(rows[y].charAt(x));
                board[y][x] = Integer.valueOf(cur);
            }
        }

        return board;

    }

    public static boolean isInDrawableBounds(Drawable d, int x, int y) {
        return d.getBounds().contains(x, y);
    }
}
