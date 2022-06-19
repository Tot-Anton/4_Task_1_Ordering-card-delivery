package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderingCardDeliveryTest {


    //LocalDate - это неизменяемый объект даты и времени, который представляет дату, часто рассматриваемую как год-месяц-день.
    // Также можно получить доступ к другим полям даты, таким как день года, день недели и неделя года.
    //plusDays - этот метод добавляет указанную сумму в поле дней
    LocalDate currentDate = LocalDate.now().plusDays(3);

    //Этот метод создаст средство форматирования на основе простого набора букв и символов, как описано в документации к классу.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    String dateOfTheMeeting = currentDate.format(formatter);


    @BeforeEach
    public void openingPage() {
        open("http://localhost:9999");
    }

    @Test
    //корректное заполнение полей
    public void correctFillingOfFields() {
        $("[data-test-id=city] input").setValue("Владимир");
        $("[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(dateOfTheMeeting);
        $("[data-test-id=name] input").setValue("Пушкин Александр");
        $("[data-test-id=phone] input").setValue("+79200001100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        //shouldBe - подождите, пока данный элемент не удовлетворит заданному условию (с заданным таймаутом)
        //Duration.ofSeconds - получает длительность, представляющую количество секунд
        $("[data-test-id=notification]").shouldBe(appear, Duration.ofSeconds(15));
        //shouldHave - подождите, пока данный элемент не удовлетворит заданному условию (с заданным таймаутом)
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + dateOfTheMeeting));
    }

    @Test
    //Тестирование функциональности (не указываем телефон)
    public void testedFunctionalityV1() {
        $("[data-test-id=city] input").setValue("Владимир");
        $("[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(dateOfTheMeeting);
        $("[data-test-id=name] input").setValue("Пушкин Александр");
        //$("[data-test-id=phone] input").setValue("+79200001100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    //Тестирование функциональности (не указываем имя фамилия)
    public void testedFunctionalityV2() {
        $("[data-test-id=city] input").setValue("Владимир");
        $("[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(dateOfTheMeeting);
        //$("[data-test-id=name] input").setValue("Пушкин Александр");
        $("[data-test-id=phone] input").setValue("+79200001100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    //Тестирование функциональности (не указываем город)
    public void testedFunctionalityV3() {
        //$("[data-test-id=city] input").setValue("Владимир");
        $("[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(dateOfTheMeeting);
        $("[data-test-id=name] input").setValue("Пушкин Александр");
        $("[data-test-id=phone] input").setValue("+79200001100");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }




    @Test
    //Тестирование функциональности (чек-бокс не отмечен)
    public void testedFunctionalityV10() {

    }


}
