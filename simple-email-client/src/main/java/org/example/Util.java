package org.example;

import java.text.DecimalFormat;

public class Util {

    /**
     *
     */

    public static long usedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     *
     */

    public static String formatSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        String template = "#,##0.#";
        //String template = "#,##0.##";
        //String template = "#,##0";
        return new DecimalFormat(template).format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
