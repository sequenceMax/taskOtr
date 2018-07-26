package com.otr.testtask.task1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class IPRange {
    private static final String IPADDRESS_PATTERN =
            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        Scanner scanner = new Scanner(System.in);

        String startIP, endIP;

        int[] startIPArr, endIPArr;

        while (true) {
            startIP = scanner.nextLine();
            endIP = scanner.nextLine();

            boolean check = validation(startIP, endIP);

            if (check) {
                startIPArr = Arrays.stream(startIP.split("\\.")).mapToInt(Integer::parseInt).toArray();
                endIPArr = Arrays.stream(endIP.split("\\.")).mapToInt(Integer::parseInt).toArray();
                break;
            } else {
                System.out.println("Проверьте правильность адресов");
            }
        }
        bruteIP(startIPArr, endIPArr);
    }

    private static boolean validation(String startIP, String endIP) {

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);

        return pattern.matcher(startIP).find() && pattern.matcher(endIP).find();
    }

    private static void bruteIP(int[] startIPArr, int[] endIPArr) {

        int[] mask = getMask(startIPArr, endIPArr);

/*
    Путём логики и абсурда выведена формула подсчета количества адресов в диапазоне.

    (mask[0] * 255^3) + ((mask[0] * 3 + mask[1]) * 255^2) + 255 * (mask[0] * 3 + mask[1] * 2 + mask[2]) +
    mask[0] + mask[1] + mask[2] + mask[3] - 1;

        * mask - разность по октетам.
*/

        int first = (255 * 255 * 255 * mask[0]);

        int second = (255 * 255 * (mask[1] + mask[0] * 3))  /*> 0 ?  (255 * 255 * (mask[1] + mask[0]*3)) : 0*/;

        int third = (255 * (mask[2] + mask[1] * 2 + (mask[0] * 3))) /*> 0 ? (255 * (mask[2] + mask[1]*2 + (mask[0] * 3))) : 0*/;

        int fourth = mask[3] + mask[1] + mask[0] + mask[2] - 1;

        int countAll = first + second + third + fourth;

        printIP(countAll, startIPArr);

        /*
         * Первым вариантом был подгон каждого актета под нужное значение многоуровневым for
         * Но в поисках альтернативы я ушел от этого решения.
         * Так же есть еще один способ - побитовыми сдвигами выравнивать IP адрес.
         * */

        /*for (int i = 0; i <= (endIPArr[0] - startIPArr[0]); i++) {
            int forJ = endIPArr[0] - startIPArr[0] == 0 ? (endIPArr[1] - startIPArr[1]) : 255;
            for (int j = 0; j <= forJ ; j++) {
                int startK;
                int endK;

                String startIP = "1.0.3.0";
                String endIP =   "1.1.4.66";

                if(j > 0){
                    startK = 0;
                    endK = endIPArr[1] - j- startIPArr[1] == 0 ? endIPArr[2] : 255;
                } else {
                    endK = endIPArr[1] - startIPArr[1] == 0 ? Math.abs(endIPArr[2] - startIPArr[2]) : 255;
                    startK = startIPArr[2];
                }
                for (int k = 0, startCountK = startK; k <= endK ; k++) {
                    int startL;
                    int endL;
                    if(k > 0){
                        startL =  1;
                        endL = endIPArr[2] - k - startIPArr[2] == 0 ? endIPArr[3] : 255;
                    } else {
                        startL = startIPArr[3];
                        endL = endIPArr[2] - startIPArr[2] == 0 ? Math.abs(endIPArr[3] - startIPArr[3]) : 255;
                    }
                    for (int l = 0, startCountL = startL; l < endL; l++) {
                        System.out.println(
                                (startIPArr[0] + i) + "." + (startIPArr[1] + j) +"." +
                                        (startCountK + k) + "." + (startCountL + l)
                        );
                    }
                }
            }
        }*/
    }

    private static int[] getMask(int[] startIPArr, int[] endIPArr) {
        int[] mask = new int[4];
        for (int m = 0; m < startIPArr.length; m++) {
            if (startIPArr[m] != endIPArr[m]) {
                mask[m] = endIPArr[m] - startIPArr[m];
            }
        }
        return mask;
    }

    private static void printIP(int countAll, int[] startIPArr) {
        for (int i = 0, countI = startIPArr[0], countJ = startIPArr[1], countK = startIPArr[2], countL = startIPArr[3] + 1;
             i < countAll; i++, countL++) {
            if (countL > 255) {
                countL = 0;
                countK++;
            }
            if (countK > 255) {
                countK = 0;
                countJ++;
            }
            if (countJ > 255) {
                countJ = 0;
                countI++;
            }
            if (countI > 255) {
                break;
            }
            System.out.println(
                    countI + "." +
                            countJ + "." +
                            countK + "." +
                            countL
            );
        }
    }
}
