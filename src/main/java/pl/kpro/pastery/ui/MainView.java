package pl.kpro.pastery.ui;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.service.PasteService;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Main application - starting page.
 * Shows different content to authenticated and unauthenticated users
 *
 * @author Krzysztof 'impune_pl' Prorok
 */
@Route("")
public class MainView extends FlexLayout
{
    @Autowired
    PasteService pasteService;

    public MainView()
    {
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
                    return pasteService.findAll(query.getOffset(), query.getLimit(), sortOrder).stream();
                },
                query -> pasteService.countInt()
        ));
    }


}

