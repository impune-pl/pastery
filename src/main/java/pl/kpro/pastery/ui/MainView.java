package pl.kpro.pastery.ui;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kpro.pastery.backend.data.entity.Paste;
import pl.kpro.pastery.backend.data.entity.User;
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
@PageTitle("Pastery")
public class MainView extends FlexLayout
{

}

