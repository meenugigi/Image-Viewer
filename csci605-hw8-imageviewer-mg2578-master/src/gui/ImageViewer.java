/*
Homework 8 : Image Viewer
File Name : ImageViewer.java
 */
package gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.QTException;
import model.QuadTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *  The main class that extends the Application
 *
 * @author Meenu Gigi, mg2578@rit.edu
 * @author Vedika Vishwanath Painjane, vp2312@rit.edu
 */
public class ImageViewer extends Application {

    /** To store the image array */
    private int [][]arrayImage;

    /** To store the filename */
    private String filename;

    /** To store size of image */
    private int imageSize;

    /** To store value to be passed in Switch block. */
    private String forSwitch;

    /** Command-line argument*/
    private static final String CMDARG = "ImageViewer";


    /**
     * Pre-processes Command-line arguments.
     */
    @Override
    public void init(){
//        to store command line arguments.
        List<String> args = getParameters().getRaw();
//        takes arguments for uncompressed files.
        if(args.size() == 2 && args.get(0).equals(CMDARG)){
            filename = args.get(1);
            forSwitch = "uncompressed";
        }
//            takes arguments for compressed files.
        else if(args.size() == 3 && args.get(0).equals(CMDARG) &&
                args.get(1).equals("-c")) {
            filename = args.get(2);
            forSwitch = "compressed";
        }
//            for incorrect number of arguments.
//            terminate program.
        else {
            System.err.println("Usage: ImageViewer [-c] <filename>\n" +
                    "-c Reads in a compressed image file. If this option is" +
                    " not present,\n" +
                    "the file is considered to be uncompressed.");
            System.exit(0);
        }
    }


    /**
     * Calls methods to process compressed and uncompressed files.
     * Sets scene to stage.
     *
     * @param stage   the Stage.
     */
    @Override
    public void start( Stage stage ){

        try{
            File file = new File(filename);
            Scanner sc = new Scanner(file);

//        process compressed and uncompressed files.
            switch (forSwitch){
                case "uncompressed": {
                    processingUncompressed(sc);
                    break;
                }
                case "compressed": {
                    processingCompressed();
                    break;
                }
                default: break;
            }
            sc.close();
//        raw image on canvas.
            Group group = rawImage();
            Scene scene = new Scene(group);
            stage.setTitle(CMDARG + ": " + filename);
            stage.setScene(scene);
            stage.show();
        }catch (FileNotFoundException e){
            System.err.println("File does not exist!");
            System.exit(0);
        }
    }

    /**
     * Reads uncompressed file into a raw image
     * stores raw image as a two dimensional array of integers.
     * Checks if integer value is within defined range.
     * Checks if non-integer value is encountered.
     *
     * @param sc   Instance of Scanner class.
     */
    private void processingUncompressed(Scanner sc){
//        list to store data after reading from file.
        List<Integer> list = new ArrayList<>();
        int fileLength=0;
        int data;
//        reads from file.
        while (sc.hasNextLine()) {
//            Checks if integer value is within defined range.
            try{
                data = Integer.parseInt(sc.nextLine());
                if(data < 0 || data > 255){
                    throw new QTException("Error: Integer value encountered " +
                            "that is outside the range 0-255");
                }
//                adds data to list.
                else{
                    list.add(data);
                    fileLength++;
                }
            }
//            if value outside defined range,
//            throw error and terminate program.
            catch (QTException e){
                System.err.println(e.getMessage());
                System.exit(0);
            } catch (NumberFormatException e){
                System.err.println("Error: Non-integer value encountered " +
                        "for a pixel value");
                System.exit(0);
            }
        }
//        calls method to check if image size is a square.
        checkImageSize(fileLength);
//        stores raw image as a 2-D array of integers.
        QuadTree qtUnCompressed = new QuadTree();
        arrayImage = qtUnCompressed.getUncompress(imageSize, list);
    }


    /**
     * Checks if image size is a square.
     *
     * @param fileLength    length of file.
     */
    private void checkImageSize(int fileLength) {
//        compute size of image.
        imageSize = (int)Math.sqrt(fileLength);
//        check if image size is a square.
//        if not, throw error and terminate program.
        try{
            if(Math.pow(imageSize, 2) != fileLength){
                throw new QTException("Error: Image size is not a square");
            }
        }catch (QTException e){
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * Creates a Group.
     * Creates a Canvas
     * Gets 2-D graphics context from the Canvas.
     * Adds canvas to Group.
     *
     * @return the group containing the canvas.
     */
    private Group rawImage(){
        Group group = new Group();
        Canvas canvas = new Canvas(imageSize, imageSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        raw image on canvas
        for (int row = 0; row < arrayImage.length; row++) {
            for (int col = 0; col < arrayImage[row].length; col++) {
                Color color = Color.rgb(arrayImage[row][col],
                        arrayImage[row][col],arrayImage[row][col],1);
                gc.setFill(color);
                gc.fillRect(row, col, 1, 1);
            }
        }
//        adds canvas to group.
        group.getChildren().add(canvas);
        return group;
    }


    /**
     * Processing compressed file.
     * Displays pre-order traversal of QuadTree.
     * Calls method to convert tree to array.
     */
    private void processingCompressed(){
        QuadTree qtCompressed = new QuadTree(filename);
//        computes number of pixels in image
        imageSize = (int)Math.sqrt(qtCompressed.getInitialSize());
        arrayImage = qtCompressed.getTreeArray();
//        prints preorder tree traversal.
        System.out.println("Uncompressing: "+filename);
        System.out.print("QTree: " + qtCompressed.getPreOrder().toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", ""));
    }

    /**
     * Main method to launch the application.
     *
     * @param args   command line arguments.
     */
    public static void main( String[] args ) {
        Application.launch( args );
    }
}
