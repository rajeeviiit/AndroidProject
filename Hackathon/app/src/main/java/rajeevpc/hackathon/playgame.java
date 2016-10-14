package rajeevpc.hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class playgame extends AppCompatActivity {


    GridView gameGrid;
    ArrayList<Integer> gridValues;
    GridAdapter gridAdapter;
    Button startBtn ,shuffleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgame);
        startBtn = (Button) findViewById(R.id.start);

        shuffleBtn =(Button) findViewById(R.id.reset);
        gameGrid = (GridView) findViewById(R.id.gridview);
        gridAdapter = new GridAdapter(this);
        final ShortestPath shortestPath = new ShortestPath();

        //on shuffle click

        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class Shoffle{
                    // Shuffle 2D Array with same no of columnsfor each row
                    public void shuffle(int[][] matrix, int columns, Random rnd){
                        int size = matrix.length*columns;
                        for (int i = size; i> 1; i--)
                            swap(matrix,columns,i-1,rnd.nextInt(i));
                    }

                    // Swap Function
                    public void swap(int[][] matrix, int columns, int i,int j){
                        int tmp = matrix[i/columns][i%columns];
                        matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
                        matrix[j / columns][j % columns] = tmp;
                    }
                }

            }
        });

        //on item click.
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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

                        for (int i = 1;i < count-1; i++) {

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

                    }}.start();

            }
        });

        gridValues = new ArrayList<>();
        shortestPath.makeReadyMap();
        gridAdapter.setPathValues(gridValues);
        gameGrid.setAdapter(gridAdapter);

        //set on item onclicklisner game grid

        gameGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String value = (String) adapterView.getItemAtPosition(i);
                Log.d("SHORTEST", " adding " +value);




            }
        });


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
            LinkedList<Field> q1 = new LinkedList<>();

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
            return x * 10 + y;
        }

        public void makeReadyMap() {
            Random rd=new Random();
            area = new int[][]{
                    {2, 1, 1, 1, 1, 0,0,1,1,1},
                    {1, 1, 1, 0, 1, 1,1,1,1,0},
                    {1, 0, 0, 1, 0, 1,0,1,0,0},
                    {1, 1, 1, 1, 1, 1,1,1,1,0},
                    {0, 0, 0, 0, 1, 0,1,0,0,0},
                    {1, 1, 1, 0, 1, 1,1,0,1,0},
                    {1, 0, 0, 1, 0, 1,0,1,0,0},
                    {1, 1, 0, 1, 1, 1,0,0,1,0},
                    {0, 0, 1, 1, 1, 0,1,0,1,0},
                    {1, 1, 1, 1, 1,1,1,1,1,3}

            };

            //shuffle(area,10,new Random());

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    gridValues.add(10 * i + j, area[i][j]);
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