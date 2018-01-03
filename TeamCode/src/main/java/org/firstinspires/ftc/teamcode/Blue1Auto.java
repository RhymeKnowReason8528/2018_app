package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Blue 1")
public class Blue1Auto extends LinearOpMode {

    private Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, this);
        double currentRunTime = getRuntime();

        waitForStart();

        robot.relicTrackables.activate();

        while (opModeIsActive()) {
            robot.wrist(0.62);

            robot.autoDrive(robot.inchesToTicks(24), -1);

            robot.gripper(0.20);
            robot.autoDrive(robot.inchesToTicks(3), 1);

            RelicRecoveryVuMark VuMark = robot.getVuMark();

            VuMark = RelicRecoveryVuMark.from(robot.relicTemplate);
            if (VuMark != RelicRecoveryVuMark.UNKNOWN) {
                robot.isVisibleVuMark = true;
                telemetry.addData("VuMark", "%s visible", VuMark);
            }

            currentRunTime = getRuntime();
            while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
            }

            robot.wrist(0.62);

            robot.autoDrive(robot.inchesToTicks(24), -1);

            robot.gripper(0.20);
            robot.autoDrive(robot.inchesToTicks(3), 1);

            telemetry.update();
        }
    }
}
