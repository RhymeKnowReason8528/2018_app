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

    private DcMotor leftDrive = robot.leftDrive;
    private DcMotor rightDrive = robot.rightDrive;

    private double inchesToTicks(double inches) {
        double rotation = 1440;
        double circumference = 12.5663706;

        double go = inches / circumference;
        return (go * rotation);
    }

    @Override
    public void runOpMode() {
        double leftPower = 0.75;
        double rightPower = 0.75;

        waitForStart();

        while (opModeIsActive()) {
            if (getRuntime() >= 29.5) {
                break;
            }

            boolean complete = false;

            while (!complete) {
                if (leftDrive.getCurrentPosition() < inchesToTicks(35)) {
                    leftDrive.setPower(leftPower);
                    rightDrive.setPower(rightPower);
                }
                if (rightDrive.getCurrentPosition() < inchesToTicks(35)) {
                    leftDrive.setPower(rightPower);
                    rightDrive.setPower(rightPower);
                }
                if (leftDrive.getCurrentPosition() >= inchesToTicks(leftPower) && rightDrive.getCurrentPosition() >= inchesToTicks(35)) {
                    complete = true;
                }
            }
        }
    }
}
