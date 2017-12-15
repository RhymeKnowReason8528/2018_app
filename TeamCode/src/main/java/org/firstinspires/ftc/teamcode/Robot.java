package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

    public String KEY = new String();

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

    public void getVuforiaKey() {
        this.KEY = "AdS8N7v/////AAAAGeY4UIl2TERklrM+PcYE7C6F9ws43EXwdhy7nPs+KOA3OpkI+AiM+uVBOIQaPH7mBZ514vuoQLUxUf+IVn42BJ+Fy1esAxeT7H2JPZ8FaVjf8agAwpY6/gs4UbNOpkeRvL/4hFp3zcDU6iOgUT1d/IlfMDc8rYvP9L6Iz9HzjaUU/dlWI8SDGCEwEl5OetdHMn+vXXrA5wRdI9PwtoR6EeOKJRXeHuZo+k2HsjoxGMgxb6U7LFYaJTIdv8MoHppxvQMbvpiYPPHO0jAf4Mj6As0Vtvud7gkjEPHE5NU1yaAEbOqjKWlaRY/GSn2kK/Gp8OB0NLYxci1FzvtI8QDcPqcmEbJhrEr2NCOs+l8fuQND";
    }

    public void arm(double position) {
        this.armServo.setPosition(position);
    }

    public void wrist(double position) {
        this.wristServo.setPosition(position);
    }

    public void gripper(double position) {
        this.gripperOneServo.setPosition(position);
    }

    public double inchesToTicks(double inches) {
        double rotation = 1440;
        double circumference = 12.5663706;

        double go = inches / circumference;
        return (go * rotation);
    }

    public void autoDrive (double distance, double speed) {
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

    public void autoTurn (double distance, double speed) {
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
