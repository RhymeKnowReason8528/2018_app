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

        waitForStart();

        //robot.gripper(0.50);
        robot.moveGripperFullClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(24), -1);

        //robot.gripper(0.20);
//
        robot.autoDrive(robot.inchesToTicks(3), 1);

        while (opModeIsActive()) {
        }
    }
}
