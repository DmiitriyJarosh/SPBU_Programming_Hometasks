package com.company;

public class Decoder {
    private String sourceMap;

    Decoder(String input){
        sourceMap = input;
    }

    public VLQDecoder[] Decode(){
        String[] codes = sourceMap.split(" ");
        boolean newLineOld = false;
        boolean newLine = false;
        VLQDecoder previous = new VLQDecoder(-1, 0, 0, 0, 0);
        VLQDecoder[] elems = new VLQDecoder[codes.length];
        for (int i = 0; i < codes.length; i++){
            if (codes[i].charAt(codes[i].length() - 1) == ';'){
                newLine = true;
            }
            codes[i] = codes[i].substring(0, codes[i].length() - 1);
            elems[i] = new VLQDecoder(-1, 0, 0, 0, 0);
            elems[i].fromVLQ(Base64Decoder.decodeBase64(codes[i]));
            elems[i].ToAbsolute(previous, newLineOld);
            previous = elems[i];
            newLineOld = newLine;
            newLine = false;
        }
        return elems;
    }

    public static void PrintSourceMap(VLQDecoder[] elems, String[] words, String[] files){
        int[] tmp;
        for (int i = 0; i < elems.length; i++){
            tmp = elems[i].getElem();
            Printer.println("Word: '" + words[tmp[4]] + "' in line " + tmp[5] + " Colomn " + tmp[0] + " from '" + files[tmp[1]] + "'  ->  " + " Line " + tmp[2] + " Colomn " + tmp[3]);
        }
    }
}
