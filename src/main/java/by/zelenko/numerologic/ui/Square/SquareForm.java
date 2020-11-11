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
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.format.DateTimeFormatter;

public class SquareForm extends FormLayout {

    private TextField firstName = new TextField("Фамилия");
    private TextField lastName = new TextField("Имя");
    private DatePicker birthDay = new DatePicker();
    private Button save = new Button("Сохранить");
    private Button choose = new Button("Рассчитать");
    private Binder<Client> binder = new BeanValidationBinder<>(Client.class);
    private Client client;
    private String date;

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
        choose.addClickListener(click -> getDate());
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

    public void getDate() {
            date = birthDay.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            fireEvent(new SquareForm.calculateEvent(this, date));
            System.out.println(date + "from form");
    }

    public static abstract class SquareFormEvent extends ComponentEvent<SquareForm> {
        private Client client;
        private String date;

        protected SquareFormEvent(SquareForm source, Client client) {
            super(source, false);
            this.client = client;
        }

        protected SquareFormEvent(SquareForm source, String date) {
            super(source, false);
            this.date = date;
        }

        public Client getClient() {
            return client;
        }

        public String getDate() {
            return date;
        }
    }

    public static class SaveEvent extends SquareForm.SquareFormEvent {
        SaveEvent(SquareForm source, Client client) {
            super(source, client);
        }
    }

    public static class calculateEvent extends SquareForm.SquareFormEvent {
        calculateEvent(SquareForm source, String date) {
            super(source, date);
        }
    }

    public static class DeleteEvent extends SquareForm.SquareFormEvent {
        DeleteEvent(SquareForm source, Client client) {
            super(source, client);
        }

    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
