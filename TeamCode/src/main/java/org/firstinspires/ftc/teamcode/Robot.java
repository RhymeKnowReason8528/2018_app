package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

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

    private RelicRecoveryVuMark vuMark;
    public boolean isVisibleVuMark;
    public static final String VUFORIA_KEY = "AdS8N7v/////AAAAGeY4UIl2TERklrM+PcYE7C6F9ws43EXwdhy7nPs+KOA3OpkI+AiM+uVBOIQaPH7mBZ514vuoQLUxUf+IVn42BJ+Fy1esAxeT7H2JPZ8FaVjf8agAwpY6/gs4UbNOpkeRvL/4hFp3zcDU6iOgUT1d/IlfMDc8rYvP9L6Iz9HzjaUU/dlWI8SDGCEwEl5OetdHMn+vXXrA5wRdI9PwtoR6EeOKJRXeHuZo+k2HsjoxGMgxb6U7LFYaJTIdv8MoHppxvQMbvpiYPPHO0jAf4Mj6As0Vtvud7gkjEPHE5NU1yaAEbOqjKWlaRY/GSn2kK/Gp8OB0NLYxci1FzvtI8QDcPqcmEbJhrEr2NCOs+l8fuQND";
    private VuforiaLocalizer vuforia;

    private LinearOpMode linearOpMode;
    private boolean initWithOpMode = false;

    public VuforiaTrackable relicTemplate;
    public VuforiaTrackables relicTrackables;

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

        this.vuforiaInit(hwmap);
    }

    public void vuforiaInit (HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
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
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
                this.isVisibleVuMark = false;
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    this.isVisibleVuMark = true;
                }
            }
        }
        if(speed < 0) {
            while(rightDrive.getCurrentPosition() > (-distance) + motorPosition && linearOpMode.opModeIsActive()) {
                leftDrive.setPower(speed);
                rightDrive.setPower(speed);
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
                this.isVisibleVuMark = false;
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    this.isVisibleVuMark = true;
                }
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

    public RelicRecoveryVuMark getVuMark() {
        return vuMark;
    }
}
