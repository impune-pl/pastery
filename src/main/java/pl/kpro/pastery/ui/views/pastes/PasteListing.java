package pl.kpro.pastery.ui.views.pastes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kpro.pastery.app.security.CurrentUser;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;
import pl.kpro.pastery.backend.data.service.PasteService;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Paste listing - allows user to browse his pastes, delete them
 * create new pastes and edit choosen paste.
 *
 * @author Krzysztof 'impune_pl' Prorok
 */

@Route("user/pastes/list")
@PageTitle("Pastery")
public class PasteListing extends FlexLayout
{
    PasteService pasteService;

    //TODO: check if it's valid way to load user
    //TODO: what if user is not logged in? -- security?
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
        pastesGrid.setDataProvider( DataProvider.fromCallbacks(
                query ->
                {
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort->sort.getSorted(),
                                    sort->sort.getDirection() == SortDirection.ASCENDING)
                            );
                    return pasteService.findAllBetweenAndSortedBy(query.getOffset(), query.getLimit(), sortOrder, this.currentUser).stream();
                },
                query -> pasteService.countLoadable(this.currentUser)
        ));

        pastesGrid.removeColumnByKey("author");
        pastesGrid.removeColumnByKey("id");
        pastesGrid.removeColumnByKey("version");
        pastesGrid.removeColumnByKey("content");

        pastesGrid.setColumns("title","creationDate");
        pastesGrid.setColumnReorderingAllowed(false);

        pastesGrid.addColumn(new ComponentRenderer<>(
                paste -> new Button("Update",
                                        event -> {
                                            edit(paste);
                                            pastesGrid.getDataProvider().refreshItem(paste);
                                        })
        )).setHeader("Actions");

        this.add(pastesGrid);
    }

    private void edit( Paste paste)
    {

    }
}
