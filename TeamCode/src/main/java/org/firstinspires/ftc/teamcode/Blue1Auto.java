package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Blue 1")
public class Blue1Auto extends LinearOpMode {

    private Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.getVuforiaKey();
        final String KEY = robot.KEY;

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();

        int side = 2;

        robot.enableGripper();
        robot.moveGripperClosed();

        waitForStart();

        double speed;

        speed = robot.getJewelSpeed(side);
        sleep(500);
        robot.autoDrive(robot.inchesToTicks(2), speed);
        sleep(2000);

        robot.moveGripperClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoTurn(2000, 1);
        robot.autoDrive(robot.inchesToTicks(30), -1);

        robot.autoOpen();
        sleep(100);
        robot.autoDrive(robot.inchesToTicks(3), 1);

    }
}
