package bouncing_balls;

/**
 * The physics model.
 * 
 * This class is where you should implement your bouncing balls model.
 * 
 * The code has intentionally been kept as simple as possible, but if you wish, you can improve the design.
 * 
 * @author Simon Robillard
 *
 */
class Model {

    final double acc_gravity = 9.82;
	double areaWidth, areaHeight;
    final double startEnergy;
    int counter = 0;

	Ball [] balls;

	Model(double width, double height) {
		areaWidth = width;
		areaHeight = height;
		
		// Initialize the model with a few balls
		balls = new Ball[2];
		balls[0] = new Ball(width / 3, height * 0.9, 1.2, 1.6, 0.2, 1);
		balls[1] = new Ball(2 * width / 3, height * 0.7, -0.6, 0.6, 0.3, 1);

        //initialize the start energy to keep track of energy differences in the system
        startEnergy =  balls[0].getMechanicalEnergy() + balls[1].getMechanicalEnergy();
	}

	void step(double deltaT) {
        showEnergyDiff();
        if(areBallsColliding()){
            ballCollision(balls[0], balls[1]);
        }

		for (Ball b: balls) {
            // Do not apply gravity if the ball collided with the top or bottom. If we do not do this, we get a net
            // increase/decrease in energy, respectively.
            if(!checkAndHandleCollisionWithEdge(b)) {
                b.velocity = b.velocity.sub(new Vector2d(0, acc_gravity*deltaT));
            }
		}
        moveBalls(deltaT);
	}

    /**
     * Check if the balls are colliding, i.e. the distance between their centers is less than ot equal to the sum of
     * their radii.
     *
     * @return if they collide.
     */
    boolean areBallsColliding(){
        Vector2d distance = balls[0].position.sub(balls[1].position);
        return distance.getMagnitude() <= balls[0].radius + balls[1].radius;
    }

    void showEnergyDiff(){
        double currentEnergy = getCurrentEnergy();
        if (currentEnergy != startEnergy) {
            counter += 1;
            if (counter >= 5) {
                System.out.println(currentEnergy - startEnergy);
            }
        }
    }
    double getCurrentEnergy(){
        return balls[0].getMechanicalEnergy() + balls[1].getMechanicalEnergy();
    }
    void collisoinWithBorderEvent(Ball b){
        // detect collision with the border
        if (b.position.getX() < b.radius) {
            b.velocity.setXDir(1);
            return false;
        }
        if (b.position.getX() > areaWidth - b.radius) {
            b.velocity.setXDir(-1);
            return false;
        }
        if (b.position.getY() < b.radius) {
            b.velocity.setYDir(1);
            return true;
        }
    }
    void moveBalls(double deltaT){
        for (Ball b : balls) {
        // compute new position according to the speed of the ball
            b.position.add_inplace(b.velocity.scalar(deltaT));
        }
    }

    void ballCollision(Ball ball1, Ball ball2) {
        Vector2d u1 = ball1.velocity;
        Vector2d u2 = ball2.velocity;
        double totMass = ball1.mass + ball2.mass;

        ball1.velocity = u1
                .scalar((ball1.mass - ball2.mass) / totMass)
                .add(u2
                        .scalar(2 * ball2.mass / totMass));

        ball2.velocity = u1
                .scalar(2 * ball1.mass / totMass)
                .add(u2
                        .scalar((ball2.mass - ball1.mass) / totMass));
    }
	
	/**
	 * Simple inner class describing balls.
	 */
	class Ball {
        /**
         * Position and velocities of the ball.
         */
        Vector2d position, velocity;

        /**
         * Radius and mass of the ball.
         */
        double radius, mass;

		Ball(double x, double y, double vx, double vy, double r, double mass) {
            position = new Vector2d(x, y);
            velocity = new Vector2d(vx, vy);
			this.radius = r;
            this.mass = mass;
		}

        /**
         * Computes the mechanical energy of the ball, i.e. the sum of kinetic and potential energy.
         *
         * @return the mechanical energy.
         */
        double getMechanicalEnergy() {
            return 0.5 * mass * velocity.getMagnitude() * velocity.getMagnitude()
                 + mass * position.getY() * acc_gravity;
        }
	}
}
