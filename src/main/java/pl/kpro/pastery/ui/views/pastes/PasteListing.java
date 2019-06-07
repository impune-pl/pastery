package pl.kpro.pastery.ui.views.pastes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pl.kpro.pastery.app.security.CurrentUser;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.data.service.PasteService;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Paste listing - allows user to browse his pastes, delete them
 * create new pastes and edit chosen paste.
 *
 * @author Krzysztof 'impune_pl' Prorok
 */

@Route("user/pastes/list")
@PageTitle("Pastery")
@Secured("USER")
public class PasteListing extends FlexLayout
{
    PasteService pasteService;

    private User currentUser;

    @Autowired
    public PasteListing(PasteService pasteService, CurrentUser currentUser)
    {
        this.currentUser=currentUser.getUser();
        this.pasteService=pasteService;
        this.setAlignItems(Alignment.CENTER);
        this.setWidthFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.getStyle().set("flex-direction","column");

        Grid<Paste> pastesGrid = new Grid<>(Paste.class);
        pastesGrid.setDataProvider(DataProvider.fromCallbacks(
                query ->
                {
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                                                          .collect(Collectors.toMap(
                                                                  sort->sort.getSorted(),
                                                                  sort->sort.getDirection() == SortDirection.ASCENDING)
                                                          );
                    return pasteService
                                    .findAllBetweenAndSortedByAndOwnedBy(query.getOffset(), query.getLimit(), sortOrder,
                                                    this.currentUser).stream();
                },
                query -> pasteService.countLoadable(this.currentUser)
        ));

        pastesGrid.removeColumnByKey("author");
        pastesGrid.removeColumnByKey("id");
        pastesGrid.removeColumnByKey("version");
        pastesGrid.removeColumnByKey("content");

        pastesGrid.setColumns("title","creationDate");
        pastesGrid.setColumnReorderingAllowed(false);


        pastesGrid.addComponentColumn(item -> createEditButton(pastesGrid, item));
        pastesGrid.addComponentColumn(item -> createDeleteButton(pastesGrid, item));
        // TODO: create some kind of sharing system
        // pastesGrid.addComponentColumn(item -> createShareButton(pastesGrid,item));

    }

    private Button createDeleteButton(Grid<Paste> pastesGrid, Paste item)
    {
        Button button = new Button();
        Icon icon = new Icon(VaadinIcon.TRASH);
        icon.setColor("red");
        button.setIcon(icon);
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClickListener(event -> {
            Dialog dialog = new Dialog();
            FormLayout layout = new FormLayout();
            layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("0", 2));

            Label label = new Label("Press OK to delete paste permanently.");

            Button okButton = new Button("OK");
            okButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            okButton.addClickListener(event1 -> pasteService.delete(currentUser, item));

            Button cancelButton = new Button("Cancel");
            cancelButton.addClickListener(event1 -> dialog.close());

            layout.add(label, okButton, cancelButton);

            dialog.setCloseOnOutsideClick(false);
            dialog.setCloseOnEsc(false);
            dialog.add(layout);

            this.add(dialog);
            dialog.open();
        });
        return button;
    }

    private Button createEditButton(Grid<Paste> pastesGrid, Paste item)
    {
        Button button = new Button();
        Icon icon = new Icon(VaadinIcon.EDIT);
        icon.setColor("blue");
        button.setIcon(icon);
        button.addThemeVariants(ButtonVariant.LUMO_ICON);
        button.addClickListener(event -> showEditorPopup(item));
        return button;
    }

    private Paste showEditorPopup(Paste paste) {

    }
}
