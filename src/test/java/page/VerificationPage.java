package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {
    @FindBy(css = "[data-test-id=code] input")
    private SelenideElement codeField;
    @FindBy(css = "[data-test-id=action-verify]")
    private SelenideElement verifyButton;
    @FindBy(css = "[data-test-id=error-notification]")
    private SelenideElement errorMessage;

    public void verificationPageVisible() {
        codeField.shouldBe(Condition.visible);
    }

    public void errorMessageVisible() {
        errorMessage.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return page(DashboardPage.class);

    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }

}
