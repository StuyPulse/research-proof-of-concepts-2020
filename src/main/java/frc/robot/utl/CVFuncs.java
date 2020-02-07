/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utl;
import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;

/**
 * Add your docs here.
 */
public class CVFuncs {
    public static double txOffset(){
        double[][] data = Limelight.getVertices();
        double srx = 0;
        double slx = 0;
        if(data.length > 0 && data[0].length > 3){
            double middleX = (data[0][3] + data[0][2])/2;
            double rightDistortion = data[0][1] - middleX;
            double leftDistortion = data[0][0] - middleX;
            //length of top
            double s1x = data[0][1] - data[0][0];
            //length of left
            slx = data[0][3] - data[0][0];
            srx = data[0][1] - data[0][2];
            System.out.println("\n\n\n--------\n"+"("+data[0][0]+","+data[1][0]+")"+
                                "("+data[0][1]+","+data[1][1]+")"+
                                "("+data[0][2]+","+data[1][2]+")"+
                                "("+data[0][3]+","+data[1][3]+")");
            System.out.println(s1x+"|"+slx+"|"+srx+"|"+(slx-srx)+"\n-------");
        }
        // if(Math.abs(slx - srx) < 1.5){
        //     return 0;
        // }
        return (srx-slx);
    }
    public static double getDistanceToTarget(){
        if(!Limelight.hasAnyTarget())return 0;
        double kLimelightPitch = 28; // deg
        double kLimelightAngle = 0; // deg
        double goal_angle = Limelight.getTargetXAngle() + kLimelightAngle;
        double goal_pitch = Limelight.getTargetYAngle() + kLimelightPitch;
        double goal_height = 92 - 31;
        return goal_height / Math.tan(Math.toRadians(goal_pitch)) - 120;
    }
	public static Mat[] estimatePose() {
        System.loadLibrary("opencv_java347");
		List<Point3> objectPointsList = new ArrayList<Point3>();
		objectPointsList.add(new Point3(-19.625, 0, 0));
		objectPointsList.add(new Point3(19.625, 0, 0));
		objectPointsList.add(new Point3(9.82, 17, 0));
		objectPointsList.add(new Point3(-9.82, 17, 0));
		
		MatOfPoint3f objectPointsMat = new MatOfPoint3f();
        objectPointsMat.fromList(objectPointsList);
        		
        List<Point> imagePointsList = new ArrayList<Point>();
        double[][] data = Limelight.getVertices();
        if(data[0].length < 4 || data[0][0] == 0) {
            return null;
        }
        
        for(int i = 0; i < 4; i++){
          imagePointsList.add(new Point(data[0][i], data[1][i]));
        }
		
		MatOfPoint2f imagePointsMat = new MatOfPoint2f();
		imagePointsMat.fromList(imagePointsList);
			
        Mat cameraMatrix = new Mat(3,3,CvType.CV_64FC1);//(2.5751292067328632e+02, 0., 1.5971077914723165e+02, 0.,2.5635071715912881e+02, 1.1971433393615548e+02, 0., 0., 1.);
        cameraMatrix.put(0, 0, 2.5751292067328632e+02);
        cameraMatrix.put(0, 1, 0.);
        cameraMatrix.put(0, 2, 1.5971077914723165e+02);
        cameraMatrix.put(1, 0, 0.);
        cameraMatrix.put(1, 1, 2.5635071715912881e+02);
        cameraMatrix.put(1, 2, 1.1971433393615548e+02);
        cameraMatrix.put(2, 0, 0.);
        cameraMatrix.put(2, 1, 0.);
        cameraMatrix.put(2, 2, 1.);


		MatOfDouble distCoeffs = new MatOfDouble(2.9684613693070039e-01, -1.4380252254747885e+00, -2.2098421479494509e-03, -3.3894563533907176e-03, 2.5344430354806740e+00);
		Mat rvec = new Mat(3,1,CvType.CV_64FC1);
        Mat tvec = new Mat(3,1,CvType.CV_64FC1);
        
        // System.out.println("\n\n\nDepth");
        // System.out.println(rvec.depth());
        // System.out.println("Width");
        // System.out.println(rvec.width());
        // System.out.println("Height");
        // System.out.println(rvec.height());
        // System.out.println("Channels");
        // System.out.println(rvec.channels());
        // System.out.println("Size");
        // System.out.println(rvec.size());

        // System.out.println("Depth");
        // System.out.println(tvec.depth());
        // System.out.println("Width");
        // System.out.println(tvec.width());
        // System.out.println("Height");
        // System.out.println(tvec.height());
        // System.out.println("Channels");
        // System.out.println(tvec.channels());
        // System.out.println("Size");
        // System.out.println(tvec.size());

		if (Calib3d.solvePnP(objectPointsMat, imagePointsMat, cameraMatrix, distCoeffs, rvec, tvec)) {
            return new Mat[] {rvec, tvec};
        }
        return null;
    }
	
    public static double[] getTranslation(){
        Mat[] data = estimatePose();
        double[] xyz = new double[3];
        if(data != null){
            // System.out.println(stuff[2].get(2,0).length);

            xyz[0] = data[1].get(0, 0)[0];
            xyz[1] = data[1].get(1, 0)[0];
            xyz[2] = data[1].get(2, 0)[0];
        }
        return xyz;
    }
    
    // public static void main(String[] args) { // Test client
    //    // getXYZ();
    // }
}
