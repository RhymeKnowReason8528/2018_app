package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Red 2")
public class Red2Auto extends LinearOpMode {

    private Robot robot = new Robot();

    private int red;
    private int blue;

    int side = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();

        blue = robot.getBlue(1);
        red = robot.getRed(1);

        robot.enableGripper();
        robot.moveGripperClosed();

        waitForStart();

        double speed;

        speed = robot.getJewelSpeed(side);
        robot.autoDrive(robot.inchesToTicks(2), speed);
        sleep(500);
        robot.jewelT1Retract();
        sleep(2000);


        robot.moveGripperClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(30), -1);
        robot.autoTurn(1800, 1);
        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(10), -1);

        robot.autoOpen();
        sleep(1000);
        robot.autoDrive(robot.inchesToTicks(3), 1);

    }
}
