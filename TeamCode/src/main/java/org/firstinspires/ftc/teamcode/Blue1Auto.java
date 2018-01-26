package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Blue 1")
public class Blue1Auto extends LinearOpMode {

    private Robot robot = new Robot();

    /*private int red;
    private int blue;*/

    @Override
    public void runOpMode() throws InterruptedException {
        robot.getVuforiaKey();
        final String KEY = robot.KEY;

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();

        /*blue = robot.getBlue(2);
        red = robot.getRed(2);

        robot.actOnBallColor(red, blue, 2);*/

        robot.enableGripper();
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
