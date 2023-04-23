package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getNewUser;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.getRandomPassword;
import static ru.netology.data.DataGenerator.getRandomUserName;

public class LoginFormTest {
    @BeforeEach

    public void setUp() {

        open("http://localhost:9999");

    }

    @Test
    void theUserIsRegisteredAndNotBlocked() {                                      // пользователь зарегистрирован и не заблокирован

        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(Condition.text("Личный кабинет")).shouldHave(Condition.visible);
    }

    @Test
    void userNotRegistered() {                                                           // пользователь не зарегистрирован

        var notRegisteredUser = getNewUser("active");
        $("[data-test-id=login] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(Condition.text("Неверно указан логин или пароль"))
                .shouldHave(Condition.visible);
    }

    @Test
    void theUserIsBlocked() {                                                                    // пользователь заблокирован

        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=login] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(Condition.text("Пользователь заблокирован"))
                .shouldHave(Condition.visible);
    }

    @Test
    void wrongLogin() {                                                                                // неверно введен логин

        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomUserName();
        $("[data-test-id=login] .input__control").setValue(wrongLogin);
        $("[data-test-id=password] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(Condition.text("Неверно указан логин или пароль"))
                .shouldHave(Condition.visible);
    }

    @Test
    void wrongPassword() {                                                                               // неверно введен пароль

        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=login] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(wrongPassword);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(Condition.text("Неверно указан логин или пароль"))
                .shouldHave(Condition.visible);
    }
}

