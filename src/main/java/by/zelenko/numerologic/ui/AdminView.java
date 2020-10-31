package by.zelenko.numerologic.ui;

import by.zelenko.numerologic.backend.Model.Service.UserService;
import by.zelenko.numerologic.backend.Model.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("Admin")
public class AdminView extends VerticalLayout {

    private final UserService userService;
    private Grid<User> grid = new Grid<>(User.class);
    private TextField searchField = new TextField();

    public AdminView(@Autowired UserService userService){
        this.userService = userService;
        addClassName("admin-view");
        setSizeFull();
        configFilter();
        configGrid();
        add(searchField, grid);
        updateList();
    }

    private void configFilter() {
        searchField.setPlaceholder("Find by name ...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> updateList());
    }

    private void updateList() {
        grid.setItems(userService.findAll(searchField.getValue()));
    }

    private void configGrid(){
        addClassName("user-grid");
        setSizeFull();
        grid.setColumns("userName", "role", "status");
        grid.addColumn(
                new NativeButtonRenderer<>("Remove item",
                        clickedItem -> {
                            userService.delete(grid.asSingleSelect().getValue());
                            updateList();
                        })
        );
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
