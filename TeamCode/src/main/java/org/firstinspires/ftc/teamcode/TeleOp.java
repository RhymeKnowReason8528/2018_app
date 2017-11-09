package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/* 144 lines
 * Human-Controlled Operation program v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {


    private Robot robot = new Robot();

    @Override
    public void runOpMode() {
        double wristPos = 0.45;
        double armPos = 0.5;
        double gripperPos = 0;

        // Declare OpMode members.
        final double GRIPPER_CLOSED = 0.75;
        final double GRIPPER_RELIC_CLOSED = 0.89;
        final double GRIPPER_OPEN = 0.59;

        final double wristModify = 0.005;
        final double armModify = 0.001;
        final double gripperModify = 0.001;

        final double ovr_wrist = 0.63;
        final double und_wrist = 1;

        final double cls_gripper = 0.427;
        final double opn_gripper = 0;

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.gripperOneServo.setPosition(gripperPos);
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
                if (gripperPos - gripperModify >= opn_gripper) {
                    gripperPos -= gripperModify;
                }
            }

            if (gamepad1.right_bumper || gamepad1.left_bumper)  {
                if (gripperPos + gripperModify <= cls_gripper) {
                    gripperPos += gripperModify;
                }
            }

            if(gamepad1.a && armPos < 0.6) {
                armPos += armModify;
            } else if (gamepad1.b && armPos > 0.3) {
                armPos -= armModify;
            }

            if(gamepad1.x) {
                wristPos += wristModify;
            } else if (gamepad1.y) {
                wristPos -= wristModify;
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
            robot.armServo.setPosition(armPos);
            robot.gripperOneServo.setPosition(gripperPos);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Gripper", "Position: " + robot.gripperOneServo.getPosition());
            telemetry.addData("Wrist", "Position: " + robot.wristServo.getPosition());
            telemetry.addData("Arm", "Position: " + robot.armServo.getPosition());
            telemetry.addData("Arm", "armPos: " + armPos);
            telemetry.addData("A button", "Position: " + gamepad1.a);
            telemetry.update();
        }
    }
}
