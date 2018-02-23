package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/* 34 lines
 * Autonomous v 1.0 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Red 2")
public class Red2Auto extends LinearOpMode {

    private Robot robot = new Robot();

    private int red;
    private int blue;

    RelicRecoveryVuMark vuMark;

    String displayVuMark = new String();

    boolean isVisibleVuMark;

    int side = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, this);
        robot.vuforiaInit(hardwareMap, "right");
        double currentRunTime = getRuntime();

        blue = robot.getBlue(1);
        red = robot.getRed(1);

        robot.enableGripper();
        robot.moveGripperClosed();

        waitForStart();

        double speed;

        speed = robot.getJewelSpeed(side);
        robot.autoDrive(robot.inchesToTicks(2), speed);
        sleep(500);
        robot.jewelT1Retract();
        sleep(2000);


        robot.moveGripperClosed();

        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

//--------------------------------------get vumark and drive to cryptobox---------------------------------------------------------

        robot.relicTrackables.activate();
        robot.autoDrive(robot.inchesToTicks(5), -1);
        vuMark = RelicRecoveryVuMark.from(robot.relicTemplate);
        robot.autoDrive(robot.inchesToTicks(5), -1);
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            vuMark = RelicRecoveryVuMark.from(robot.relicTemplate);
        }
        robot.autoDrive(robot.inchesToTicks(5), -1);
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            vuMark = RelicRecoveryVuMark.from(robot.relicTemplate);
        }
        robot.autoDrive(robot.inchesToTicks(5), -1);
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            vuMark = RelicRecoveryVuMark.from(robot.relicTemplate);
        }
        robot.autoDrive(robot.inchesToTicks(5), -1);
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            vuMark = RelicRecoveryVuMark.from(robot.relicTemplate);
        }

//-----------------------------------------------------------------------------------------------------------------------------------

        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            robot.autoDrive(robot.inchesToTicks(5), -1);
        } else if (vuMark == RelicRecoveryVuMark.CENTER) {
            robot.autoDrive(robot.inchesToTicks(4), -1);
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            robot.autoDrive(robot.inchesToTicks(7), -1);
        }

        robot.autoTurn(1800, 1);
        currentRunTime = getRuntime();
        while(getRuntime() < 1 + currentRunTime && opModeIsActive()) {
        }

        robot.autoDrive(robot.inchesToTicks(10), -1);

        sleep(1000);
        robot.autoDrive(robot.inchesToTicks(3), 1);

    }
}
