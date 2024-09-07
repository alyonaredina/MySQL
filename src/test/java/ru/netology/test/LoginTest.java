package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import javax.management.Notification;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class LoginTest {
    LoginPage loginPage;
    @AfterEach
    void tearDown(){
        cleanAuthCodes();
    }
    @AfterAll
    static void tearDownAll(){
        cleanDatabase();
    }
    @BeforeEach
    void setUp(){
        loginPage = open("http://localhost:9999", LoginPage.class);
    }
    @Test
    @DisplayName ("Should successfully login to dashboard with exist login and password from sut test data")
    void  shouldSuccessfulLogin(){
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }
    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase(){
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }
    @Test
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode(){
        var authInfo = DataHelper.getAuthInfoWithTestData();
    }
}
