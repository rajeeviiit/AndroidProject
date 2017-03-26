package com.google.engedu.puzzle8;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

public class PuzzleBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private PuzzleBoard puzzleBoard;
    private ArrayList<PuzzleBoard> animation;
    private Random random = new Random();
    private final String LOG_TAG = "DLG";
    private HashSet<String> visitedPuzzleBoards = new HashSet<>();

    public PuzzleBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }

    public void initialize(Bitmap imageBitmap) {
        int width = getWidth();
        puzzleBoard = new PuzzleBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (puzzleBoard != null) {
            if (animation != null && animation.size() > 0) {
                puzzleBoard = animation.remove(0);
                puzzleBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    puzzleBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                puzzleBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        if (animation == null && puzzleBoard != null) {
            ArrayList<PuzzleBoard> boards;
            for (int i = 0; i < NUM_SHUFFLE_STEPS; i++){
                boards = puzzleBoard.neighbours();
                puzzleBoard = boards.get(random.nextInt(boards.size()));
            }
            // refresh board
            puzzleBoard.reset();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animation == null && puzzleBoard != null) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (puzzleBoard.click(event.getX(), event.getY())) {
                        invalidate();
                        if (puzzleBoard.resolved()) {
                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(event);
    }

    public void solve() {
        Comparator<PuzzleBoard> comparator = new PuzzleBoardComparator();
        PriorityQueue<PuzzleBoard> boardQueue = new PriorityQueue<PuzzleBoard>(1000, comparator);
        puzzleBoard.steps = 0; puzzleBoard.previousBoard = null;
        boardQueue.add(puzzleBoard);
        ArrayList<PuzzleBoard> solution = new ArrayList<>();
        PuzzleBoard previousBoard = null;
        visitedPuzzleBoards.clear();

        while (!boardQueue.isEmpty()){
            PuzzleBoard temp = boardQueue.poll();
            visitedPuzzleBoards.add(puzzleBoardSerialize(temp));
            Log.d(LOG_TAG, "Solving " + temp.priority());
            if ((temp.priority() - temp.steps) != 0){ // not the solution
                for (PuzzleBoard p : temp.neighbours()){
                    if (!p.equals(previousBoard) && !visitedPuzzleBoards.contains(puzzleBoardSerialize(p))){
//                        p.steps = 0;
                        boardQueue.add(p);
                    }
                }
            } else { // solution
                solution.add(temp);
                while (temp != null){
                    if (temp.getPreviousBoard() == null) break;
                    solution.add(temp.getPreviousBoard());
                    temp = temp.getPreviousBoard();
                }
                Collections.reverse(solution);
                animation = solution;
                invalidate();
                break;
            }
            previousBoard = temp;
        }
    }

    private String puzzleBoardSerialize(PuzzleBoard puzzleBoard){
        ArrayList<PuzzleTile> tiles = puzzleBoard.tiles;
        String s = "";
        for (PuzzleTile t: tiles){
            if (t == null){
                s += "a";
            } else {
                s += ("" + t.getNumber());
            }
        }
        return s;
    }
}

// Comparing Puzzle Boards
class PuzzleBoardComparator implements Comparator<PuzzleBoard>
{
    @Override
    public int compare(PuzzleBoard lhs, PuzzleBoard rhs) {
        if (lhs.priority() < rhs.priority())
            return -1;
        if (lhs.priority() > rhs.priority())
            return 1;
        return 0;
    }
}