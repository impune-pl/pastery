package pl.kpro.pastery.ui.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Allows users to authenticate or proceed to RegistrationView or RecoveryView
 * TODO: customize login form
 *
 * @author Krzysztof 'impune_pl' Prorok
 */
@Route
public class LoginView extends VerticalLayout
{
    public LoginView()
    {
        LoginForm component = new LoginForm();
        component.setAction("/login");

    }
}
