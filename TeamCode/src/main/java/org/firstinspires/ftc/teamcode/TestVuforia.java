package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by Will on 2/9/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Vuforia Test")

public class TestVuforia extends LinearOpMode {

    Robot robot = new Robot();

    public void runOpMode() {
        robot.init(hardwareMap, this);
        robot.vuforiaInit(hardwareMap, "left");

        robot.relicTrackables.activate();

        RelicRecoveryVuMark vuMark;

        String displayVuMark = new String();

        boolean isVisibleVuMark;

        waitForStart();
        robot.runtime.reset();

        while (opModeIsActive()) {
            vuMark = RelicRecoveryVuMark.from(robot.relicTemplate);

            displayVuMark = vuMark.toString();

            isVisibleVuMark = true;

            if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                isVisibleVuMark = false;
            }

            telemetry.addData("Visible vuMark?  ", isVisibleVuMark);
            telemetry.addData("Visible vuMark: ", displayVuMark);
            telemetry.update();
        }
    }
}
