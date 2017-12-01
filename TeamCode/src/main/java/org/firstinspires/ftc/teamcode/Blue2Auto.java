package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Blue 2")
public class Blue2Auto extends LinearOpMode {

    private Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();

        waitForStart();

        robot.gripperOneServo.setPosition(0.50);

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.driveForward(robot.inchesToTicks(24), -1);
        robot.turn(2100, -1);
        robot.wristServo.setPosition(0.62);

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.driveForward(robot.inchesToTicks(5), -1);

        robot.gripperOneServo.setPosition(0.20);
        robot.driveForward(robot.inchesToTicks(3), 1);

        while (opModeIsActive()) {
        }
    }
}
