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
        startEnergy =  0.5 * (Math.pow(balls[0].vx, 2) + Math.pow(balls[0].vy, 2)) * balls[0].mass +
                       0.5 * (Math.pow(balls[1].vx, 2) + Math.pow(balls[1].vy, 2)) * balls[1].mass;
	}

	void step(double deltaT) {
		// TODO this method implements one step of simulation with a step deltaT
        double distanceX = Math.pow((balls[0].x - balls[1].x), 2);
        double distanceY = Math.pow((balls[0].y - balls[1].y), 2);

        double ball0Energy = (Math.pow(balls[0].vx,2) + Math.pow(balls[0].vy,2)) * balls[0].mass * 1 / 2
                + balls[0].mass * balls[0].y * acc_gravity;
        double ball1Energy = (Math.pow(balls[1].vx,2) + Math.pow(balls[1].vy,2))* balls[1].mass * 1 / 2
                + balls[1].mass * balls[1].y * acc_gravity;

        double currentEnergy =  ball0Energy + ball1Energy;

        if (currentEnergy != startEnergy) {
            counter += 1;
            if (counter >= 5) {
                System.out.println(currentEnergy - startEnergy);
            }
        }

        if (distanceX + distanceY <= Math.pow(balls[0].radius + balls[1].radius, 2)) {
            ballCollision(balls[0], balls[1]);
        }

		for (Ball b : balls) {
			// detect collision with the border
			if (b.x < b.radius) {
				b.vx *= -1; // change direction of ball
                b.x = b.radius;
			}
            if (b.x > areaWidth - b.radius) {
                b.vx *= -1; // change direction of ball
                b.x = areaWidth - b.radius;
            }
			if (b.y < b.radius) {
				b.vy *= -1;
                b.y = b.radius;
			}
            if (b.y > areaHeight - b.radius) {
                b.vy *= -1;
                b.y = areaHeight - b.radius;
            }
            //b.vy -= acc_gravity * deltaT / 2;
            b.vy -= acc_gravity * deltaT;

			// compute new position according to the speed of the ball
			b.x += deltaT * b.vx;
			b.y += deltaT * b.vy;
		}
	}

    void ballCollision(Ball ball1, Ball ball2) {
        double u1x = ball1.vx;
        double u1y = ball1.vy;
        double u2x = ball2.vx;
        double u2y = ball2.vy;

        ball1.vx = ((ball1.mass - ball2.mass)  * u1x + 2 * ball2.mass * u2x) / (ball1.mass + ball2.mass);
        ball1.vy = ((ball1.mass - ball2.mass)  * u1y + 2 * ball2.mass * u2y) / (ball1.mass + ball2.mass);

        ball2.vx = ((ball1.mass - ball2.mass)  * u1x + 2 * ball2.mass * u2x) / (ball1.mass + ball2.mass) - (u2x - u1x);
        ball2.vy = ((ball1.mass - ball2.mass)  * u1y + 2 * ball2.mass * u2y) / (ball1.mass + ball2.mass) - (u2y - u1y);
    }
	
	/**
	 * Simple inner class describing balls.
	 */
	class Ball {
		
		Ball(double x, double y, double vx, double vy, double r, double mass) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.radius = r;
            this.mass = mass;
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, radius, mass;
	}
}
