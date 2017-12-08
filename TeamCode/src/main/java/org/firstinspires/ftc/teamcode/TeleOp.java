package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 144 lines
 * Human-Controlled Operation program v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {
    private Robot robot = new Robot();

    @Override
    public void runOpMode() {
        double wristPos = 0;
        double armPos = 0.3;
        double gripperPos = 0.234;

        // Declare OpMode members.
        //final double GRIPPER_CLOSED = 0.75;
        //final double GRIPPER_RELIC_CLOSED = 0.89;
        //final double GRIPPER_OPEN = 0.59;

        final double armModify = 0.0008;
        final double gripperModify = 0.008;

        //final double ovr_wrist = 0.63;
        //final double und_wrist = 1;

      //  final double cls_gripper = 0.427;
        final double opn_gripper = 0;
        final double block_gripper = 0.43;

        final double und_arm = 0.2;
        final double ovr_arm = 0.7;

        robot.init(hardwareMap, this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.gripper(robot.gripperOnePosition);
        robot.arm(robot.armServoPosition);

        robot.gripperOneServo.setPosition(block_gripper);
        robot.armServo.setPosition(und_arm);

        // Wait for the game to start (driver presses PLAY)

        waitForStart();
        robot.runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (getRuntime() >= 120) {
                break;
            }

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            leftPower = gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;

            if(gamepad2.right_trigger > 0.5 || gamepad2.left_trigger > 0.5 && robot.gripperOneServo.getPosition() > 0.16) {
                if (gripperPos - gripperModify >= opn_gripper) {
                    gripperPos = robot.gripperOnePosition - gripperModify;
                }
            }

            if (gamepad2.right_bumper || gamepad2.left_bumper && robot.gripperOneServo.getPosition() < 0.586)  {
                    gripperPos = robot.gripperOneServo.getPosition() + gripperModify;
            }

            if(gamepad2.right_stick_y < -0.2 && armPos <= ovr_arm) {
                armPos += armModify;
            } else if (gamepad2.right_stick_y > 0.2 && armPos >= und_arm) {
                armPos -= armModify;
            }
            if(gamepad1.dpad_up) {
                leftPower = -0.3;
                rightPower = -0.3;
            } else if (gamepad1.dpad_down) {
                leftPower = 0.3;
                rightPower = 0.3;
            }

            if (gamepad1.a) {
                leftPower = 0.7;
                rightPower = 0.7;
            } else if (gamepad1.y) {
                leftPower = -0.7;
                rightPower = -0.7;
            }

            //wristPos = ((2 * armPos) - 0.12);

            /* Tank Mode uses one stick to control each wheel.
            Right Trigger sets the position of the servo to one that will hold the block
            Left Trigger sets the position of the servo to one that will hold the relic
             - This requires no math, but it is hard to drive forward slowly and keep straight*/

            // Send calculated power to wheels
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            robot.wrist(wristPos);
            robot.arm(armPos);
            robot.gripper(gripperPos);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Gripper", "Position: " + robot.gripperOneServo.getPosition());
            telemetry.addData("Wrist", "Position: " + robot.wristServo.getPosition());
            telemetry.addData("Arm", "Position: " + robot.armServo.getPosition());
            telemetry.addData("Arm", "armPos: " + armPos);
            telemetry.addData("A button", "Position: " + gamepad1.a);
            telemetry.addData("Left Joystick: ",  gamepad2.left_stick_y);
            telemetry.update();
        }
    }
}
