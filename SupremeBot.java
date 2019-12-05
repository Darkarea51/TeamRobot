package sample;


import robocode.HitRobotEvent;
import robocode.TeamRobot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;



public class SupremeBot extends TeamRobot {
	int count = 0; // Keeps track of how long we've
	// been searching for our target
	double gunTurnAmt; // How much to turn our gun when searching
	String trackName; // Name of the robot we're currently tracking

	/**
	 * run:  SupremeBot's main run function
	 */
	public void run() {
		// Set colors
		setColors(Color.red, Color.blue, Color.white);

		// Prepare gun
		trackName = null; // Initialize to not tracking anyone
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		gunTurnAmt = 10; // Initialize gunTurn to 10

		// Loop forever
		while (true) {
			// turn the Gun (looks for enemy)
			turnGunRight(gunTurnAmt);
			// Keep track of how long we've been looking
			count++;
			// If we've haven't seen our target for 2 turns, look left
			if (count > 2) {
				gunTurnAmt = -10;
			}
			// If we still haven't seen our target for 5 turns, look right
			if (count > 5) {
				gunTurnAmt = 10;
			}
			// If we *still* haven't seen our target after 10 turns, find another target
			if (count > 11) {
				trackName = null;
			}
		}
	}

	/**
	 * onScannedRobot:  Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			return;
		}
		else {
			fire(3);
			// If we have a target, and this isn't it, return immediately
			// so we can get more ScannedRobotEvents.
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
			if (e.getDistance() > 50) {
				gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
	
				turnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
				turnRight(e.getBearing()); // and see how much SupremeBot improves...
				// (you'll have to make SupremeBot an AdvancedRobot)
				if (e.getDistance() < 200) {
					ahead(e.getDistance());
				}
				else if (e.getDistance() > 200) {
					ahead(e.getDistance() / 2);
				}
				return;
			}
		}

		// Our target is close.
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		fire(3);

		
		scan();
	}

	/**
	 * onHitRobot:  Set him as our new target
	 */
	public void onHitRobot(HitRobotEvent e) {
		// Only print if he's not already our target.
		if (trackName != null && !trackName.equals(e.getName())) {
			out.println("Tracking " + e.getName() + " due to collision");
		}
		// Set the target
		trackName = e.getName();
		// Back up a bit.
		back(10);
		// Note:  We won't get scan events while we're doing this!
		// An AdvancedRobot might use setBack(); execute();
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		fire(3);
	}

	/**
	 * onWin:  Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
}

