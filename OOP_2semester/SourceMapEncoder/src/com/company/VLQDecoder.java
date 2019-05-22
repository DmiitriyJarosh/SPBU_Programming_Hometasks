package com.company;

public class VLQDecoder {
    private int[] elem;
    VLQDecoder(int colIN, int fileIN, int lineOUT, int colOUT, int word){
        elem = new int[6];
        elem[0] = colIN;
        elem[1] = fileIN;
        elem[2] = lineOUT;
        elem[3] = colOUT;
        elem[4] = word;
        elem[5]  = 1;
    }

    public int[] getElem() {
        return elem;
    }

    private boolean isExist(){
        return !(elem[0] == -1);
    }

    public void ToAbsolute(VLQDecoder previous, boolean newLine){
        if (previous.isExist()){
            int[] prevElem;
            prevElem = previous.getElem();
            if (elem[2] == prevElem[2]){
                elem[3] += prevElem[3];
            }
            elem[1] += prevElem[1];
            elem[4] += prevElem[4];
            if (!newLine){
                elem[0] += prevElem[0];
                elem[5] = prevElem[5];
            }
            else{
                elem[5] = prevElem[5] + 1;
            }
        }
    }

    public void fromVLQ(byte[] code) {
        int pointer = code.length - 1;
        int sign, num, powTwo, tmp;
        boolean flag;
        for (int j = 4; j >= 0; j--)
        {
            num = 0;
            flag = false;
            powTwo = 1;
            tmp = pointer;
            if (pointer - 6 > 0) {
                pointer -= 6;
                while (pointer - 5 > 0 && code[pointer - 5] == 1) {
                    powTwo *= 32;
                    pointer -= 6;
                    flag = true;
                }
                if (flag){
                    powTwo /= 2;
                }
            }
            pointer = tmp;
            while (pointer - 11 > 0 && code[pointer - 11] == 1) {
                for (int i = pointer; i > pointer - 5; i--) {
                    num += code[i] * powTwo;
                    powTwo *= 2;
                }
                pointer -= 6;
                powTwo /= 1024;
            }
            sign = -1 * (int)code[pointer];
            if (sign == 0){
                sign = 1;
            }
            powTwo = 1;
            for (int i = pointer - 1; i > pointer - 5; i--) {
                num += code[i] * powTwo;
                powTwo *= 2;
            }
            pointer -= 6;
            num *= sign;
            elem[j] = num;
        }
    }

    /*public void ToRelative(elem previous, boolean newLine){
        if (previous.isExist()){
            int[] prevElem;
            prevElem = previous.getElem();
            if (elem[2] == prevElem[2]){
                elem[3] -= prevElem[3];
            }
            elem[1] -= prevElem[1];
            elem[4] -= prevElem[4];
            if (!newLine){
                elem[0] -= prevElem[0];
            }
        }
    }

    public byte[] toVLQ() {
        int BinLength = 0, tmp;
        int[] Blength = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++){
            tmp = Math.abs(elem[i]);
            while (tmp > 0){
                BinLength++;
                Blength[i]++;
                tmp /= 2;
            }
        }
        byte[] code = new byte[BinLength];
        int codePointer = 0;
        byte[] binary;
        int pointer, sign = 1;
        for (int i = 0; i < 5; i++) {
            binary = new byte[Blength[i]];
            pointer = Blength[i] - 1;
            if (elem[i] < 0) {
                sign = -1;
            }
            tmp = Math.abs(elem[i]);
            while (tmp > 0) {
                binary[pointer] = (byte)(tmp % 2);
                pointer--;
                tmp /= 2;
            }

            if (Blength[i] > 4){
                code[codePointer] = 1;
                codePointer++;
                pointer = Blength[i] - 4;
                for (int j = 0; j < 4; j++){
                    code[codePointer] = binary[pointer];
                    codePointer++;
                    pointer++;
                }

            }
        }
        return code;
    }
    */
}

