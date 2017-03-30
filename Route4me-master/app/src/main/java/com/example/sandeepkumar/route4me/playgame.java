package com.example.sandeepkumar.route4me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class playgame extends AppCompatActivity {

    private static int val,dest,c1=0;
    //private int val;
    GridView gameGrid;
    View v1;
   private static int[] va=new int[2];
    ArrayList<Integer> gridValues;
    GridAdapter gridAdapter;
    Button startBtn ,shuffle,source,des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgame);
        startBtn = (Button) findViewById(R.id.start);
        //source=(Button)findViewById(R.id.source);
        //des=(Button)findViewById(R.id.dest);
        shuffle =(Button) findViewById(R.id.reset);
        gameGrid = (GridView) findViewById(R.id.gridview);
        gridAdapter = new GridAdapter(this);

      final ShortestPath shortestPath = new ShortestPath();

        //on shuffle click

//        source.setOnClickListener(new View.OnClickListener() {


  //          public void onClick(final View v) {
                gameGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                        //    gridAdapter.setPathItem(position,Constants.ITEM_TYPE_INTERMEDIATE_ITEM);
                        //va[c1]=position;
                          //  c1++;

                      //if(c1==2){
                        va[0]=position;

                          //  gridAdapter.setPathItem(va[1],Constants.ITEM_TYPE_DESTINATION);
                            gridAdapter.setPathItem(va[0],Constants.ITEM_TYPE_SOURCE);
                            shortestPath.getSolution();
                            gridAdapter.setPathValues(gridValues);
                            gameGrid.setAdapter(gridAdapter);
                        //}
                       /**/ //  call_Method(val);

                        // ShortestPath shortestPath=new ShortestPath();
                        //gridValues = new ArrayList<>();
                        // shortestPath.makeReadyMap(val);
                      //*  gridAdapter.setPathItem(position,Constants.ITEM_TYPE_SOURCE);
                     /*   shortestPath.getSolution();
                        gridAdapter.setPathValues(gridValues);
                        gameGrid.setAdapter(gridAdapter);*/
}
                });








            //}
        //});//




        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(playgame.this, playgame.class));

                finish();
            }
        });
        //on item click.
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<Integer> sols = shortestPath.getSolution();
           //     final ArrayList<Integer> sols1 = shortestPath.getSolution1();

                if (sols.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Shortest_Path is not found!", Toast.LENGTH_LONG).show();
                }
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Random rd=new Random();
                        int n,x=0,x1=0,n1;
                        final int count = sols.size();
                       // int count1 = sols1.size();
                        int p=0,i,j,k,v;
                           for ( i = 1 ; i < count - 1; i++) {
                                final int loopNum = i;
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                               final int finalI = i;
                               runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        gridAdapter.setPathItem(sols.get(loopNum), Constants.ITEM_TYPE_INTERMEDIATE_ITEM);
                                        if(finalI ==count-2){
                                            Toast.makeText(getApplicationContext(), "YOU REACHED DESTINATION!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }




                    }}.start();


            }

        });



        gridValues = new ArrayList<>();
       shortestPath.makeReadyMap(val);
        gridAdapter.setPathValues(gridValues);
        gameGrid.setAdapter(gridAdapter);

    }

/*
protected void call_Method(int val){
    ShortestPath shortestPath=new ShortestPath();
    gridValues = new ArrayList<>();
   // shortestPath.makeReadyMap(val);
    shortestPath.getSolution();
    gridAdapter.setPathValues(gridValues);
    gameGrid.setAdapter(gridAdapter);

}
*/




    public class ShortestPath {
    int[][] area;

    public List<Field> findShortestPath(int[][] area, int x1, int y1) {
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





        Field start = fields[x1][y1];
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


        public void Shuffling(int[][] area){
          //  ArrayList<Integer> array=new ArrayList<>();
            Random rd=new Random();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (area[i][j] != 2 && area[i][j] != 3) {
                        area[i][j] = 1;
                    }
                }
            }
            int n, x, y;
            for (int i = 0; i < 45; i++) {
                n = rd.nextInt(100);
                x = n / 10;
                y = n % 10;
                if (area[x][y] != 2 && area[x][y] != 3) {
                    area[x][y] = 0;
                }

            }


            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    gridValues.add(10 * i + j, area[i][j]);
                }
            }

        }




        public void makeReadyMap(int p1) {

        area = new int[][]{
                {1, 1, 1, 1, 1, 0,0,1,1,1},
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

       // area[p1/10][p1%10]=2;

Shuffling(area);

    }


    public ArrayList<Integer> getSolution() {
        //playgame ob=new playgame();
        //area[playgame.va[1]/10][playgame.va[1]%10]=playgame.va[1];
        //gridValues.add(playgame.va[1], area[playgame.va[1]/10][playgame.va[1]%10]);
        ArrayList<Integer> solutions = new ArrayList<>();
        List<Field> shortestPath = findShortestPath(area,(playgame.va[0])/10,(playgame.va[0])%10);
        //ArrayList<Integer> index = new ArrayList<>();
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