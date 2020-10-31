package by.zelenko.numerologic.ui;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.Date;

@Route("Admin")
public class AdminView extends VerticalLayout {

    public AdminView(){
        DatePicker labelDatePicker = new DatePicker();
        labelDatePicker.setLabel("Label");
        DatePicker placeholderDatePicker = new DatePicker();
        placeholderDatePicker.setPlaceholder("Placeholder");
        DatePicker valueDatePicker = new DatePicker();
        LocalDate now = LocalDate.now();
        valueDatePicker.setValue(now);
        add(labelDatePicker, placeholderDatePicker, valueDatePicker);
    }
}
