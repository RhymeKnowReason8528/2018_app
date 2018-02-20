package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.teamcode.Robot.GripperState.CLOSED;
import static org.firstinspires.ftc.teamcode.Robot.GripperState.MAX_OPEN;

/**
 * Created by trucc on 10/27/2017.
 */

public class Robot {

    ElapsedTime runtime = new ElapsedTime();
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    public DcMotor gripperOneMotor;
    public ServoImplEx armServo;

    public ServoImplEx jewelExtension1;

    // get a reference to our digitalTouch object.

    DigitalChannel touchSensor1;
    DigitalChannel touchSensor2;

    public VuforiaTrackable relicTemplate;
    public VuforiaTrackables relicTrackables;

    int ballColor;

    int retVal;

    ColorSensor colorSensor1;

    public String KEY = new String();

    private LinearOpMode linearOpMode;
    private boolean initWithOpMode = false;
    private boolean isGripperDisabled = false;

    private final double GRIPPER_OPEN_LIMIT = 0.52;
    private final double GRIPPER_CLOSED_LIMIT = 0.67;

    private final double jewelExtendedPosition = 0.1;
    private final double jewelRetractPosition = 0.7;

    int ballDifference = 10;

    VuforiaLocalizer vuforia_localizer;

    enum GripperState {
        MAX_OPEN, //MAX_OPEN might be used once limit switches are added for the open limit
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
        if (getGripperState() != MAX_OPEN && !isGripperDisabled) {
            gripperOneMotor.setPower(-1);
        }
    }

    public void moveGripperClosed() {
        if (getGripperState() != CLOSED && !isGripperDisabled) {
            gripperOneMotor.setPower(1);
        }
    }

    public void moveGripperFullClosed() {
        while (getGripperState() != CLOSED) {
            moveGripperClosed();
        }
    }

    public void moveGripperFullOpen() {
        while (getGripperState() != GripperState.MAX_OPEN) {
            moveGripperClosed();
        }
        gripperOneMotor.setPower(0);
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

    public void init(HardwareMap hwmap, LinearOpMode opMode) {
        leftDrive = hwmap.dcMotor.get("left_drive");
        rightDrive = hwmap.dcMotor.get("right_drive");

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        armServo = (ServoImplEx) hwmap.servo.get("arm_servo");
        gripperOneMotor = hwmap.dcMotor.get("gripper_motor");

        touchSensor1 = hwmap.get(DigitalChannel.class, "touch_sensor_one");
        touchSensor2 = hwmap.get(DigitalChannel.class, "touch_sensor_two");
        touchSensor1.setMode(DigitalChannel.Mode.INPUT);
        touchSensor2.setMode(DigitalChannel.Mode.INPUT);

        jewelExtension1 = (ServoImplEx) hwmap.servo.get("jewelServoRight");

        jewelT1Retract();

        colorSensor1 = hwmap.get(ColorSensor.class, "colorSensorRight");

        linearOpMode = opMode;
        initWithOpMode = true;

        gripperOneMotor.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void vuforiaInit(HardwareMap hardwareMap, String direction) {
        getVuforiaKey();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia_localizer = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackables = vuforia_localizer.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
    }

    public void getVuforiaKey() {
        this.KEY = "AdS8N7v/////AAAAGeY4UIl2TERklrM+PcYE7C6F9ws43EXwdhy7nPs+KOA3OpkI+AiM+uVBOIQaPH7mBZ514vuoQLUxUf+IVn42BJ+Fy1esAxeT7H2JPZ8FaVjf8agAwpY6/gs4UbNOpkeRvL/4hFp3zcDU6iOgUT1d/IlfMDc8rYvP9L6Iz9HzjaUU/dlWI8SDGCEwEl5OetdHMn+vXXrA5wRdI9PwtoR6EeOKJRXeHuZo+k2HsjoxGMgxb6U7LFYaJTIdv8MoHppxvQMbvpiYPPHO0jAf4Mj6As0Vtvud7gkjEPHE5NU1yaAEbOqjKWlaRY/GSn2kK/Gp8OB0NLYxci1FzvtI8QDcPqcmEbJhrEr2NCOs+l8fuQND";
    }

    public void arm(double position) {
        this.armServo.setPosition(position);
    }


    public double inchesToTicks(double inches) {
        double rotation = 1440;
        double circumference = 12.5663706;

        double go = inches / circumference;
        return (go * rotation);
    }

    public void autoDrive(double distance, double speed) {
        double initialPosition = rightDrive.getCurrentPosition();
        if (speed > 0) {
            while (rightDrive.getCurrentPosition() < distance + initialPosition && linearOpMode.opModeIsActive()) {

                leftDrive.setPower(speed);
                rightDrive.setPower(speed);

                linearOpMode.telemetry.addData("getCurrentPosition: ", "returning: " + rightDrive.getCurrentPosition());
                linearOpMode.telemetry.update();
            }
        }
        if (speed < 0) {
            while (rightDrive.getCurrentPosition() > initialPosition - distance && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(speed);
                rightDrive.setPower(speed);
            }
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    public void autoTurn(double distance, double speed) {
        double motorPosition = rightDrive.getCurrentPosition();
        if (speed > 0) {
            while (rightDrive.getCurrentPosition() < distance + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(-speed);
                rightDrive.setPower(speed);
            }
        }
        if (speed < 0) {
            while (rightDrive.getCurrentPosition() > (-distance) + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(-speed);
                rightDrive.setPower(speed);
            }
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    public void ledEnable() {
        colorSensor1.enableLed(true);
    }

    public void ledDisable() {
        colorSensor1.enableLed(false);
    }

    public int getRGB(int colorSensor) {
        if (colorSensor == 1) {
            retVal = colorSensor1.argb();
        }
        return retVal;
    }

    public int getRed(int colorSensor) {
        if (colorSensor == 1) {
            return colorSensor1.red();
        }
        return 0;
    }

    public int getBlue(int colorSensor) {
        if (colorSensor == 1) {
            return colorSensor1.blue();
        }
        return 0;
    }

    public int getGreen(int colorSensor) {
        if (colorSensor == 1) {
            return colorSensor1.green();
        }
        return 0;
    }

    public int getBallColor(int r, int b) {
        if (r >= (b + ballDifference)) {
            return 1;
        } else if (b >= (r + ballDifference)) {
            return 2;
        }
        return 0;
    }

    public void jewelT1Retract() {
        jewelExtension1.setPosition(jewelRetractPosition);
    }

    public void jewelT1Extend() {
        jewelExtension1.setPosition(jewelExtendedPosition);
    }

    int getJewelSpeed(int side) {
        //1 is red, 2 is blue

        this.ledEnable();

        this.jewelT1Extend();
        linearOpMode.sleep(2000);

        for (int i = 0; i < 10 && ballColor == 0; i++) {
            ballColor = getBallColor(getRed(1), getBlue(1));
            linearOpMode.sleep(500);
        }

        if (side == 1) {
            if (ballColor == 1) {
                return -1;
            } else if (ballColor == 2) {
                return 1;
            }
        } else if (side == 2) {
            if (ballColor == 1) {
                return 1;
            } else if (ballColor == 2) {
                return -1;
            }
        }
        this.jewelT1Retract();
        return 0;
    }
}
