package gr.orestislef.roomhandling.utils;

import java.util.Random;

public class RandomTextGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TEXT_LENGTH = 10;

    public static void main(String[] args) {
        String randomText = generateRandomText(TEXT_LENGTH);
        System.out.println(randomText);
    }

    public static String generateRandomText(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
