package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    public ArrayList<PuzzleTile> tiles = new ArrayList<>();
    private String LOG_TAG = "DLG";
    public int steps = 0;
    public PuzzleBoard previousBoard = null;

    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        Log.d(LOG_TAG, "" + bitmap.getWidth());
        int tempHeight = (int) (((float) parentWidth / (float) bitmap.getWidth()) * bitmap.getHeight());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, parentWidth, tempHeight, false);
        Bitmap squareBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, parentWidth, parentWidth);
        int size = squareBitmap.getWidth() / NUM_TILES;
        for (int i=0; i<NUM_TILES; i++){
            for (int j=0; j<NUM_TILES; j++){
                Bitmap chunk = Bitmap.createBitmap(squareBitmap, j * size, i * size, size, size);
                if (i == NUM_TILES-1 && j == NUM_TILES-1){
                    tiles.add(null);
                } else {
                    tiles.add(new PuzzleTile(chunk, NUM_TILES * i + j));
                }
            }
        }
    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        steps = otherBoard.steps + 1;
        previousBoard = otherBoard;
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    public PuzzleBoard getPreviousBoard(){
        return previousBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        int i, j=0;
        // get empty tile
        for (i=0; i<NUM_TILES; i++){
            for (j=0; j<NUM_TILES; j++){
                if (tiles.get(i * NUM_TILES + j) == null){
                    break;
                }
            }
            if (j != NUM_TILES) break;
        }
        int nullX = j, nullY = i;
        ArrayList<PuzzleBoard> neighbourBoards = new ArrayList<>();
        // evaluate neighbours
        // find and add them in ArrayList
        for (i=0; i<4; i++){
            int x = nullX + NEIGHBOUR_COORDS[i][0];
            int y = nullY + NEIGHBOUR_COORDS[i][1];
            if (x >= 0 && x < NUM_TILES && y >= 0 && y < NUM_TILES){
                PuzzleBoard copy = new PuzzleBoard(this);
                copy.swapTiles(nullY * NUM_TILES + nullX, y * NUM_TILES + x);
                neighbourBoards.add(copy);
            }
        }
        return neighbourBoards;
    }

    public int priority() {
        int count = steps, realPos;
        for (int i=0; i<NUM_TILES; i++){
            for (int j=0; j<NUM_TILES; j++){
                if (tiles.get(NUM_TILES * i + j) == null)
                    continue;
                realPos = tiles.get(NUM_TILES * i + j).getNumber();
                count += Math.abs( (realPos / NUM_TILES) - i );
                count += Math.abs( (realPos % NUM_TILES) - j );
            }
        }
        return count;
    }

}
