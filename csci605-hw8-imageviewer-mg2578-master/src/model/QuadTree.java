/*
Homework 8 : Image Viewer
File Name : QuadTree.java
 */
package model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

/**
 *  Creates the QuadTree.
 *  UnCompresses the compressed files.
 *  Stores raw image as a 2-D array of integers.
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class QuadTree {

    /** To store preorder tree traversal */
    private List<Integer> preOrder = new ArrayList<>();

    /** Value representing an internal node */
    private static final int QUAD_SPLIT = -1;

    /** The node */
    private QTNode tree;

    /** Number of pixels in image */
    private int initialSize;


    /**
     * Constructor.
     */
    public QuadTree(){}


    /**
     * Parameterized Constructor.
     * Reads the file.
     *
     * @param filename    name of file.
     */
    public QuadTree(String filename){
        try{BufferedReader file = new BufferedReader(
                new FileReader(filename));
            initialSize = parseInt(file.readLine());
//            call method to check if node
//            is internal node or child node.
            tree = getNode(parseInt(file.readLine()), file);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Recursive method.
     * Checks if a node is an internal node or child node.
     * Populates the preorder traversal tree list.
     *
     * @param data    data read from file.
     * @param file    file being read.
     * @return the node
     */
    private QTNode getNode(int data, BufferedReader file) throws IOException {
        QTNode node;
//        if node is an internal node.
        if(data == QUAD_SPLIT){
//            populate list with preorder tree traversal.
            preOrder.add(data);
            node = new QTNode(QUAD_SPLIT,
                    getNode(parseInt(file.readLine()), file),
                    getNode(parseInt(file.readLine()), file),
                    getNode(parseInt(file.readLine()), file),
                    getNode(parseInt(file.readLine()), file));

        }
//        if node is a child node.
        else{
//           populate list with preorder tree traversal.
            preOrder.add(data);
            node = new QTNode(data);
        }
        return node;
    }


    /**
     * Converts image text file as a 2 Dimensional array of integers.
     *
     * @param size    size of the image.
     * @param list    contains data read from uncompressed file.
     * @return the 2D array of integers.
     */
    private int[][] uncompressFile(int size, List<Integer> list){
        int[][] imageArray;
        imageArray = new int[size][size];
        int y=0;
//        iterates over image size.
//        stores data in 2D array.
        for (int i=0; i<size; i++){
            for (int j =0; j<size; j++){
                imageArray[j][i] = list.get(y);
                y++;
            }
        }
        return imageArray;
    }


    /**
     * Getter to obtain the 2 Dimensional Array from raw image.
     *
     * @param size    size of the image.
     * @param list    contains data read from uncompressed file.
     * @return the 2D array of integers.
     */
    public int[][] getUncompress(int size, List<Integer> list){
        return uncompressFile(size, list);
    }


    /**
     * Getter to access list containing preorder tree traversal.
     * @return the list containing the preorder tree traversal.
     */
    public List<Integer> getPreOrder() {
        return preOrder;
    }


    /**
     * Converts QuadTree to 2 Dimensional array.
     * @return the array representing the QuadTree.
     */
    private int[][] convertTreeToArray(){
//        computes size of 2D array.
        int size = (int)Math.sqrt(initialSize);
        int [][]treeToArray= new int[size][size];
        traverseQuadTree(tree, 0, 0, size, treeToArray);
        return treeToArray;
    }


    /**
     * Traverses the QuadTree recursively.
     * Populates all nodes of Quadrant containing child nodes.
     *
     * @param node      node of QuadTree.
     * @param x         x-coordinate.
     * @param y         y-coordinate.
     * @param size      size of the image.
     * @param imgArr    image array.
     */
    private void traverseQuadTree(QTNode node, int x, int y,
                                  int size, int[][] imgArr){
//        if node is a child node.
        if (node.getValue() != QUAD_SPLIT){
            fillTreeArray(imgArr, x, y, size, node.getValue());
        }
//        if node is an internal node.
//        populate all nodes of the quadrant recursively.
        else{
            size = size/2;
            traverseQuadTree(node.getUpperLeft(), x, y, size, imgArr);
            traverseQuadTree(node.getUpperRight(), x + size , y, size, imgArr);
            traverseQuadTree(node.getLowerLeft(), x, y + size, size, imgArr);
            traverseQuadTree(node.getLowerRight(), x+size, y+size, size,
                    imgArr);
        }
    }


    /**
     * Getter to access the 2D array created using Quadtree.
     * @return the array representing the QuadTree.
     */
    public int[][] getTreeArray(){
        return convertTreeToArray();
    }

    /**
     * Getter to access the number of pixels in an image.
     * @return number of pixels in image.
     */
    public int getInitialSize(){
        return initialSize;
    }


    /**
     * Populates the image array using node values.
     *
     * @param imgArr    image array.
     * @param x         x-coordinate.
     * @param y         y-coordinate.
     * @param size      size of the image.
     * @param value     value of the current node.
     */
    private void fillTreeArray(int[][] imgArr, int x, int y, int size,
                               int value){
        for(int i = y; i < y+size; i++){
            for(int j = x; j < x+size; j++){
                imgArr[j][i] = value;
            }
        }
    }
}
