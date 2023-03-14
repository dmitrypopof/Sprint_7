package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierNotLogin;
import ru.yandex.praktikum.model.CourierNotPassword;

public class CourierGenerator {
    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static CourierNotPassword getRandomNotPassword() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierNotPassword(login,firstName);
    }

    public static CourierNotLogin getRandomNotLogin() {
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierNotLogin(password, firstName);
    }


}
