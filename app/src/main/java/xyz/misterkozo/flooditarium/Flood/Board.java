package xyz.misterkozo.flooditarium.Flood;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import xyz.misterkozo.flooditarium.Utilities.*;

public class Board {

    private Context context;
    private Canvas canvas;
    private int bsx, bsy;
    private int colors, size, one;
    private int[][] board;
    private Cell[][] cells;
    private Cell[] ctrl;
    private String seed;
    private String table = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int unit, division;
    private int ctrlPosY = 0;

    public Board(Context context, Canvas canvas, int bsx, int bsy, int one, int colors, int size) {
        //input is actual (human, +1) number of colors
        this.canvas = canvas;
        this.colors = colors;
        this.size   = size;
        this.board  = new int[size][size];
        this.cells  = new Cell[size][size];
        this.ctrl   = new Cell[colors];
        this.bsx    = bsx;
        this.bsy    = bsy;
        this.one    = one;

        GenerateNewGame();
    }

    public Board(Context context, int colors, int size) {
        this.context = context;
        this.colors  = colors;
        this.size    = size;
        this.board  = new int[size][size];
        this.cells  = new Cell[size][size];
        this.ctrl   = new Cell[colors];

        GenerateNewGame();
    }

    public Board(Context context, int bsx, int bsy, int one, int colors, int size) {
        this.colors = colors;
        this.size   = size;
        this.board  = new int[size][size];
        this.cells  = new Cell[size][size];
        this.ctrl   = new Cell[colors];
        this.bsx    = bsx;
        this.bsy    = bsy;
        this.one    = one;

        GenerateNewGame();
    }

