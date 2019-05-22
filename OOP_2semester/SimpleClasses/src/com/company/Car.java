package com.company;

public abstract class Car {
    protected int MaxVel;
    protected int NumOfSeats;
    protected String NameOfModel;
    protected String NameOfProdecer;
    protected int HP;

    public String getInfo() {
        String output = "CAR\nModel: " + NameOfModel + "\nProducer: " + NameOfProdecer + "\nThis car has " + HP + " horsepowers\nSeats for "
                + NumOfSeats + " people\nMaximum speed is " + MaxVel +"km/h\n";
        return output;
    }
}
