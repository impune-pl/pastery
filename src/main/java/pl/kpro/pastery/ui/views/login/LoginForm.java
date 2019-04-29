package pl.kpro.pastery.ui.views.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.dom.Element;

/**
 * Login form for LoginAndRegistrationView
 * TODO: add remember me functionality
 *
 * TODO: important: for some reason when logged in user enters page, gets error message.
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
class LoginForm extends FormLayout
{
    private EmailField emailField;
    private PasswordField passwordField;
    private Button applyButton;
    private Button resetPasswordButton;
    private Element formElement;
    private Element ironForm;

    public LoginForm()
    {
        emailField = new EmailField("E-mail");

        passwordField = new PasswordField("Password");

        applyButton = new Button("Log in");
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        applyButton.setId("applyButton");
        applyButton.addClickListener(
                e -> UI.getCurrent().getPage().executeJavaScript("document.getElementById('ironForm').submit();")
        );

        resetPasswordButton = new Button("Forgot password");
        resetPasswordButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        this.add(
                emailField,
                passwordField,
                applyButton,
                resetPasswordButton
        );

        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("0",1)
        );

        formElement = new Element("form");
        formElement.setAttribute("method", "post");
        formElement.setAttribute("action", "login");
        formElement.appendChild(this.getElement());

        ironForm = new Element("iron-form");
        ironForm.setAttribute("id", "ironForm");
        ironForm.setAttribute("allow-redirect", true);
        ironForm.appendChild(formElement);
    }

    public Element getIronForm()
    {
        return ironForm;
    }
}
