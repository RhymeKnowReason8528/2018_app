package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="RKR Autonomous")
public class Autonomous extends LinearOpMode {

    private Robot robot = new Robot();

    /*Positions 1 and 2 are the red alliance
    Positions 3 and 4 are the blue alliance

    Position 1 is the left red from blue's perspective
    Position 2 is the right red from blue's perspective

    Position 3 is the left blue from blue's perspective
    Position 4 is the right blue from blue's perspective */


    //final double position = 1;

    private double inchesToTicks(double inches) {
        double rotation = 1440;
        double circumference = 12.5663706;

        double go = inches / circumference;
        return (go * rotation);
    }

    @Override
    public void runOpMode() {
        robot.leftDrive.setDirection(DcMotor.Direction.FORWARD);
        robot.rightDrive.setDirection(DcMotor.Direction.REVERSE);

        double left_drive_position;
        double right_drive_position;

        double leftPower = 0.75;
        double rightPower = 0.75;

        waitForStart();
        robot.runtime.reset();

        while (opModeIsActive()) {
            if (getRuntime() >= 29.5) {
                break;
            }

            boolean complete = false;

            while (!complete) {
                left_drive_position = robot.leftDrive.getCurrentPosition();
                right_drive_position = robot.rightDrive.getCurrentPosition();

                if (left_drive_position < inchesToTicks(35)) {
                    robot.leftDrive.setPower(leftPower);
                }
                if (right_drive_position < inchesToTicks(35)) {
                    robot.leftDrive.setPower(rightPower);
                }
                if (left_drive_position >= inchesToTicks(leftPower) && right_drive_position >= inchesToTicks(35)) {
                    complete = true;
                }
            }
        }
    }
}
