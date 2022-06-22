package model;

/**
 * A node contains a value which is either a grayscale color (0-255) for a
 * region, or QTree.QUAD_SPLIT meaning this node cannot hold a single color
 * and thus has split itself into 4 sub-regions (left, center-left, center-right, right).
 *
 * @author CS RIT
 */
public class QTNode {
    /**
     * The node's value
     */
    private int value;
    /**
     * The upper left quadrant
     */
    private QTNode ul;
    /**
     * The upper right quadrant
     */
    private QTNode ur;
    /**
     * The lower left quadrant
     */
    private QTNode ll;
    /**
     * The lower right quadrant
     */
    private QTNode lr;

    /**
     * Construct a leaf node with no children.
     *
     * @param value node's value
     */
    public QTNode(int value) {
        this(value, null, null, null, null);
    }

    /**
     * Construct a quad tree node.
     *
     * @param value the node's value
     * @param ul    the upper left quadrant node
     * @param ur    the upper right quadrant node
     * @param ll    the lower left quadrant node
     * @param lr    the lower right quadrant node
     */
    public QTNode(int value, QTNode ul, QTNode ur, QTNode ll, QTNode lr) {
        this.value = value;
        this.ul = ul;
        this.ur = ur;
        this.ll = ll;
        this.lr = lr;
    }

    /**
     * Get the node's value
     *
     * @return the node's value
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the upper left quadrant node
     *
     * @return the upper left node
     */
    public QTNode getUpperLeft() {
        return ul;
    }

    /**
     * Get the upper right quadrant node
     *
     * @return the upper right node
     */
    public QTNode getUpperRight() {
        return ur;
    }

    /**
     * Get the lower left quadrant node
     *
     * @return the lower left node
     */
    public QTNode getLowerLeft() {
        return ll;
    }

    /**
     * Get the lower right quadrant node
     *
     * @return the lower right node
     */
    public QTNode getLowerRight() {
        return lr;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
