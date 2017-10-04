package xyz.misterkozo.flooditarium.Flood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {

    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};
    private Canvas canvas;
    private int posX, posY;
    private Paint paint;
    private int size;

    public Cell(Canvas canvas) {
        this.canvas = canvas;
        this.posX   = -1;
        this.posY   = -1;
        this.paint  = new Paint();
        this.size   = 69;

        this.paint.setColor(Color.WHITE);
    }

    public Cell(Canvas canvas, int posX, int posY, int color, int size) {
        this.canvas = canvas;
        this.posX   = posX;
        this.posY   = posY;
        this.size   = size;

        this.paint = new Paint();
        this.paint.setColor(this.colors[color]);
    }

    public void AfterConstructor(Canvas canvas, int posX, int posY, int size) {
        this.canvas = canvas;
        this.posX   = posX;
        this.posY   = posY;
        this.size   = size;

        this.paint = new Paint();
        //this.paint.setColor(this.colors[color]);
    }

    public Cell() {
        this.paint = new Paint();
    }

    public void PrepareCell() {
        this.canvas.drawRect(posX, posY, posX+this.size, posY+this.size, this.paint);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void SetColor(int color) {
        this.paint.setColor(this.colors[color]);
    }

    public int[] GetColors() { return this.colors; }
}