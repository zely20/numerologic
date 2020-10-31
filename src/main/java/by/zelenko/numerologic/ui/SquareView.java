package by.zelenko.numerologic.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "square", layout = MainView.class)
@PageTitle("квадрат | Numerologic")
public class SquareView extends VerticalLayout {
   Grid grid = new Grid();


    public SquareView() {
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn("name");
        grid.addColumn("born");
        add(grid);
    }
}