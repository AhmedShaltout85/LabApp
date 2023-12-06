package com.ao8r.labapp.models;

public class OnSiteTestModel {
    String testNameArabicInModel;
    double testMaxValueInModel, testMinValueInModel;

    public OnSiteTestModel() {
    }

    public OnSiteTestModel(String testNameArabicInModel, double testMaxValueInModel, double testMinValueInModel) {
        this.testNameArabicInModel = testNameArabicInModel;
        this.testMaxValueInModel = testMaxValueInModel;
        this.testMinValueInModel = testMinValueInModel;
    }

    public String getTestNameArabicInModel() {
        return testNameArabicInModel;
    }

    public void setTestNameArabicInModel(String testNameArabicInModel) {
        this.testNameArabicInModel = testNameArabicInModel;
    }

    public double getTestMaxValueInModel() {
        return testMaxValueInModel;
    }

    public void setTestMaxValueInModel(double testMaxValueInModel) {
        this.testMaxValueInModel = testMaxValueInModel;
    }

    public double getTestMinValueInModel() {
        return testMinValueInModel;
    }

    public void setTestMinValueInModel(double testMinValueInModel) {
        this.testMinValueInModel = testMinValueInModel;
    }

    @Override
    public String toString() {
        return testNameArabicInModel
                + "-"+testMinValueInModel+"-"+testMaxValueInModel;
    }
}
