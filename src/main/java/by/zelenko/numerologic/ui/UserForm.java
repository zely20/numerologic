package by.zelenko.numerologic.ui;

import by.zelenko.numerologic.backend.Model.Role;
import by.zelenko.numerologic.backend.Model.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UserForm extends FormLayout {

    TextField firstName = new TextField("User Name");
    TextField lastName = new TextField("Password");
    ComboBox<Status> status = new ComboBox<>("Status");
    ComboBox<Role> company = new ComboBox<>("Role");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public UserForm() {
    }
}
