package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.ui.Client.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;
import java.util.Map;

@Route(value = "square", layout = MainView.class)
@PageTitle("квадрат | Numerology")
public class SquareView extends VerticalLayout implements AfterNavigationObserver,HasUrlParameter<Long> {

    private HorizontalLayout header = new HorizontalLayout();
    private H1 h1 = new H1("Квадрат");
    private SquareForm form = new SquareForm();
    private final ClientService clientService;
    public Client client;
    public Long id;

    public SquareView(ClientService clientService) {
        this.clientService = clientService;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        form.addListener(SquareForm.SaveEvent.class, this::saveClient);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        header.add(h1);
        add(header);
    }

    //получаем  id из URL
    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();
        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();
        if (!parametersMap.isEmpty()) {
            id = Long.valueOf(parametersMap.get("id").get(0));
        }
    }

    private void setClient (Long id){
        this.client = clientService.geyById(id);
    }
    //При открытие страницы квадрат добавляется новый пустой клиент нужно что-то с этим придумать
    private void saveClient(SquareForm.SaveEvent event) {
        clientService.save(event.getClient());
        Notification.show("Saved");
    }

  @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if(id != null){
            setClient(id);
            form.setClient(client);
        } else {
            form.setClient(new Client());
        }
        addComponentAtIndex(1, form);
    }
}
