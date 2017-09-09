package com.example.pcsaini779.hackathon;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PatriciaTrieTest {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        Random r=new Random();

        /* Creating object of PatriciaTrie */

        PatriciaTrie pt = new PatriciaTrie();
        System.out.println("Patricia Trie Test\n");
         String Square="YES";

        int digit3=3;//digit4=4,digit5=5;
        /*  Perform trie operations  */
    }
       /* if(Qube=="YES"){
            for(int i=1; i<100; i++){
                pt.insert(i*i*i);
            }}
        if(Qurter=="YES"){
            for(int i=1; i<50; i++){
                pt.insert(i*i*i*i);
            }}



        do
        {
            System.out.println("\nPatricia Trie Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. search");
            System.out.println("3. check emepty");
            System.out.println("4. make emepty");

            int choice = scan.nextInt();
            switch (choice)
            {
            case 1 :
                System.out.println("Enter key element to insert");
                pt.insert( scan.nextInt() );
                break;
            case 2 :
                System.out.println("Enter key element to search");
                System.out.println("Search result : "+ pt.search( scan.nextInt() ));
                break;
            case 3 :
                System.out.println("Empty Status : "+ pt.isEmpty() );
                break;
            case 4 :
                System.out.println("Patricia Trie cleared");
                pt.makeEmpty();
                break;
            default :
                System.out.println("Wrong Entry \n ");
                break;
            }

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');*/

    public  static ArrayList<Integer> square(int X,int Z){
        ArrayList<Integer> squar = new ArrayList<Integer>();
        for(int i=X; i<Z; i++){
            squar.add(i*i);
        }
        return squar;
    }
    public  static ArrayList<Integer> Qube(int X,int Z){
        ArrayList<Integer> Qub = new ArrayList<Integer>();
        for(int i=X; i<Z; i++){
            Qub.add(i*i);
        }
        return Qub;
    }
    public  static ArrayList<Integer> Quarter(int X,int Z){
        ArrayList<Integer> Quate = new ArrayList<Integer>();
        for(int i=X; i<Z; i++){
            Quate.add(i*i);
        }
        return Quate;
    }
    public   ArrayList<Integer>  game(PatriciaTrie pt,int digit,String Square) {
        Random r = new Random();
        int y = 0;
        ArrayList<Integer> show = new ArrayList<Integer>();
        if (Square == "2") {
            for (int i = 1; i < 30; i++) {
                pt.insert(i * i);
            }
            int count = 0, start = 0, end = 0;

            for (int i = 1; i < 30; i++) {
                int w = 0;
                int k = i * i;
                while (k > 0) {
                    k = k / 10;
                    w++;
                }
                if (w == digit) {
                    if (count == 0) {
                        start = i;
                        count = 1;
                    } else {
                        end = i;
                    }

                }

            }
            show = square(start, end);
        }
        if (Square == "3") {
            for (int i = 1; i <50; i++) {
                pt.insert(i * i * i);
            }
            int count = 0, start = 0, end = 0;
            for (int i = 1; i < 50; i++) {
                int w = 0;
                int k = i * i * i;
                while (k > 0) {
                    k = k / 10;
                    w++;
                }
                if (w == digit) {
                    if (count == 0) {
                        start = i;
                        count = 1;
                    } else {
                        end = i;
                    }

                }

            }
            show = qube(start, end);
        }
        if (Square == "4") {
            for (int i = 1; i < 50; i++) {
                pt.insert(i * i * i * i);
            }
            int count = 0, start = 0, end = 0;
            for (int i = 1; i < 50; i++) {
                int w = 0;
                int k = i * i * i * i;
                while (k > 0) {
                    k = k / 10;
                    w++;
                }
                if (w == digit) {
                    if (count == 0) {
                        start = i;
                        count = 1;
                    } else {
                        end = i;
                    }

                }

            }
            show = Quarter(start, end);
        }
        return show;
    }
    public  static ArrayList<Integer> qube(int X,int Z){
        ArrayList<Integer> qub = new ArrayList<Integer>();

        for(int i=X; i<Z; i++){
            qub.add(i*i*i);
        }
        return qub;
    }
    public  static  ArrayList<Integer> Quater(int X,int Z){
        ArrayList<Integer> Quat = new ArrayList<Integer>();

        for(int i=X; i<Z; i++){
            Quat.add(i*i*i*i);
        }
        return Quat;
    }
}
