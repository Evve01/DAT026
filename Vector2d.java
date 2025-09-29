package bouncing_balls;

/**
 * Simple two-dimensional vector.
 * This is used for positions and velocities of the balls.
 */
public class Vector2d {
    private double x;
    private double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Computes the sum of this vector and another vector.
     *
     * @param other The other vector.
     * @return A new vector, the sum.
     */
    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    /**
     * Adds another vector to this one.
     *
     * @param other The vector to add to this one.
     */
    public void add_inplace(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Computes the difference between this vector and another vector.
     *
     * @param other The other vector.
     * @return A new vector, the difference.
     */
    public Vector2d sub(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    /**
     * Create a new vector, equal to this vector scaled by some scalar.
     *
     * @param scalar The scalar to scale by.
     * @return The new scaled vector.
     */
    public Vector2d scalar(double scalar) {
        return new Vector2d(this.x * scalar, this.y * scalar);
    }

    public void setYDir(int dir) {
        switch (dir) {
            case -1:
                this.y = -Math.abs(this.y);
                break;
            case 1:
                this.y = Math.abs(this.y);
                break;
        }
    }

    public void setXDir(int dir) {
        switch (dir) {
            case -1:
                this.x = -Math.abs(x);
                break;
            case 1:
                this.x = Math.abs(x);
        }
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
