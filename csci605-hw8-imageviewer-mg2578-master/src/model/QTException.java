package model;

/**
 * A class used to communicate errors with operations involving the QTree
 * and the files it uses for uncompressing.
 *
 * @author CS RIT
 */
public class QTException extends Exception{

    public QTException(String message){
        super(message);
    }


}
