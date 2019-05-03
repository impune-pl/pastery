package pl.kpro.pastery.ui.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.OptionalParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.kpro.pastery.backend.data.service.UserService;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Allows users to authenticate or register.
 * Validates user input in backend.
 * TODO: Add different errors to setParameter method
 * @author Krzysztof 'impune_pl' Prorok
 */
@Route("login")
@PageTitle("Login")
@HtmlImport("frontend://bower_components/iron-form/iron-form.html")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginAndRegistrationView extends FlexLayout implements HasUrlParameter<String>
{
    private final UserService userService;

    //If wrong password or e-mail have been provided (spring security will redirect user to /login/error), display error popup
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter)
    {
        if(parameter != null && parameter.equals("error"))
        {
            Dialog dialog = new Dialog();

            FlexLayout flexLayout = new FlexLayout();
            flexLayout.setAlignItems(Alignment.CENTER);
            flexLayout.setJustifyContentMode(JustifyContentMode.CENTER);
            flexLayout.getStyle().set("flex-direction","column");

            flexLayout.add(new Label("Wrong e-mail or password."));

            Button closeButton = new Button("Close", (e) -> { dialog.close();});
            flexLayout.add(closeButton);

            dialog.add(flexLayout);
            dialog.setCloseOnEsc(false);
            dialog.setCloseOnOutsideClick(false);

            this.add(dialog);

            dialog.open();
        }
    }
    @Autowired
    public LoginAndRegistrationView(UserService userService)
    {
        this.userService = userService;
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.getStyle().set("flex-direction","column");

        LoginForm loginForm = new LoginForm();
        Element loginIronForm = loginForm.getIronForm();

        RegistrationForm registrationForm = new RegistrationForm(userService);
        Element registrationIronForm = registrationForm.getIronForm();

        Tab loginTab = new Tab("Log in");
        Tab registrationTab = new Tab("Register");

        Tabs tabs = new Tabs(loginTab,registrationTab);
        tabs.setWidth("350px");
        tabs.setFlexGrowForEnclosedTabs(1);

        Div forms = new Div();
        forms.getElement().appendChild(loginIronForm);
        forms.getElement().appendChild(registrationIronForm);
        forms.setMaxWidth("350px");
        forms.setMinHeight("40%");

        HashMap<Tab,Element> tabsToForms = new HashMap<>();
        tabsToForms.put(loginTab, loginIronForm);
        tabsToForms.put(registrationTab, registrationIronForm);

        Set<Element> formsShown = Stream.of(loginIronForm).collect(Collectors.toSet());

        tabs.addSelectedChangeListener( e -> {
            formsShown.forEach(form -> form.setVisible(false));
            formsShown.clear();
            Element selectedForm = tabsToForms.get(tabs.getSelectedTab());
            selectedForm.setVisible(true);
            formsShown.add(selectedForm);
        });
        registrationIronForm.setVisible(false);
        loginIronForm.setVisible(true);
        this.add(tabs);
        this.add(forms);
    }

}
