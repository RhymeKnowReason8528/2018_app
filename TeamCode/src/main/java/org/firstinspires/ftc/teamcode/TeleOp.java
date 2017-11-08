package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/* 144 lines
 * Human-Controlled Operation program v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {


    private Robot robot = new Robot();

    @Override
    public void runOpMode() {

        // Declare OpMode members.
        final double GRIPPER_CLOSED = 0.75;
        final double GRIPPER_RELIC_CLOSED = 0.89;
        final double GRIPPER_OPEN = 0.59;
        double wristPos = 0.5;

        /*safes
        final double ovr_arm = 0.1;
        final double und_arm = 0.1;

        final double ovr_gripper = 0.1;
        final double und_gripper = 0.1;*/

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.gripperOneServo.setPosition(GRIPPER_OPEN);
        robot.armServo.setPosition(robot.armServo.getPosition());

        robot.leftDrive.setDirection(DcMotor.Direction.REVERSE);
        robot.rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)

        waitForStart();
        robot.runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            leftPower = gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;

            if(gamepad1.right_trigger > 0.5) {
                robot.gripperOneServo.setPosition(GRIPPER_CLOSED);
            }
            if(gamepad1.left_trigger > 0.5) {
                robot.gripperOneServo.setPosition(GRIPPER_RELIC_CLOSED);
            }
            if (gamepad1.right_bumper || gamepad1.left_bumper)  {
                robot.gripperOneServo.setPosition(GRIPPER_OPEN);
            }

            if(gamepad1.a) {
                robot.armServo.setDirection(Servo.Direction.FORWARD);
            } else if (gamepad1.b) {
                robot.armServo.setDirection(Servo.Direction.REVERSE);
            } else {
                robot.armServo.setPosition(robot.armServo.getPosition());
            }

            if(gamepad1.x) {
                wristPos += 0.005;
            } else if (gamepad1.y) {
                wristPos -= 0.005;
            }

            if (gamepad1.dpad_up) {
                leftPower = -0.3;
                rightPower = -0.3;
            } else if (gamepad1.dpad_down) {
                leftPower = 0.3;
                rightPower = 0.3;
            }

            /* Tank Mode uses one stick to control each wheel.
            Right Trigger sets the position of the servo to one that will hold the block
            Left Trigger sets the position of the servo to one that will hold the relic
             - This requires no math, but it is hard to drive forward slowly and keep straight*/

            // Send calculated power to wheels
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            robot.wristServo.setPosition(wristPos);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.update();
        }
    }
}
