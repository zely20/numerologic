package by.zelenko.numerologic.ui;

import by.zelenko.numerologic.backend.Entity.User;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.backend.Service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value="clients", layout = MainView.class)
//
@PageTitle("Клиенты | Numerology")
public class ListView extends VerticalLayout {

    private final ClientService clientService;
    private Grid<User> grid = new Grid<>(User.class);
    private TextField searchField = new TextField();
    private H1 h1 = new H1("Список Пользователей");
    private HorizontalLayout header = new HorizontalLayout();
    private UserForm form;

    public AdminView(@Autowired UserService userService) {
        setHorizontalComponentAlignment(Alignment.CENTER,header);
        this.userService = userService;
        addClassName("admin-view");
        header.add(h1);
        setSizeFull();

        form = new UserForm();
        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        configGrid();
        add(header, getToolbar(), content);
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        searchField.setPlaceholder("Filter by name...");
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(searchField, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addContact() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }

    private void updateList() {
        grid.setItems(userService.findAll(searchField.getValue()));
    }

    private void configGrid() {
        addClassName("user-grid");

        setSizeFull();
        grid.setColumns("userName", "role", "status");
        grid.addColumn(
                new NativeButtonRenderer<>("Удалить пользователя",
                        clickedItem -> {
                            userService.delete(grid.asSingleSelect().getValue());
                            updateList();
                        })
        );
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    public void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setVisible(true);
            form.setUser(user);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveUser(UserForm.SaveEvent event) {
        userService.save(event.getUser());
        updateList();
        closeEditor();
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        userService.delete(event.getUser());
        updateList();
        closeEditor();
    }

}
