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
        robot.moveGripperFullClosed();

        waitForStart();

        //double speed;

        //speed = robot.getJewelSpeed(side);
        //robot.autoDrive(robot.inchesToTicks(1), speed);
        //sleep(8000);

        robot.moveGripperFullClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(24), -1);
        robot.autoTurn(1800, 1);
        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(10), -1);

        robot.autoOpen();

        robot.autoDrive(robot.inchesToTicks(3), 1);

    }
}
