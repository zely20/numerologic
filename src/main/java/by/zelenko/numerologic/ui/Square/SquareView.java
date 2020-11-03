package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.ui.Client.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;
import java.util.Map;

@Route(value = "square", layout = MainView.class)
@PageTitle("квадрат | Numerology")
public class SquareView extends VerticalLayout implements HasUrlParameter<String> {

    private HorizontalLayout header = new HorizontalLayout();
    private H1 h1 = new H1("Квадрат");
    private SquareForm form;
    private final ClientService clientService;
    private Client client;
    private Long id;

    public SquareView(ClientService clientService) {
        this.clientService = clientService;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        form = new SquareForm();
        form.addListener(SquareForm.SaveEvent.class, this::saveClient);
        form.setClient(new Client());
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        header.add(h1);

        add(header, form);
    }

    //олучаем  id из URL
    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();

        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();
        if(!parametersMap.isEmpty()) {
            id = Long.valueOf(parametersMap.get("id").get(0));
            System.out.println(id);
        }
    }

    private void setClient (Long id){
        this.client = clientService.geyById(id);
    }
    //При открытие страницы квадрат добавляется новый пустой клиент нужно что-то с этим придумать
    private void saveClient(SquareForm.SaveEvent event) {
        clientService.save(event.getClient());
    }

}