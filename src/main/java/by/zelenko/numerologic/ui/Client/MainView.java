package by.zelenko.numerologic.ui.Client;

import by.zelenko.numerologic.ui.Square.SquareView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("База Клиентов", ListView.class);
        RouterLink squareLink = new RouterLink("Квадрат", SquareView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(listLink), new VerticalLayout(squareLink));
    }

    private void createHeader() {
        H1 logo = new H1("Numerology");
        logo.addClassName("logo");
        Label label = new Label("Name of User");
        label.addClassName("label");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, label);
        header.setSpacing(true);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }
}
