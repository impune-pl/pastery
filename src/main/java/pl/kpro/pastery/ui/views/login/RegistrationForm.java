package pl.kpro.pastery.ui.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;

/**
 * Registration form for LogiView
 * TODO: Validate user data
 * TODO: register new user in database
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
public class RegistrationForm extends FormLayout
{
    private EmailField emailField;
    private TextField usernameField;
    private PasswordField mainPasswordField;
    private PasswordField confirmPasswordField;
    private Checkbox eulaAndTosCheckbox;
    private Button applyButton;
    private Element formElement;
    private Element ironForm;

    public RegistrationForm()
    {
        emailField = new EmailField("Email");

        usernameField = new TextField("Username");

        mainPasswordField = new PasswordField("Password");

        confirmPasswordField = new PasswordField("Confirm Password");

        eulaAndTosCheckbox = new Checkbox(false);
        eulaAndTosCheckbox.setLabelAsHtml("Accept the EULA AND TOS");

        applyButton = new Button("Create account");
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        applyButton.setId("applyButton");
        applyButton.setEnabled(false);

        this.add(
                emailField,
                usernameField,
                mainPasswordField,
                confirmPasswordField,
                eulaAndTosCheckbox,
                applyButton
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
