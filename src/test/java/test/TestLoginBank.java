package test;

import data.DataHelper;
import data.SqlHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SqlHelper.cleanDataBase;

public class TestLoginBank {

    @AfterAll
    static void cleanUP() {
        cleanDataBase();
    }

    @Test
    @DisplayName("Positive")
    void shouldLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoFromTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisible();
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("WrongPasswordThreeTimes")
    void shouldBlock() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var wrongUser = DataHelper.generataRandomUser();
        loginPage.validLogin(wrongUser);
        loginPage.verificationNoticeErrorVisibility();
        loginPage.validLogin(wrongUser);
        loginPage.verificationNoticeErrorVisibility();
        loginPage.validLogin(wrongUser);
        loginPage.verificationNoticeErrorVisibility();
        loginPage.validLogin(wrongUser);
        loginPage.blockNotice();

    }

    @Test
    @DisplayName("WrongCode")
    void shouldNoticeWrongCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoFromTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisible();
        var verificationCode = DataHelper.generataRandomUser().getPassword();
        verificationPage.verify(verificationCode);
        verificationPage.errorMessageVisible();
    }

}
