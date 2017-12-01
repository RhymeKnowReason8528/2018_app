package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by trucc on 10/27/2017.
 */

public class Robot {

    ElapsedTime runtime = new ElapsedTime();
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    public Servo armServo;
    public Servo gripperOneServo;
    public Servo wristServo;

    private LinearOpMode linearOpMode;
    private boolean initWithOpMode = false;

    public void init (HardwareMap hwmap, LinearOpMode opMode) {
        leftDrive  = hwmap.dcMotor.get("left_drive");
        rightDrive = hwmap.dcMotor.get("right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        armServo = hwmap.servo.get("arm_servo");
        gripperOneServo = hwmap.servo.get("gripper_one_servo");
        wristServo = hwmap.servo.get("wrist_servo");
        linearOpMode = opMode;
        initWithOpMode = true;
    }


    public double inchesToTicks(double inches) {
        double rotation = 1440;
        double circumference = 12.5663706;

        double go = inches / circumference;
        return (go * rotation);
    }

    public void driveForward (double distance, double speed) {
        double motorPosition = rightDrive.getCurrentPosition();
        if(speed > 0) {
            while(rightDrive.getCurrentPosition() < distance + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(speed);
                rightDrive.setPower(speed);
            }
        }
        if(speed < 0) {
            while(rightDrive.getCurrentPosition() > (-distance) + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(speed);
                rightDrive.setPower(speed);
            }
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    public void turn (double distance, double speed) {
        double motorPosition = rightDrive.getCurrentPosition();
        if(speed > 0) {
            while(rightDrive.getCurrentPosition() < distance + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(-speed);
                rightDrive.setPower(speed);
            }
        }
        if(speed < 0) {
            while(rightDrive.getCurrentPosition() > (-distance) + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(-speed);
                rightDrive.setPower(speed);
            }
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }
}
