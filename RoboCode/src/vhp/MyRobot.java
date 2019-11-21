package vhp;
import robocode.*;
import java.awt.Color;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyRobot - a robot by (your name here)
 */
public class MyRobot extends TeamRobot
{
	/**
	 * run: MyRobot's default behavior
	 */
	
	double bearing = 0;
	double enemyHeading = 0;
	
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.red,Color.blue,Color.white); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			//turnLeft(20);
			turnRadarRight(360);
			
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent event) {
		// Replace the next line with any behavior you would like
		bearing = event.getBearing();
		if(bearing >= 800) {
			turnGunRight(bearing);
			fire(1);
		}else if(bearing >= 500) {
			turnGunRight(bearing);
			fire(2);
		}else {
			turnGunRight(bearing);
			fire(3);
		}
		turnRight(bearing);
		turnRight(90);
		ahead(150);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent event) {
		// Replace the next line with any behavior you would like
		enemyHeading = event.getHeading();
		turnGunLeft(enemyHeading);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent event) {
		// Replace the next line with any behavior you would like
		back(100);
		turnRight(180);
		fire(3);
	}	
}
