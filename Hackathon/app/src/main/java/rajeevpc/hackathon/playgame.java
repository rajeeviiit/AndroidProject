package rajeevpc.hackathon;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class playgame extends AppCompatActivity {


    GridView gameGrid;
    ArrayList<Integer> gridValues;
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgame);

        gameGrid = (GridView) findViewById(R.id.gridview);
        gridAdapter = new GridAdapter(this);

        gridValues = new ArrayList<>();
//
//        gridValues.add(0,new Integer(Constants.ITEM_TYPE_SOURCE));
//        gridValues.add(1,new Integer(Constants.ITEM_TYPE_BANNED));
//        gridValues.add(2,new Integer(Constants.ITEM_TYPE_BLANK));
//        gridValues.add(3,new Integer(Constants.ITEM_TYPE_INTERMEDIATE_ITEM));
//        gridValues.add(4,new Integer(Constants.ITEM_TYPE_BANNED));
//        gridValues.add(5,new Integer(Constants.ITEM_TYPE_BLANK));
//        gridValues.add(6,new Integer(Constants.ITEM_TYPE_INTERMEDIATE_ITEM));
//        gridValues.add(7,new Integer(Constants.ITEM_TYPE_INTERMEDIATE_ITEM));
//        gridValues.add(8,new Integer(Constants.ITEM_TYPE_DESTINATION));

        ShortestPath shortestPath = new ShortestPath();
        shortestPath.makeReadyMap();
        gridAdapter.setPathValues(gridValues);
        gameGrid.setAdapter(gridAdapter);

        final ArrayList<Integer> sols = shortestPath.getSolution();


        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                int count = sols.size();

                for (int i = 0; i < count; i++) {

                    final int loopNum = i;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            gridAdapter.setPathItem(sols.get(loopNum), Constants.ITEM_TYPE_INTERMEDIATE_ITEM);

                        }
                    });
                }

            }
        }.start();


    }


    public class ShortestPath {
        int[][] area;

        public List<Field> findShortestPath(int[][] area) {
            Field[][] fields = new Field[area.length][area[0].length];
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[0].length; j++) {
                    if (area[i][j] != 0) {
                        fields[i][j] = new Field(i, j, Integer.MAX_VALUE, null);
                    }
                }
            }

            LinkedList<Field> q = new LinkedList<>();

            Field start = fields[0][0];
            start.dist = 0;
            q.add(start);

            Field dest = null;
            Field cur;
            while ((cur = q.poll()) != null) {
                if (area[cur.x][cur.y] == 3) {
                    dest = cur;
                }
                visitNeighbour(fields, q, cur.x - 1, cur.y, cur);
                visitNeighbour(fields, q, cur.x + 1, cur.y, cur);
                visitNeighbour(fields, q, cur.x, cur.y - 1, cur);
                visitNeighbour(fields, q, cur.x, cur.y + 1, cur);
            }

            if (dest == null) {
                return Collections.emptyList();
            } else {
                LinkedList<Field> path = new LinkedList<>();
                cur = dest;
                do {
                    path.addFirst(cur);
                } while ((cur = cur.prev) != null);

                return path;
            }
        }

        private void visitNeighbour(Field[][] fields, LinkedList<Field> q, int x, int y, Field parent) {
            int dist = parent.dist + 1;
            if (x < 0 || x >= fields.length || y < 0 || y >= fields[0].length || fields[x][y] == null) {
                return;
            }
            Field cur = fields[x][y];
            if (dist < cur.dist) {
                cur.dist = dist;
                cur.prev = parent;
                q.add(cur);
            }
        }

        private class Field implements Comparable<Field> {
            public int x;
            public int y;
            public int dist;
            public Field prev;

            private Field(int x, int y, int dist, Field prev) {
                this.x = x;
                this.y = y;
                this.dist = dist;
                this.prev = prev;
            }

            @Override
            public int compareTo(Field o) {
                return dist - o.dist;
            }
        }


        private int XYtoIndex(int x, int y) {
            return x * 6 + y;
        }

        public void makeReadyMap() {
            area = new int[][]{
                    {2, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 0, 1},
                    {1, 0, 0, 3, 1, 1},
                    {1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0, 0}
            };

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    gridValues.add(6 * i + j, area[i][j]);
                }
            }

        }


        public ArrayList<Integer> getSolution() {

            ArrayList<Integer> solutions = new ArrayList<>();
            List<Field> shortestPath = findShortestPath(area);
            ArrayList<Integer> index = new ArrayList<>();
            int c = 0;
            for (Field f : shortestPath) {
                System.out.println(String.format("(%d;%d)", f.x, f.y));
                Log.d("SHORTEST", " adding " + XYtoIndex(f.x, f.y));
                solutions.add(c++, XYtoIndex(f.x, f.y));
            }

            return solutions;
        }
    }
}