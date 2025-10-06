package examples.geometry;

public abstract class Shape {
    // instance variables go here
    //     no instance variables for this class

    // abstract methods (not implemented) go here
    
    /**
     * @param xy - Point to be tested for belonging.
     * @return - true if and only if xy is contained in this shape
     */
    abstract boolean contains(Point xy);
    
    /**
     * @return - The area of this shape.
     */
    abstract double getArea();

    // concrete methods (implemented) go here
    //     no concrete methods for this class
}
