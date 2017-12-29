package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Blue 2")
public class Blue2Auto extends LinearOpMode {

    private Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.getVuforiaKey();
        final String KEY = robot.KEY;

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();

        waitForStart();

        robot.gripper(0.50);

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(24), -1);
        robot.autoTurn(2100, -1);

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(5), -1);

        robot.gripper(0.20);
        robot.autoDrive(robot.inchesToTicks(3), 1);

        while (opModeIsActive()) {
        }
    }
}
