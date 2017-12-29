package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 124 lines
 * Human-Controlled Operation program v 1.1 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {
    private Robot robot = new Robot();

    @Override
    public void runOpMode() {
        double armPos = 0.3;
        double gripperPos = 0.234;

        // Declare OpMode members.
        //final double GRIPPER_CLOSED = 0.75;
        //final double GRIPPER_RELIC_CLOSED = 0.89;
        //final double GRIPPER_OPEN = 0.59;

        final double armModify = 0.0008;
        final double gripperModify = 0.001;

        //final double ovr_wrist = 0.63;
        //final double und_wrist = 1;

        //final double cls_gripper = 0.427;
        final double opn_gripper = 0;
        final double block_gripper = 0.43;

        boolean isPwmEnabled = true;

        //final double und_arm = 0.2;
        //final double ovr_arm = 0.7;

        robot.init(hardwareMap, this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.gripper(robot.gripperOneServo.getPosition());
        robot.arm(robot.armServo.getPosition());

        robot.gripperOneServo.setPosition(block_gripper);
        //robot.armServo.setPosition(und_arm);
        // Wait for the game to start (driver presses PLAY)

        //robot.gripperOneServo.setPwmEnable();
        //robot.armServo.setPwmEnable();

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

            if(gamepad2.right_trigger > 0.5 || gamepad2.left_trigger > 0.5 && robot.gripperOneServo.getPosition() > 0.16 && robot.gripperOneServo.isPwmEnabled()) {
                if (gripperPos - gripperModify >= opn_gripper) {
                    gripperPos = robot.gripperOneServo.getPosition() - gripperModify;
                }
            }

            if (gamepad2.right_bumper || gamepad2.left_bumper && robot.gripperOneServo.getPosition() < 0.586 && robot.gripperOneServo.isPwmEnabled())  {
                    gripperPos = robot.gripperOneServo.getPosition() + gripperModify;
            }

            if(gamepad2.right_stick_y < -0.2 /*&& armPos <= ovr_arm*/) {
                armPos += armModify;
            } else if (gamepad2.right_stick_y > 0.2 /*&& armPos >= und_arm*/) {
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

            if(gamepad2.x) {
                robot.gripperOneServo.setPwmDisable();
            }
            if(gamepad2.y) {
                robot.gripperOneServo.setPwmEnable();
            }

            /* Tank Mode uses one stick to control each wheel.
            Right Trigger sets the position of the servo to one that will hold the block
            Left Trigger sets the position of the servo to one that will hold the relic
             - This requires no math, but it is hard to drive forward slowly and keep straight*/

            // Send calculated power to wheels and servos
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            robot.arm(armPos);

            if(robot.gripperOneServo.isPwmEnabled()) {
                robot.gripper(gripperPos);
            }


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Gripper", "Position: " + robot.gripperOneServo.getPosition());
            telemetry.addData("Arm", "Position: " + robot.armServo.getPosition());
            telemetry.addData("Arm", "armPos: " + armPos);
            telemetry.addData("is pwm enabled: ", robot.gripperOneServo.isPwmEnabled());
            telemetry.update();
        }
    }
}
