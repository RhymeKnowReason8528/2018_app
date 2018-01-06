package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Robot.GripperState.CLOSED;
import static org.firstinspires.ftc.teamcode.Robot.GripperState.MAX_OPEN;

/**
 * Created by trucc on 10/27/2017.
 */

public class Robot {

    ElapsedTime runtime = new ElapsedTime();
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    private ServoImplEx gripperOneServo;
    public ServoImplEx armServo;
    // get a reference to our digitalTouch object.

    DigitalChannel touchSensor1;
    DigitalChannel touchSensor2;

    public String KEY = new String();

    private LinearOpMode linearOpMode;
    private boolean initWithOpMode = false;
    private boolean isGripperDisabled = false;

    private final double GRIPPER_OPEN_LIMIT = 0.52;
    private final double GRIPPER_CLOSED_LIMIT = 0.69;

    enum GripperState {
        MAX_OPEN, //MAX_OPEN might be used once limit switches are added for the open limit
        //TODO: Order and add limit switches for the outside and the appropriate code
        CLOSED,
        MIDDLE
    }
    
    final double GRIPPER_MODIFY = 0.004;

    public GripperState getGripperState() {

        if (touchSensor1.getState() == false && touchSensor2.getState() == false) {
             return GripperState.CLOSED;
        } /*else if (!outer limit switch) {
                gripperState = GripperState.MAX_OPEN;
            } */ else {
            return GripperState.MIDDLE;
        }
    }

    public void moveGripperOpen() {
        if(getGripperState() != MAX_OPEN && !isGripperDisabled && (gripperOneServo.getPosition() - GRIPPER_MODIFY) >= GRIPPER_OPEN_LIMIT) {
            gripperOneServo.setPosition(gripperOneServo.getPosition() - GRIPPER_MODIFY);
        }
    }

    public void moveGripperClosed() {
        if (getGripperState() != CLOSED && !isGripperDisabled && (gripperOneServo.getPosition() + GRIPPER_MODIFY) <= GRIPPER_CLOSED_LIMIT)  {
            gripperOneServo.setPosition(gripperOneServo.getPosition() + GRIPPER_MODIFY);
        }
    }

    public void moveGripperFullClosed() {
        while(getGripperState() != Robot.GripperState.CLOSED && gripperOneServo.getPosition() + GRIPPER_MODIFY < GRIPPER_CLOSED_LIMIT) {
            moveGripperClosed();
        }
        gripperServoPwmDisable();
    }

    public void moveGripperFullOpen() {
        while(getGripperState() != GripperState.MAX_OPEN && gripperOneServo.getPosition() - GRIPPER_MODIFY > GRIPPER_OPEN_LIMIT) {
            moveGripperClosed();
        }
        gripperServoPwmDisable();
    }

    public void gripperServoPwmDisable() {
        gripperOneServo.setPwmDisable();
    }

    public void disableGripper() {
        isGripperDisabled = true;
    }

    public void enableGripper() {
        isGripperDisabled = false;
    }

    public boolean isGripperDisabled() {
        return isGripperDisabled;
    }
    public void init (HardwareMap hwmap, LinearOpMode opMode) {
        leftDrive  = hwmap.dcMotor.get("left_drive");
        rightDrive = hwmap.dcMotor.get("right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        armServo = (ServoImplEx) hwmap.servo.get("arm_servo");
        gripperOneServo = (ServoImplEx) hwmap.servo.get("gripper_one_servo");

        touchSensor1 = hwmap.get(DigitalChannel.class, "touch_sensor_one");
        touchSensor2 = hwmap.get(DigitalChannel.class, "touch_sensor_two");
        touchSensor1.setMode(DigitalChannel.Mode.INPUT);
        touchSensor2.setMode(DigitalChannel.Mode.INPUT);

        linearOpMode = opMode;
        initWithOpMode = true;

        gripperOneServo.setPwmEnable();
        gripperOneServo.setPosition(0.52);
    }

    public void getVuforiaKey() {
        this.KEY = "AdS8N7v/////AAAAGeY4UIl2TERklrM+PcYE7C6F9ws43EXwdhy7nPs+KOA3OpkI+AiM+uVBOIQaPH7mBZ514vuoQLUxUf+IVn42BJ+Fy1esAxeT7H2JPZ8FaVjf8agAwpY6/gs4UbNOpkeRvL/4hFp3zcDU6iOgUT1d/IlfMDc8rYvP9L6Iz9HzjaUU/dlWI8SDGCEwEl5OetdHMn+vXXrA5wRdI9PwtoR6EeOKJRXeHuZo+k2HsjoxGMgxb6U7LFYaJTIdv8MoHppxvQMbvpiYPPHO0jAf4Mj6As0Vtvud7gkjEPHE5NU1yaAEbOqjKWlaRY/GSn2kK/Gp8OB0NLYxci1FzvtI8QDcPqcmEbJhrEr2NCOs+l8fuQND";
    }

    public void arm(double position) {
        this.armServo.setPosition(position);
    }

    public void setGripperPosition(double position) {
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
