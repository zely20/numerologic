package by.zelenko.numerologic.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.jboss.jandex.Main;

@Route("")
public class MainView extends VerticalLayout {

    Button button=new Button("I'm button");
    public MainView()   {
        button.addClickListener(clickEvent ->
                add(new Text("Clicked!")));
        add(button);
    }
}
