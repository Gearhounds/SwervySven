package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;


public class Swervedrive {
    
  final AHRS gyro = new AHRS();

    private WheelDrive backRight;
    private WheelDrive backLeft;
    private WheelDrive frontRight;
    private WheelDrive frontLeft;

    public Swervedrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft){
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
    }
    
    public void turn(double turnPower){
        frontLeft.SetDirection(45, turnPower);
        frontRight.SetDirection(135, turnPower);
        backLeft.SetDirection(225, turnPower);
        backRight.SetDirection(315, turnPower);
    }

    double oldAngle;

    public void drive(double lX, double lY, double rX){
        double speed = Math.sqrt((lX*lX) + (lY*lY));

        double yaw = gyro.getYaw();

        
        
        double angle = -Math.atan(lX/1);
    
        angle *= 100;


        //angle += yaw;
    
        if((lX == 0 && lY == 0) && oldAngle != 0){
          angle = oldAngle;
        }
        // else{
        //   angle = 0;
        // }

        
        SmartDashboard.putNumber("Raw angle", angle);
        
        if (lX>=0 && lY>=0){ //1
          angle = 90 - angle - 90;
        }
        if(lX>0 && lY<0){//2
          angle = angle + 180;
        }
        if(lX<0 && lY>0){//4
          angle = 90-angle + 270;
        }
        if(lX<0 && lY<0){//3
          angle = angle + 180;
        }
        if(lY < 0 && angle == 0){
          angle = 180;
        }
        if(lY == 0 && lX < 0) {
          angle = 270;
        }
        if(lY == 0 && lX > 0) {
          angle = 90;
        }

        // if(speed < .1){
        //     speed = 0;
        // }

        oldAngle = angle;

        angle = Math.abs(angle);

        






        // angle = Math.sqrt(angle*angle);
        // angle *= 2;
        // angle = 0;
        // speed *=0.25;
        if(rX == 0){
            frontLeft.SetDirection(angle-yaw, speed);
            frontRight.SetDirection(angle-yaw, speed);
            backLeft.SetDirection(angle-yaw, speed);
            backRight.SetDirection(angle-yaw, speed);
        } else if (speed == 0){
          frontLeft.SetDirection(45, rX);
          frontRight.SetDirection(135, rX);
          backLeft.SetDirection(315, rX);
          backRight.SetDirection(225, rX);
        }else{
          frontLeft.turnAndDrive(angle-yaw, speed, rX, 315);
          frontRight.turnAndDrive(angle-yaw, speed, rX, 45);
          backLeft.turnAndDrive(angle-yaw, speed, rX, 135);
          backRight.turnAndDrive(angle-yaw, speed, rX, 225);
        }
        
        
        SmartDashboard.putNumber("angle", angle);
        SmartDashboard.putNumber("Yaw", yaw);
    }

    public void autoDrive(double angle, double speed, double rotate) {
      oldAngle = angle;

        angle = Math.abs(angle);

        






        // angle = Math.sqrt(angle*angle);
        // angle *= 2;
        // angle = 0;
        // speed *=0.25;
        if(rotate == 0){
            frontLeft.SetDirection(angle, speed);
            frontRight.SetDirection(angle, speed);
            backLeft.SetDirection(angle, speed);
            backRight.SetDirection(angle, speed);
        } else {
          frontLeft.SetDirection(45, rotate);
          frontRight.SetDirection(135, rotate);
          backLeft.SetDirection(315, rotate);
          backRight.SetDirection(225, rotate);
        }
    }
}
