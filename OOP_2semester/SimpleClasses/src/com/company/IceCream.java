package com.company;

public abstract class IceCream {
    protected String type;
    protected String taste;
    protected String firstElement;
    protected String secondElement;
    protected String thirdElement;
    protected int NumOfFirst;
    protected int NumOfSecond;
    protected int NumOfThird;

    public String getInfo() {
        int i = 1;
        String output = "Ice Cream\nType: " + type + "\nThis ice cream will have " + taste + " taste\nTo make it follow the receipe:\n";
        if (firstElement != "-" && NumOfFirst != 1){
            output += i + ") Add " + NumOfFirst + " balls of " + firstElement + " ice cream\n";
            i++;
        }
        if (firstElement != "-" && NumOfFirst == 1){
            output += i + ") Add " + NumOfFirst + " ball of " + firstElement + " ice cream\n";
            i++;
        }
        if (secondElement != "-" && NumOfSecond != 1){
            output += i + ") Add " + NumOfSecond + " balls of " + secondElement + " ice cream\n";
            i++;
        }
        if (secondElement != "-" && NumOfSecond == 1){
            output += i + ") Add " + NumOfSecond + " ball of " + secondElement + " ice cream\n";
            i++;
        }

        if (thirdElement != "-" && NumOfThird != 1){
            output += i + ") Add " + NumOfThird + " balls of " + thirdElement + " ice cream\n";
            i++;
        }
        if (thirdElement != "-" && NumOfThird == 1){
            output += i + ") Add " + NumOfThird + " ball of " + thirdElement + " ice cream\n";
            i++;
        }
        return output;
    }
}
