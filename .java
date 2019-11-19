// hi due december 10 
// Carson's Code
package CW;
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * CWRobot - a robot by Carson 
 */
public class CWRobot extends TeamRobot
{
	/**
	 * run: CWRobot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.red,Color.blue,Color.white); // body,gun,radar

		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(200);
			turnGunLeft(90);
			ahead(100);
			//setTurnRight(100);
			//turnGunLeft(480);
			//setTurnLeft(100);
			//turnGunRight(360);
			//ahead(100);
			//turnGunRight(360);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		fire(1);
		fire(5);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}
	
	//Bullet bullet = null;

	//public void setFireBullet(double power){
		//if (getGunHeat() == 0) {
			//bullet = setFireBullet(Rules.MAX_BULLET_POWER);
				
		//execute();
		//if (bullet != null) {
			//double bulletVelocity = bullet.getVelocity();
		
	
	
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
		
	}	
}





