package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class PuzzleTile {

    private Bitmap bitmap;
    private int number;

    public PuzzleTile(Bitmap bitmap, int number){
        this.bitmap = bitmap;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(bitmap, x * bitmap.getWidth(), y * bitmap.getHeight(), null);
    }

    public boolean isClicked(float clickX, float clickY, int tileX, int tileY) {
        int tileX0 = tileX * bitmap.getWidth();
        int tileX1 = (tileX + 1) * bitmap.getWidth();
        int tileY0 = tileY * bitmap.getWidth();
        int tileY1 = (tileY + 1) * bitmap.getWidth();
        return (clickX >= tileX0) && (clickX < tileX1) && (clickY >= tileY0) && (clickY < tileY1);
    }
}
