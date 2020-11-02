package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.ui.Client.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "square", layout = MainView.class)
@PageTitle("квадрат | Numerology")
public class SquareView extends VerticalLayout {

    private HorizontalLayout header = new HorizontalLayout();
    private H1 h1 = new H1("Квадрат");
    private SquareForm form;
    private final ClientService clientService;
    private Client client;

    public SquareView(ClientService clientService) {
        this.clientService = clientService;

        form = new SquareForm();
        form.addListener(SquareForm.SaveEvent.class, this::saveClient);
        form.setClient(new Client());
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        header.add(h1);
        add(header, form);
    }
    //При открытие страницы квадрат добавляется новый пустой клиент нужно что-то с этим придумать
    private void saveClient(SquareForm.SaveEvent event) {

        clientService.save(event.getClient());

    }
}