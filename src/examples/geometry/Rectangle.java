package examples.geometry;

public class Rectangle extends Shape {

    private final Point lowerLeft, upperRight;

    public Rectangle(Point ll, Point ur) {
        if ( ll.getX() >= ur.getX() || ll.getY() >= ur.getY() ) {
            throw new IllegalArgumentException(
                "Points ll and ur are incorrectly oriented."
            );
        }
        lowerLeft = ll;
        upperRight = ur;
    }

    @Override
    public boolean contains(Point xy) {
        return (
            // x coordinate is in range
            lowerLeft.getX() <= xy.getX() && xy.getX() <= upperRight.getX()
            // y coordinate is in range
            && lowerLeft.getY() <= xy.getY() && xy.getY() <= upperRight.getY()
        );
    }

    @Override
    public double getArea() {
        double base = upperRight.getX() - lowerLeft.getX();
        double height = upperRight.getY() - lowerLeft.getY();
        return base * height;
    }

}
