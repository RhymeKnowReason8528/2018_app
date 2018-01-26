package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Red 1")
public class Red1Auto extends LinearOpMode {

    private Robot robot = new Robot();

    private int red;
    private int blue;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();
        robot.enableGripper();

        blue = robot.getBlue(1);
        red = robot.getRed(1);

        robot.actOnBallColor(red, blue, 1);

        robot.moveGripperFullClosed();

        waitForStart();

        robot.moveGripperFullClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(30), -1);

        robot.autoOpen();

        robot.autoDrive(robot.inchesToTicks(3), 1);

    }
}
