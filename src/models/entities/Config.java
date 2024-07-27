/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.entities;

import java.io.Serializable;

/**
 *
 * @author rodri
 */
public class Config implements Serializable{
    
    private String portaComm;
    private Integer baunds;
    
    private Integer motorMode;
    
    private Float stepEixoX;
    private Float stepEixoY;
    private Float stepEixoZ;
    
    private Float servoPosMax;
    private Float servoPosMin;
    private Float servoIncrement;
    
    public Config(){
    }

    public Config(String portaComm, Integer baunds, Integer motorMode, Float stepEixoX, Float stepEixoY, Float stepEixoZ, Float servoPosMax, Float servoPosMin, Float servoIncrement) {
        this.portaComm = portaComm;
        this.baunds = baunds;
        this.motorMode = motorMode;
        this.stepEixoX = stepEixoX;
        this.stepEixoY = stepEixoY;
        this.stepEixoZ = stepEixoZ;
        this.servoPosMax = servoPosMax;
        this.servoPosMin = servoPosMin;
        this.servoIncrement = servoIncrement;
    }

    public String getPortaComm() {
        return portaComm;
    }

    public void setPortaComm(String portaComm) {
        this.portaComm = portaComm;
    }

    public Integer getBaunds() {
        return baunds;
    }

    public void setBaunds(Integer baunds) {
        this.baunds = baunds;
    }

    public Float getStepEixoX() {
        return stepEixoX;
    }

    public void setStepEixoX(Float stepEixoX) {
        this.stepEixoX = stepEixoX;
    }

    public Float getStepEixoY() {
        return stepEixoY;
    }

    public void setStepEixoY(Float stepEixoY) {
        this.stepEixoY = stepEixoY;
    }

    public Float getStepEixoZ() {
        return stepEixoZ;
    }

    public void setStepEixoZ(Float stepEixoZ) {
        this.stepEixoZ = stepEixoZ;
    }

    public Float getServoPosMax() {
        return servoPosMax;
    }

    public void setServoPosMax(Float servoPosMax) {
        this.servoPosMax = servoPosMax;
    }

    public Float getServoPosMin() {
        return servoPosMin;
    }

    public void setServoPosMin(Float servoPosMin) {
        this.servoPosMin = servoPosMin;
    }

    public Float getServoIncrement() {
        return servoIncrement;
    }

    public void setServoIncrement(Float servoIncrement) {
        this.servoIncrement = servoIncrement;
    }

    public Integer getMotorMode() {
        return motorMode;
    }

    public void setMotorMode(Integer motorMode) {
        this.motorMode = motorMode;
    }
    
    

    @Override
    public String toString() {
        return "Config{" + "portaComm=" + portaComm + ", baunds=" + baunds + ", stepEixoX=" + stepEixoX + ", stepEixoY=" + stepEixoY + ", stepEixoZ=" + stepEixoZ + ", servoPosMax=" + servoPosMax + ", servoPosMin=" + servoPosMin + ", servoIncrement=" + servoIncrement + '}';
    }
    
}
