package com.hlb;

import java.text.SimpleDateFormat;
import java.util.*;

class JavaTest {
     // E.g., Input: Automation, Output: noitamotuA
    public static String reverseStringWithoutUsingStringMethod(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

      // Sort the integer in ASC order without using the built-in method such as ArrayUtils.sort
    public static Integer[] sortIntegers(Integer[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    public static boolean isInDateRange(Date givenDate, Date startDate, Date endDate) {
        return !(givenDate.before(startDate) || givenDate.after(endDate));
    }

   // sort the given String in ASC order without using method like Arrays.sort
    public static char[] sortStringInAscOrder(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = 0; j < chars.length - i - 1; j++) {
                if (chars[j] > chars[j + 1]) {
                    char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                }
            }
        }
        return chars;
    }

    public static char lowestOccurrence(String input) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : input.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        int minFreq = Integer.MAX_VALUE;
        for (int val : freq.values()) {
            if (val < minFreq) {
                minFreq = val;
            }
        }

        List<Character> result = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == minFreq) {
                result.add(entry.getKey());
            }
        }

        Collections.sort(result); 
        return result.get(0);
    }

    public static double solveEquations(double input, String[] equations) {
        double x = input;
        for (String eq : equations) {
            String replaced = eq.replace("x", Double.toString(x));
            x = eval(replaced);
        }
        return x;
    }

    private static double eval(String expr) {
        try {
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < expr.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    for (;;) {
                        if (eat('+')) x += parseTerm();
                        else if (eat('-')) x -= parseTerm();
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    for (;;) {
                        if (eat('*')) x *= parseFactor();
                        else if (eat('/')) x /= parseFactor();
                        else return x;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return parseFactor();
                    if (eat('-')) return -parseFactor();

                    double x;
                    int startPos = this.pos;
                    if ((ch >= '0' && ch <= '9') || ch == '.') {
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(expr.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }

                    return x;
                }
            }.parse();
        } catch (Exception e) {
            System.out.println("Error evaluating expression: " + expr);
            return 0.0;
        }
    }

    public static void main(String[] args) {
        System.out.println("Test 1: " + reverseStringWithoutUsingStringMethod("Automation"));

        Integer[] intArray = new Integer[] { 10, 12, 54, 1, 2, -9, 8 };
        Integer[] sortedArray = sortIntegers(intArray);
        System.out.print("Test 2: ");
        for (Integer i : sortedArray) {
            System.out.print(i + ", ");
        }

        System.out.println();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date startDate = sdf.parse("2024-12-01 13:09:22");
            Date endDate = sdf.parse("2025-01-09 20:10:12");
            Date givenDate = sdf.parse("2025-02-02 00:11:22");
            System.out.println("Test 3: " + isInDateRange(givenDate, startDate, endDate));
        } catch (Exception e) {
            System.out.println(e);
        }

        char[] sorted = sortStringInAscOrder("testingNG311");
        System.out.print("Test 4 :");
        for (char c : sorted) {
            System.out.print(c + ", ");
        }
        System.out.println();
        System.out.println("Test 5: " + lowestOccurrence("Abc1dd23affbc1ee23u3278"));
        System.out.print("Test 6: ");
        double calculated = solveEquations(3.4, new String[] { "x*x", "x-10/2.2", "x+4-10", "x+5*8" });
        System.out.print("calculated = " + calculated);
    }
}
