package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="ServoTest")
@Disabled
public class ServoTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Robot robot = new Robot();
        double gripperPos = 0.7;
        double armPos = 0.6;

        robot.init(hardwareMap, this);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                gripperPos += 0.01;
            }
            else if(gamepad1.left_bumper) {
                gripperPos -= 0.01;
            }

            if (gamepad1.a){
                armPos += 0.001;
            }
            else if(gamepad1.b) {
                armPos -= 0.001;
            } else {
                armPos += 0;
            }


            robot.gripperOneServo.setPosition(gripperPos);
            robot.armServo.setPosition(armPos);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("ArmPos", "Arm Position: " + robot.armServo.getPosition());
            telemetry.addData("GripperPos", "Gripper Position: " + robot.gripperOneServo.getPosition());
            telemetry.update();
        }
    }
}
