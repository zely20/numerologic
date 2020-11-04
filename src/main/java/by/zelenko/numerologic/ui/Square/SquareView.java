package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.ui.Client.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
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
    private HorizontalLayout r1 = new HorizontalLayout();
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
        Component component = drawSquare();
        r1.add(component);
        add(header,r1);
    }

    //рисуем квадрат

    public Component drawSquare(){
        VerticalLayout root = new VerticalLayout();
        root.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        HorizontalLayout lineOne  = new HorizontalLayout();
        lineOne.setClassName("line_table");
        lineOne.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Label label = new Label("Число судьбы");
        Label label2 = new Label("Доп числа");
        Label label3 = new Label("");
        Label label4 = new Label("Темперамент");
        lineOne.add(label, label2, label3, label4);

        HorizontalLayout lineTwo  = new HorizontalLayout();
        lineTwo.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        HorizontalLayout lineThree  = new HorizontalLayout();
        lineThree.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        HorizontalLayout lineFour  = new HorizontalLayout();
        lineFour.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        root.add(lineOne);
        return root;
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
        r1.addComponentAsFirst(form);
       // addComponentAtIndex(1, form);
    }
}
