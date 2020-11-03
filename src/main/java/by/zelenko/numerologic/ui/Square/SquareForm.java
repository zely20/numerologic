package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.shared.Registration;

public class SquareForm extends FormLayout {

    private TextField firstName = new TextField("Фамилия");
    private TextField lastName = new TextField("Имя");
    private DatePicker birthDay = new DatePicker();
    private Button save = new Button("Сохранить");
    private Button choose = new Button("Рассчитать");
    private Binder<Client> binder = new BeanValidationBinder<>(Client.class);
    private Client client;

    public SquareForm() {
        addClassName("client-form");
        binder.bindInstanceFields(this);
        birthDay.setLabel("Дата Рождения");
        birthDay.setClearButtonVisible(true);
        add(    firstName,
                lastName,
                birthDay,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        setWidth("30%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        choose.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        binder.forField(firstName)
                .withValidator(Validator.from((String value) -> value != null, (ValueContext ctx) -> "Введите Фамилию"))
                .bind(Client::getFirstName, Client::setFirstName);
        binder.forField(lastName)
                .withValidator(Validator.from((String value) -> value != null, (ValueContext ctx) -> "Введите Имя"))
                .bind(Client::getLastName, Client::setLastName);
        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        save.addClickListener(click -> validateAndSave());
        return new HorizontalLayout(save, choose);
    }

    public void setClient(Client client) {
        this.client = client;
        binder.readBean(client);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(client);
            fireEvent(new SquareForm.SaveEvent(this, client));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class SquareFormEvent extends ComponentEvent<SquareForm> {
        private Client client;

        protected SquareFormEvent(SquareForm source, Client client) {
            super(source, false);
            this.client = client;
        }

        public Client getClient() {
            return client;
        }
    }

    public static class SaveEvent extends SquareForm.SquareFormEvent {
        SaveEvent(SquareForm source, Client client) {
            super(source, client);
        }
    }

    public static class DeleteEvent extends SquareForm.SquareFormEvent {
        DeleteEvent(SquareForm source, Client client) {
            super(source, client);
        }

    }

    public static class CloseEvent extends SquareForm.SquareFormEvent {
        CloseEvent(SquareForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
