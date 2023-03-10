package model;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    private static String loginGen = RandomStringUtils.randomAlphabetic(10);
    private static String loginSecondGen = RandomStringUtils.randomAlphabetic(10);
    private static String passwordGen = RandomStringUtils.randomAlphabetic(10);
    private static String passwordSecondGen = RandomStringUtils.randomAlphabetic(10);
    private static String firstNameGen = RandomStringUtils.randomAlphabetic(10);
    public static final String jsonNewCourier = "{\n" +
            "    \"login\": \"" + loginGen + "\",\n" +
            "    \"password\": \"" + passwordGen + "\",\n" +
            "    \"firstName\": \"" + firstNameGen + "\"\n" +
            "}";
    public static final String jsonNewCourierWithoutLogin  = "{\n" +
            "    \"password\": \""+ loginGen +"\",\n" +
            "    \"firstName\": \""+firstNameGen+"\"\n" +
            "}";
    public static final String jsonNewCourierWithoutPassword = "{\n" +
            "    \"login\": \""+ loginGen +"\",\n" +
            "    \"firstName\": \""+firstNameGen+"\"\n" +
            "}";
    public static final String jsonNewCourierIncorrectLogin = "{\n" +
            "    \"login\": \""+ loginSecondGen +"\",\n" +
            "    \"password\": \""+ passwordGen +"\"\n" +
            "}";
    public static final String jsonNewCourierIncorrectPassword = "{\n" +
            "    \"login\": \""+loginGen+"\",\n" +
            "    \"password\": \""+passwordSecondGen+"\"\n" +
            "}";

}
