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
        controller.set(value);
    }

    public void setColor(Color color) {
        switch (color) {
            case PINK:
                setValue(0.57);
            case RED:
                setValue(0.61);
            case ORANGE:
                setValue(0.65);
            case YELLOW:
                setValue(0.69);
            case GREEN:
                setValue(0.77);
            case BLUE:
                setValue(0.87);
            case PURPLE:
                setValue(0.91);
            case BLACK:
                setValue(0.99);
            case WHITE:
                setValue(0.93);
            case GRAY:
                setValue(0.95);
            case RAINBOW:
                setValue(-0.99);
        }
    }

}