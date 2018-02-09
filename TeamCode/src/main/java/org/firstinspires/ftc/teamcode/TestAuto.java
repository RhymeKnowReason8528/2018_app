package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Will on 1/26/2018.
 */


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TestAuto")

public class TestAuto extends LinearOpMode {

    private Robot robot = new Robot();

    public void runOpMode() {

        robot.init(hardwareMap, this);

        waitForStart();
        robot.runtime.reset();

//        robot.autoDrive(robot.inchesToTicks(5), 1);
        robot.autoDrive(robot.inchesToTicks(5), -1);
//        robot.autoDrive(robot.inchesToTicks(5), 1);
//        robot.autoDrive(robot.inchesToTicks(5), -1);

        while (opModeIsActive()) {

            telemetry.addData("Right Motor", "Encoder Return" + robot.rightDrive.getCurrentPosition());
            telemetry.addData("Left Motor", "Encoder Return" + robot.leftDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}
