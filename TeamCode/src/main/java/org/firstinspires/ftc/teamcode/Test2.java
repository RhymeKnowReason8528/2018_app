package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Will on 1/26/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Test2")

public class Test2 extends LinearOpMode {

    private Robot robot = new Robot();

    public void runOpMode() {

        robot.init(hardwareMap, this);

        waitForStart();
        robot.runtime.reset();

        while (opModeIsActive()) {

            telemetry.addData("Right Motor", "Encoder Return" + robot.rightDrive.getCurrentPosition());
            telemetry.addData("Left Motor", "Encoder Return" + robot.leftDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}
