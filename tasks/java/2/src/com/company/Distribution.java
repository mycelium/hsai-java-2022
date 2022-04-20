package com.company;

public enum Distribution {
    Uniform(0, 2, "Min", "Max"),
    Normal(1, 2, "Mean", "Standard deviation"),
    Poisson(2, 1, "Mean", null);

    Distribution(int type, int parCount, String firstParName, String secondParName) {
        this.type = type;
        this.parNum = parCount;
        this.firstName = firstParName;
        this.secondName = secondParName;
    }

    public int getParameterNumber() {
        return this.parNum;
    }

    public int getType() {
        return this.type;
    }

    public String[] getParameterNames() {
        return new String[] {firstName, secondName};
    }

    private int type;
    private String firstName;
    private String secondName;
    private int parNum;
}