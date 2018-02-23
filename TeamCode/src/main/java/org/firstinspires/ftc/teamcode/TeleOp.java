package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.Robot.GripperState.CLOSED;
import static org.firstinspires.ftc.teamcode.Robot.GripperState.MIDDLE;
import static org.firstinspires.ftc.teamcode.Robot.GripperState.MAX_OPEN;
/* 124 lines
 * Human-Controlled Operation program v 1.1 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode {
    private Robot robot = new Robot();

    @Override
    public void runOpMode() {

//--------------Declare some variables--------------

        // Set up a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        double armPos = 0.52;
        double relicExtensionPos = 0.5;
        double relicGrabberPos = 0.5;

        final double ARM_MODIFY = 0.002;

//--------------Initialize the robot----------------

        robot.init(hardwareMap, this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot.enableGripper();

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
            if (gamepad1.dpad_up) {
                leftPower = -0.3;
                rightPower = -0.3;
            } else if (gamepad1.dpad_down) {
                leftPower = 0.3;
                rightPower = 0.3;
            }
//-----------------Relic Arm Control----------------

            if (gamepad2.left_stick_y > 0.2) {
                robot.linearSlideMotor.setPower(1);
            } else if (gamepad2.left_stick_y < -0.2) {
                robot.linearSlideMotor.setPower(-1);
            } else {
                robot.linearSlideMotor.setPower(0);
            }

            if(gamepad2.left_bumper) {
                relicGrabberPos += 0.01;
            } else if (gamepad2.left_trigger > 0.5) {
                relicGrabberPos -= 0.01;
            } else {
                relicExtensionPos -= 0;
            }

            if(gamepad2.x) {
                relicExtensionPos += 0.01;
            } else if (gamepad2.b) {
                relicExtensionPos -= 0.01;
            } else {
                relicExtensionPos -= 0;
            }

//------------------Gripper Control-----------------

            if (robot.isGripperDisabled() == false) {
                if (robot.getGripperState() != MIDDLE) {
                    robot.gripperOneMotor.setPower(0);
                }
                if (gamepad2.right_bumper)  {
                    robot.moveGripperClosed();
                } else if (gamepad2.right_trigger > 0.5) {
                    robot.moveGripperOpen();
                } else {
                    robot.gripperOneMotor.setPower(0);
                }

            } else {
                robot.gripperOneMotor.setPower(0);
            }

            //Allow the driver to manually turn off the servo (to prevent burning it out during a match)
            if(gamepad2.x) {
                robot.disableGripper();
            }
            if(gamepad2.y) {
                robot.enableGripper();
            }

//-------------------Calculations-------------------

            // Send calculated power to wheels and servos
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            robot.arm(armPos);
            robot.linearExtensionServo.setPosition(relicExtensionPos);
            robot.relicServo.setPosition(relicGrabberPos);

//------------------Telemetry-----------------

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Gripper state: ", robot.getGripperState().toString());
            telemetry.addData("touch sensor 1: ", robot.touchSensor1.getState());
            telemetry.addData("touch sensor 2", robot.touchSensor2.getState());
            telemetry.addData("is gripper disabled", robot.isGripperDisabled());
            telemetry.addData("arm ", robot.armServo.getPosition());
            telemetry.addData("extension ", robot.linearExtensionServo.getPosition());
            telemetry.addData("relic grabber ", robot.relicServo.getPosition());
            telemetry.update();
        }
    }
}
