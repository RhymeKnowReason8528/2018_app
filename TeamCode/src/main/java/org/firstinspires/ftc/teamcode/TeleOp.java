package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.Servo;

/* 144 lines
 * Human-Controlled Operation program v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Linear Opmode") 
public class TeleOp extends LinearOpMode {


    private Robot robot = new Robot();

    @Override
    public void runOpMode() {

        // Declare OpMode members.
        Servo gripperOneServo = null;
        Servo armServo = null;

        final double GRIPPER_CLOSED = 0.72;
        final double GRIPPER_RELIC_CLOSED = 0.87;
        final double GRIPPER_OPEN = 0.59;

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        gripperOneServo.setPosition(GRIPPER_OPEN);

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
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
                gripperOneServo.setPosition(GRIPPER_CLOSED);
            }
            if(gamepad1.left_trigger > 0.5) {
                gripperOneServo.setPosition(GRIPPER_RELIC_CLOSED);
            }
            if (gamepad1.right_bumper || gamepad1.left_bumper)  {
                gripperOneServo.setPosition(GRIPPER_OPEN);
            }

            if(gamepad1.a) {
                armServo.setDirection(Servo.Direction.REVERSE);
            } else if (gamepad1.y) {
                armServo.setDirection(Servo.Direction.FORWARD);
            } else {
                armServo.setPosition(armServo.getPosition());
            }

            /* Tank Mode uses one stick to control each wheel.
            Right Trigger sets the position of the servo to one that will hold the block
            Left Trigger sets the position of the servo to one that will hold the relic
             - This requires no math, but it is hard to drive forward slowly and keep straight*/

            // Send calculated power to wheels
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Servos", "gripper_one_closed (%)", (gripperOneServo.getPosition() == GRIPPER_CLOSED));
            telemetry.addData("Arm", "arm_position (%)", (armServo.getPosition()));
            telemetry.update();
        }
    }
}
