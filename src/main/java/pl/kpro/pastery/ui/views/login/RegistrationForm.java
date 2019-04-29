package pl.kpro.pastery.ui.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.dom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kpro.pastery.backend.data.service.UserService;
import pl.kpro.pastery.backend.error.UserAlreadyExistsException;
import pl.kpro.pastery.ui.data.dtos.UserDto;

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
    private Binder<UserDto> binder;

    private UserService userService;

    public RegistrationForm(UserService userService)
    {
        this.userService=userService;

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
        applyButton.addClickListener((e)->
                                     {
                                         if(this.applyForm())
                                             this.displayRegistrationSuccessDialog();
                                         else
                                             this.displayRegistrationFailureDialog();
                                     });

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

        binder = new Binder<UserDto>(UserDto.class);
        binder.forField(emailField)
              .withValidator(new EmailValidator("Correct email address required"))
              .asRequired()
              .bind(UserDto::getEmail, UserDto::setEmail);
        binder.forField(usernameField)
              .withValidator(value -> value.matches("[a-zA-Z0-9]{8,254}"),"Username must be at least 8 and at most 254 characters long")
              .asRequired()
              .bind(UserDto::getUsername, UserDto::setUsername);
        binder.forField(mainPasswordField)
              .withValidator(value -> value.matches("[0-9#$%&'()*+,\\-./:;<=>?@\\[\\\\\\]^_`{|}!~\\p{L}]{10,254}"),"Password must be at least 10 characters long and identical in both fields.")
              .asRequired()
              .bind(user -> mainPasswordField.getEmptyValue(), UserDto::setPassword);
        binder.forField(confirmPasswordField)
              .withValidator(value -> value.equals(mainPasswordField.getValue()),"Password must be at least 10 characters long and identical in both fields.")
              .asRequired()
              .bind(value -> confirmPasswordField.getEmptyValue(), UserDto::setPasswordConfirmation);
        binder.forField(eulaAndTosCheckbox)
              .withValidator(value -> value, "You must read and agree to EULA and Terms Of Service before proceeding" )
              .asRequired()
              .bind(UserDto::getHasAgreedToEulaAndToS, UserDto::setHasAgreedToEulaAndToS);

        binder.addStatusChangeListener(event -> {
            if(binder.isValid())
                applyButton.setEnabled(true);
            else
                applyButton.setEnabled(false);
        });

    }

    private void displayRegistrationFailureDialog()
    {
        Dialog dialog = new Dialog();

        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        flexLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        flexLayout.getStyle().set("flex-direction","column");

        flexLayout.add(new Label("User account with this email address already exists"));

        Button closeButton = new Button("Close", (e) -> { dialog.close();});
        flexLayout.add(closeButton);

        dialog.add(flexLayout);
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        this.add(dialog);

        dialog.open();
    }

    private void displayRegistrationSuccessDialog()
    {
        Dialog dialog = new Dialog();

        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        flexLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        flexLayout.getStyle().set("flex-direction","column");

        flexLayout.add(new Label("Registration sucessfull. To activate account log in and follow instructions."));

        Button closeButton = new Button("Close", (e) -> { dialog.close();});
        flexLayout.add(closeButton);

        dialog.add(flexLayout);
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        this.add(dialog);

        dialog.open();
    }

    public Element getIronForm()
    {
        return ironForm;
    }

    private Boolean applyForm()
    {
        UserDto userDto = new UserDto();
        binder.writeBeanIfValid(userDto);
        try
        {
            userService.registerNewUser(userDto);
            return true;
        }catch(UserAlreadyExistsException e)
        {
            return false;
        }
    }

}
