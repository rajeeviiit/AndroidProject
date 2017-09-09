package com.example.pcsaini779.hackathon;

/**
 * Created by jsrathore on 10/11/16.
 */

public class PatriciaTrie {
    private PatriciaNode root;
    private static final int MaxBits = 50;

    /** Constructor **/
    public PatriciaTrie()
    {
        root = null;
    }
    /** function to check if empty **/
    public boolean isEmpty()
    {
        return root == null;
    }
    /** function to clear **/
    public void makeEmpty()
    {
        root = null;
    }
    /** function to get ith bit of key k from left **/
    private boolean bit(int k, int i)
    {
        String binary = Integer.toString(k, 2);
        while (binary.length() != MaxBits)
            binary = "0" + binary;
        if (binary.charAt(i - 1) == '1')
            return true ;
        return false;
    }
    /** function to search for an element **/
    public boolean search(int k)
    {
        int numOfBits = (int) (Math.log(k)/Math.log(2)) + 1;
        if (numOfBits > MaxBits)
        {
            System.out.println("Error : Number too large");
            return false;
        }
        PatriciaNode searchNode = search(root, k);
        if (searchNode.data == k)
            return true;
        else
            return false;
    }
    /** function to search for an element **/
    private PatriciaNode search(PatriciaNode t, int k)
    {
        PatriciaNode currentNode, nextNode;
        if (t == null)
        {
            return null;
        }
        nextNode = t.leftChild;
        currentNode = t;
        while (nextNode.bitNumber > currentNode.bitNumber)
        {
            currentNode = nextNode;
            nextNode = (bit(k, nextNode.bitNumber)) ? nextNode.rightChild : nextNode.leftChild;
        }
        return nextNode;
    }
    /** function to insert and element **/
    public void insert(int ele)
    {
        int numOfBits = (int) (Math.log(ele)/Math.log(2)) + 1;
        if (numOfBits > MaxBits)
        {
            System.out.println("Error : Number too large");
            return;
        }
        root = insert(root, ele);
    }
    /** function to insert and element **/
    private PatriciaNode insert(PatriciaNode t, int ele)
    {
        PatriciaNode current, parent, lastNode, newNode;
        int i;

        if (t == null)
        {
            t = new PatriciaNode();
            t.bitNumber = 0;
            t.data = ele;
            t.leftChild = t;
            t.rightChild = null;
            return t;
        }

        lastNode = search(t, ele);

        if (ele == lastNode.data)
        {
            System.out.println("Error : key is already present\n");
            return t;
        }

        for (i = 1; bit(ele, i) == bit(lastNode.data, i); i++);

        current = t.leftChild; parent = t;
        while (current.bitNumber > parent.bitNumber && current.bitNumber < i)
        {
            parent = current;
            current = (bit(ele, current.bitNumber)) ? current.rightChild : current.leftChild;
        }

        newNode = new PatriciaNode();
        newNode.bitNumber = i;
        newNode.data = ele;
        newNode.leftChild = bit(ele, i) ? current : newNode;
        newNode.rightChild = bit(ele, i) ? newNode : current;

        if (current == parent.leftChild)
            parent.leftChild = newNode;
        else
            parent.rightChild = newNode;

        return t;
    }
}
