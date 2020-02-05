package frc.robot;

import edu.wpi.first.wpilibj.PWMSparkMax;

public class LEDController {

    private PWMSparkMax controller;

    public enum Color {
        PINK, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, BLACK, WHITE, GRAY, RAINBOW
    }

    public LEDController(int port) {
        controller = new PWMSparkMax(port);
    }

    public void setValue(double value) {
        controller.setSpeed(value);
    }

    public void setColor(Color color) {
        switch (color) {
            case PINK:
                setValue(0.57);
                break;
            case RED:
                setValue(0.61);
                break;
            case ORANGE:
                setValue(0.65);
                break;
            case YELLOW:
                setValue(0.69);
                break;
            case GREEN:
                setValue(0.77);
                break;
            case BLUE:
                setValue(0.87);
                break;
            case PURPLE:
                setValue(0.91);
                break;
            case BLACK:
                setValue(0.99);
                break;
            case WHITE:
                setValue(0.93);
                break;
            case GRAY:
                setValue(0.95);
                break;
            case RAINBOW:
                setValue(-0.99);
                break;
        }
    }

}