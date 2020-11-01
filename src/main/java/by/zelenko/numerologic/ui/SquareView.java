package by.zelenko.numerologic.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "square", layout = MainView.class)
@PageTitle("квадрат | Numerology")
public class SquareView extends VerticalLayout {

    private HorizontalLayout header = new HorizontalLayout();
    Label label;
    private H1 h1 = new H1("Список Клиентов");
    public SquareView() {
        label = new Label("fskljfsl");
        header.add(h1);
        add(header);
    }
}