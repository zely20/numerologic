package by.zelenko.numerologic.ui.Client;

import by.zelenko.numerologic.backend.Entity.Client;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.Registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientForm extends FormLayout {

    TextField firstName = new TextField("Фамилия");
    TextField LastName = new TextField("Имя");
    DatePicker birthDay = new DatePicker();

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Button choose = new Button("Выбрать");
    Binder<Client> binder = new BeanValidationBinder<>(Client.class);
    private Client client;

    public ClientForm() {
        addClassName("client-form");
        binder.bindInstanceFields(this);
        add(    firstName,
                LastName,
                birthDay,
                createButtonsLayout());


    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        choose.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, client)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));
        //передача пользователя в другой лояут
        choose.addClickListener(click -> {
                Map<String, List<String>> parameter = new HashMap<>();
                String id = client.getId().toString();
                parameter.put("id", List.of(id));
                QueryParameters queryParameters = new QueryParameters(parameter);
                this.getUI().ifPresent(ui -> ui.navigate("square", queryParameters));
        });

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close, choose);
    }

    public void setClient(Client client) {
        this.client = client;
        binder.readBean(client);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(client);
            fireEvent(new ClientForm.SaveEvent(this, client));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    // Events
    public static abstract class ClientFormEvent extends ComponentEvent<ClientForm> {
        private Client client;

        protected ClientFormEvent(ClientForm source, Client client) {
            super(source, false);
            this.client = client;
        }

        public Client getClient() {
            return client;
        }
    }

    public static class SaveEvent extends ClientForm.ClientFormEvent {
        SaveEvent(ClientForm source, Client client) {
            super(source, client);
        }
    }

    public static class DeleteEvent extends ClientFormEvent {
        DeleteEvent(ClientForm source, Client client) {
            super(source, client);
        }

    }

    public static class CloseEvent extends ClientForm.ClientFormEvent {
        CloseEvent(ClientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
