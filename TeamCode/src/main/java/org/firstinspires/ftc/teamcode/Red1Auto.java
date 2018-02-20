package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Red 1")
public class Red1Auto extends LinearOpMode {

    private Robot robot = new Robot();

    private int red;
    private int blue;

    int side = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();
        robot.enableGripper();

        blue = robot.getBlue(1);
        red = robot.getRed(1);

        robot.moveGripperClosed();

        waitForStart();

        /*double speed;

        speed = robot.getJewelSpeed(side);
        robot.autoDrive(robot.inchesToTicks(1), speed);
        sleep(2000);*/

        robot.moveGripperClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(30), -1);

        robot.autoDrive(robot.inchesToTicks(3), 1);

    }
}
