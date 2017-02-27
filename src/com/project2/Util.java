package com.project2;

import java.util.Scanner;

/**

 */
public class Util {

    /**
     * @param scanner
     * @param allowZero
     * @return an int read from command prompt/line
     */
    public static int readInt(Scanner scanner, boolean allowZero) {
        int sid = 0;
        while (sid == 0) {
            try {
                sid = scanner.nextInt();
                if (sid == 0 && allowZero) {
                    return sid;
                } else if (sid == 0 && !allowZero) {
                    System.out.println("Invalid Input. Enter a number > 0");
                    System.out.println("");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Invalid Input. Try Again");
                scanner.nextLine();
            }
        }
        return sid;
    }
}
