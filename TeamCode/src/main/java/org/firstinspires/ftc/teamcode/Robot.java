package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by trucc on 10/27/2017.
 */

public class Robot {

    ElapsedTime runtime = new ElapsedTime();
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;

    public void init (HardwareMap hwmap) {
        leftDrive  = hwmap.dcMotor.get("left_drive");
        rightDrive = hwmap.dcMotor.get("right_drive");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
    }
}
