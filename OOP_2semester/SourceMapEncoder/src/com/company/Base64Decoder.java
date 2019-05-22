package com.company;

public class Base64Decoder {
    public static byte[] decodeBase64(String code) {
        byte[] decoded = new byte[6 * code.length()];
        int B64code = 0;
        int count = 0;
        int pointer = decoded.length - 1;
        for (int i = code.length() - 1; i >= 0; i--) {
            if (code.charAt(i) >= 'A' && code.charAt(i) <= 'Z') {
                B64code = code.charAt(i) - 65;
            } else if (code.charAt(i) >= 'a' && code.charAt(i) <= 'z') {
                B64code = code.charAt(i) - 97 + 26;
            } else if (code.charAt(i) >= '0' && code.charAt(i) <= '9') {
                B64code = code.charAt(i) - 48 + 52;
            } else if (code.charAt(i) == '/') {
                B64code = 63;
            } else if (code.charAt(i) == '+') {
                B64code = 62;
            }
            while (B64code > 0){
                decoded[pointer] = (byte)(B64code % 2);
                pointer--;
                B64code /= 2;
                count++;
            }
            if (count < 6){
                for (int j = count; j < 6; j++){
                    decoded[pointer] = 0;
                    pointer--;
                }
            }
            count = 0;
        }
        return decoded;
    }
}
