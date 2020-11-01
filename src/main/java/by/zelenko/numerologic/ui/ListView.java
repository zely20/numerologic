package by.zelenko.numerologic.ui;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value="clients", layout = MainView.class)
//
@PageTitle("Клиенты | Numerology")
public class ListView extends VerticalLayout {

    private final ClientService clientService;
    private Grid<Client> grid = new Grid<>(Client.class);
    private TextField searchField = new TextField();
    private H1 h1 = new H1("Список Клиентов");
    private HorizontalLayout header = new HorizontalLayout();
    private ClientForm form;

    public ListView(@Autowired ClientService clientService) {
        setHorizontalComponentAlignment(Alignment.CENTER,header);
        this.clientService = clientService;
        addClassName("admin-view");
        header.add(h1);
        setSizeFull();

        form = new ClientForm();
        form.addListener(ClientForm.SaveEvent.class, this::saveUser);
        form.addListener(ClientForm.DeleteEvent.class, this::deleteUser);
        form.addListener(ClientForm.CloseEvent.class, e -> closeEditor());

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
        editClient(new Client());
    }

    private void updateList() {
        grid.setItems(clientService.findAll(searchField.getValue()));
    }

    private void configGrid() {
        addClassName("user-grid");

        setSizeFull();
        grid.setColumns("firstName", "lastName", "birthDay");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editClient(event.getValue()));
    }

    public void editClient(Client client) {
        if (client == null) {
            closeEditor();
        } else {
            form.setVisible(true);
            form.setClient(client);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setClient(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveUser(ClientForm.SaveEvent event) {
        clientService.save(event.getClient());
        updateList();
        closeEditor();
    }

    private void deleteUser(ClientForm.DeleteEvent event) {
        clientService.delete(event.getClient());
        updateList();
        closeEditor();
    }

}
