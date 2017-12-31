package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/* 124 lines
 * Human-Controlled Operation program v 1.1 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {
    private Robot robot = new Robot();

    @Override
    public void runOpMode() {

//--------------Declare some variables--------------

        double armPos = 0.3;
        double gripperPos = 0.234;

        final double ARM_MODIFY = 0.0008;
        final double GRIPPER_MODIFY = 0.001;

        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

//--------------Initialize the robot----------------

        robot.init(hardwareMap, this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.gripper(robot.gripperOneServo.getPosition());
        robot.arm(robot.armServo.getPosition());

        robot.gripperOneServo.setPwmEnable();

//--------Wait for the driver to press play---------

        waitForStart(); //This is when the driver presses the button
        robot.runtime.reset();

//------------------Start the loop------------------

        while (opModeIsActive()) {// run until the end of the match (driver presses STOP)

            leftPower = gamepad1.left_stick_y;
            rightPower = gamepad1.right_stick_y;

//-------------------Wheel control-------------------

            //Main joystick drive
            if(gamepad2.right_stick_y < -0.2) {
                armPos += ARM_MODIFY;
            } else if (gamepad2.right_stick_y > 0.2) {
                armPos -= ARM_MODIFY;
            }

            //Drive for 3/4 power
            if (gamepad1.a) {
                leftPower = 0.7;
                rightPower = 0.7;
            } else if (gamepad1.y) {
                leftPower = -0.7;
                rightPower = -0.7;
            }

            //Drive for 1/3 power
            if(gamepad1.dpad_up) {
                leftPower = -0.3;
                rightPower = -0.3;
            } else if (gamepad1.dpad_down) {
                leftPower = 0.3;
                rightPower = 0.3;
            }

//------------------Gripper Control-----------------

            //Open the gripper
            if (gamepad2.right_bumper && robot.gripperOneServo.getPosition() < 0.586 && robot.gripperOneServo.isPwmEnabled() && gripperPos + GRIPPER_MODIFY <= 1)  {
                gripperPos = robot.gripperOneServo.getPosition() + GRIPPER_MODIFY;
            }

            //Close the gripper
            if(gamepad2.right_trigger > 0.5 && robot.gripperOneServo.getPosition() > 0.16 && robot.gripperOneServo.isPwmEnabled() && gripperPos - GRIPPER_MODIFY >= 0) {
                gripperPos = robot.gripperOneServo.getPosition() - GRIPPER_MODIFY;
            }

            //Allow the driver to manually turn off the servo (to prevent burning it out during a match)
            if(gamepad2.x) {
                robot.gripperOneServo.setPwmDisable();
            }
            if(gamepad2.y) {
                robot.gripperOneServo.setPwmEnable();
            }

//-------------------Calculations-------------------

            // Send calculated power to wheels and servos
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            robot.arm(armPos);

            if(robot.gripperOneServo.isPwmEnabled()) {
                robot.gripper(gripperPos);
            }

//------------------Telemetry-----------------

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Gripper", "Position: " + robot.gripperOneServo.getPosition());
            telemetry.addData("Arm", "Position: " + robot.armServo.getPosition());
            telemetry.addData("is pwm enabled: ", robot.gripperOneServo.isPwmEnabled());
            telemetry.update();
        }
    }
}
