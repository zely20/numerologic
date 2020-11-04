package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.ui.Client.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.flow.helper.HasUrlParameterMapping;
import org.vaadin.flow.helper.UrlParameter;
import org.vaadin.flow.helper.UrlParameterMapping;

@Route(value = "square", layout = MainView.class)
@PageTitle("квадрат | Numerology")
@UrlParameterMapping(queryParameters = {"id=:id"})
public class SquareView extends VerticalLayout implements HasUrlParameterMapping/*HasUrlParameter<String>*/ {

    private HorizontalLayout header = new HorizontalLayout();
    private H1 h1 = new H1("Квадрат");
    private SquareForm form = new SquareForm();
    private final ClientService clientService;
    private Client client;
    @UrlParameter
    private Long id;

    public SquareView(ClientService clientService) {
        this.clientService = clientService;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        form.addListener(SquareForm.SaveEvent.class, this::saveClient);
        System.out.println(id);
        //form.setClient(new Client());
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        header.add(h1);
        add(header, form);
    }

    //получаем  id из URL
    /*@Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();
        Map<String, List<String>> parametersMap =
                queryParameters.getParameters();
        if(!parametersMap.isEmpty()) {
            id = Long.valueOf(parametersMap.get("id").get(0));
            setClient(id);
            //form.setClient(client);
            System.out.println(client);
        } else {
            form.setClient(new Client());
        }
    }*/

    private void setClient (Long id){
        this.client = clientService.geyById(id);
    }
    //При открытие страницы квадрат добавляется новый пустой клиент нужно что-то с этим придумать
    private void saveClient(SquareForm.SaveEvent event) {
        clientService.save(event.getClient());
        Notification.show("Saved");
    }

}