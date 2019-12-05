package vp;
import robocode.*;
import java.awt.Color;
import robocode.TeamRobot;
import robocode.ScannedRobotEvent;
import robocode.HitRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.geom.Point2D;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * CWRobot - a robot by Carson 
 */
public class CWRobot extends TeamRobot
{	
	boolean movingForward;
	int count = 0; 
	double gunTurnAmt;
	String trackName;
	/**
	 * run: CWRobot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		trackName = null;
		setAdjustGunForRobotTurn(true);
		gunTurnAmt = 10;
		setColors(Color.red,Color.blue,Color.white); // body,gun,radar
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			setAhead(40000);
			movingForward = true;
			setTurnRight(90);
			turnGunRight(gunTurnAmt);
			count++;
			if (count > 2) {
				gunTurnAmt = -10;
			}
			if (count > 5) {
				gunTurnAmt = 10;
			}
			if (count > 11){
				trackName = null;
			}
		}
	} 
	public void onHitRobot(HitRobotEvent e) {
		// Only print if he's not already our target.
		if (trackName != null && !trackName.equals(e.getName())) {
			out.println("Tracking " + e.getName() + " due to collision");
		}
		trackName = e.getName();
		fire(3);
		back(50);
	}

	public void onHitByBullet(HitByBulletEvent e) {
	
		reverseDirection(); // if hit by a bullet reverse from the place where the robot was hit.
	}
	public void onHitWall(HitWallEvent e) {
		reverseDirection(); // if hit by a wall, reverse from the wall. 
	}	
	
	public void reverseDirection() {
		if (movingForward) {
			setBack(40000);
			movingForward = false; 
		}else{
			setAhead(40000);
			movingForward = true;
		}
	}
	public void onScannedRobot(ScannedRobotEvent e){
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		
		if (isTeammate(e.getName())){
			return;
		}
		//If teammate is scanned return, do not fire upon teammate. 
		fire(3);
		
		if (Math.abs(bearingFromGun) <= 3){
			turnGunRight(bearingFromGun);
			if (getGunHeat() == 0){
				fire(Math.min(3- Math.abs(bearingFromGun),getEnergy() - .1));
			}
		}
		else {
			turnGunRight(bearingFromGun);
		}
		if (bearingFromGun == 0) {
			scan();
		}
		if (trackName != null && !e.getName().equals(trackName)) {
			return;
		}
		// If we don't have a target, well, now we do!
		if (trackName == null) {
			trackName = e.getName();
			out.println("Tracking " + trackName);
		}
		// This is our target.  Reset count (see the run method)
		count = 0;
		// If our target is too far away, turn and move toward it.
		if (e.getDistance() > 150) {
			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

			setTurnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
			turnRight(e.getBearing()); // and see how much Tracker improves...
			ahead(e.getDistance() - 140);
			return;
		}
		// Prints out the following values for Gun Heading, Bearing, Heading, and Radar Heading
		System.out.println("Gun Heading: " + getGunHeading());
		System.out.println("Bearing: " + e.getBearing());
		System.out.println("Heading: " +getHeading());
		System.out.println("Radar Heading: " + getRadarHeading());
	

		// Get distance of the target relative to the robot and backs up if its too close.
		if (e.getDistance() < 100) {
			if (e.getBearing() > -90 && e.getBearing() <= 90) {
				back(40);
			} else {
				ahead(40);
			}
		}
		scan();
	}
}