    public void AfterThought(Canvas canvas, int bsx, int bsy, int one) {
        this.canvas = canvas;
        this.one    = one;
        this.bsx    = bsx;
        this.bsy    = bsy;

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.cells[y][x].AfterConstructor(this.canvas, bsx+bsx*x, bsy+bsy*y, this.one);
                this.cells[y][x].SetColor(this.board[y][x]);
            }
        }
    }

    public void GenerateNewGame() {
        ResetBoard();
        NewBoard();
        //PrepareCells();
        //CreateSeed();
    }

    public void ResetBoard() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.board[y][x] = 0;
                //this.cells[y][x] = new Cell(this.canvas, bsx+bsx*x, bsy+bsy*y, 0, this.one);
                this.cells[y][x] = new Cell();
            }
        }
    }

    public void NewBoard() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.board[y][x] = new Random().nextInt(colors);
                this.cells[y][x].SetColor(this.board[y][x]);
            }
        }
    }

    /*public void HitWith(int y, int x, int color, int original, boolean[][] visited) {
        if (visited == null) {
            visited = new boolean[this.size][this.size];
            for (int cY = 0; cY < this.size; cY++) {
                for (int cX = 0; cX < this.size; cX++) {
                    visited[cY][cX] = false;
                }
            }
        }

        if (original < 0) {
            original = this.board[0][0];
        }

        if (visited[y][x]) return;
        visited[y][x] = true;

        int change = this.board[0][0];
        if (y != this.size && x != this.size) {
            if (this.board[y][x] == original) {
                this.board[y][x] = color;
                this.cells[y][x].SetColor(color);
                HitWith(y+1, x, color, original, visited);
                HitWith(y, x+1, color, original, visited);
                HitWith(y-1, x, color, original, visited);
                HitWith(y, x-1, color, original, visited);
                //HitWith(y, x-1, color, original);
            }
        }
    }*/

    public void Flood(int original, int to, boolean[][] visited, int r, int c) {
        if (visited == null) {
            visited = new boolean[this.size][this.size];
            for (int cY = 0; cY < this.size; cY++) {
                for (int cX = 0; cX < this.size; cX++) {
                    visited[cY][cX] = false;
                }
            }
        }

        if(r < 0 || r >= this.board.length || c < 0 || c >= this.board[0].length) return;

        if(visited[r][c]) return;
        visited[r][c] = true;

        if (this.board[r][c] != original) return;

        if(this.board[r][c] == original) this.board[r][c] = to;

        Flood(original, to, visited,r+1,c);
        Flood(original, to, visited,r-1,c);
        Flood(original, to, visited,r,c+1);
        Flood(original, to, visited,r,c-1);
    }

    public boolean IsSolved() {
        int original = this.board[0][0];

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if (this.board[y][x] != original) {
                    return false;
                }
            }
        }

        return true;
    }

    public void CreateSeed() {
        /*int tl = table.length();
        String toSeed = "";
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                toSeed += String.valueOf(board[y][x]);
            }
        }

        String meanwhile = "";
        for (int i = 0; i < meanwhile.length(); i++) {
            if (meanwhile != "") {
                if (Integer.valueOf(meanwhile) < tl) {
                    meanwhile += toSeed.charAt(i);
                } else {
                    this.seed += table.charAt(Integer.valueOf(Utilities.removeLastChar(meanwhile)));
                    meanwhile = String.valueOf(meanwhile.charAt(meanwhile.length()));
                }
            } else {
                meanwhile += toSeed.charAt(i);
            }
        }
        this.seed += table.charAt(Integer.valueOf(Utilities.removeLastChar(meanwhile)));*/
        this.seed = Utilities.deflateBoard(this.board);
    }

    public void NewFromSeed(String seed) {
        /*this.seed = seed;
        String fromSeed = "";
        for (int i = 0; i < seed.length(); i++) {
            fromSeed += Utilities.firstOccurrence(this.table, seed.charAt(i));
        }*/
        ResetBoard();
        this.seed = seed;
        this.board = Utilities.inflateBoard(seed);
        this.size = Math.round((long)Math.sqrt((seed.length())));
        this.colors = 0;
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if (this.board[y][x] > this.colors)
                    this.colors = this.board[y][x];
                this.cells[y][x].SetColor(this.board[y][x]);
            }
        }
    }

    public void RefreshValues() {
        if (this.board[0].length != this.board.length) {
            this.board = new int[30][30];
            NewBoard();
        }

        this.size = this.board.length;
        this.colors = 0;
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if (board[y][x] > colors)
                    this.colors = board[y][x];
            }
        }
    }

    public int[][] GetBoard()  { return this.board;  }
    public int     GetSize()   { return this.size;   }
    public int     GetColors() { return this.colors; }

    public void SetBoard(int[][] board) {
        this.board = board;
        RefreshValues();
    }
    public void SetSize(int size) {
        this.size = size;
        RefreshValues();

    }
    public void SetColors(int colors) {
        this.colors = colors;
        RefreshValues();
    }

    /*public void AfterCells(Canvas canvas, int posX, int posY, int size) {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.cells[y][x].AfterConstructor(canvas, posX, posY, size);
            }
        }
    }*/

    public void PrepareCells() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.cells[y][x].PrepareCell();
            }
        }
    }

    public void PrepareControls() {
        this.ctrl     = new Cell[this.colors];
        this.division = (this.colors*3) + (this.colors-1) + 2; //controls, as inferred from here, span across 3 units
        this.unit     = (this.canvas.getWidth() / this.division);

        int posX = 0, posY = 0;
        for (int i = 0; i < this.colors; i++) {
            posX = this.unit + this.unit*3*i + this.unit*i;
            posY = this.canvas.getHeight() - this.unit - this.unit*3;
            this.ctrl[i] = new Cell(this.canvas, posX, posY, i, this.unit*3);
            this.ctrl[i].PrepareCell();
        }

        this.ctrlPosY = posY;
    }

    public int GetDivision() { return this.division; }
    public int GetUnit() { return this.unit; }
    public Cell[] GetCtrl() { return this.ctrl; }
    public int GetCtrlPosY() { return this.ctrlPosY; }

    public int GetDifficulty() { return (this.colors/this.size)*100; }

}
