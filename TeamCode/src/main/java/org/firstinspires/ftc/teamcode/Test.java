package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Will on 1/19/2018.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Test")

public class Test extends LinearOpMode{
    private Robot robot = new Robot();

    public void runOpMode() {

        robot.init(hardwareMap, this);

        int red;
        int blue;
        int green;

        String bc = new String();

        int bcNum;

        robot.jewelT1Retract();
//-------------------------------Wait for driver to press "play"-----------------------------------------------------
        waitForStart();
        robot.runtime.reset();
//-------------------------------After driver presses "play"---------------------------------------------------------

        robot.jewelT1Extend();

        bc = "No Ball";

        red = robot.getRed(1);
        blue = robot.getBlue(1);
        green = robot.getGreen(1);

        bcNum = robot.getBallColor(red, blue);

        double speed;

        speed = robot.getJewelSpeed(1);
        robot.autoDrive(robot.inchesToTicks(1), speed);
        robot.ledDisable();

        while (opModeIsActive()) {
            //1 is red, 2 is blue

            bc = "No Ball";

            red = robot.getRed(1);
            blue = robot.getBlue(1);
            green = robot.getGreen(1);

            bcNum = robot.getBallColor(red, blue);

            if (bcNum == 1) {
                bc = "Red";
            } else if (bcNum == 2) {
                bc = "Blue";
            }

            telemetry.addData("Status: ", "Run Time: " + robot.runtime.toString());
            telemetry.addData("Jewel Servo: ", "Position: " + robot.jewelExtension1.getPosition());
            telemetry.addData("Color Sensor 1: ", "Red: " + red);
            telemetry.addData("Color Sensor 1: ", "Blue: " + blue);
            telemetry.addData("Color Sensor 1: ", "Green: " + green);
            telemetry.addData("Ball Color: ", "red v blue: " + bc);
            telemetry.update();

        }
    }
}
