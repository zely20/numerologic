package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Service.ClientService;
import by.zelenko.numerologic.ui.Client.MainView;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.Component;
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
    //private H1 h1 = new H1("Квадрат");
    private SquareForm form = new SquareForm();
    private final ClientService clientService;
    public Client client;
    public Long id;

    public SquareView(ClientService clientService) {
        this.clientService = clientService;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        form.addListener(SquareForm.SaveEvent.class, this::saveClient);
        form.addListener(SquareForm.calculateEvent.class, this::getDate);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        //header.add(h1);
        Component component = drawSquare();
        r1.add(component);
        add(r1);
    }
    //пытаемся взять дату
    private void getDate(SquareForm.calculateEvent event) {
        System.out.println(event.getDate() + "from View");
    }

    //рисуем квадрат
    public Component drawSquare(){
        VerticalLayout root = new VerticalLayout();
        root.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        HorizontalLayout lineOne  = new HorizontalLayout();
        lineOne.setClassName("line_table");
        lineOne.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card11  = new Card(new TitleLabel("Число судьбы").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"));
        Card card12  = new Card(new TitleLabel("Доп числа").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text2"));
        Card card13 = new Card();
        card13.setWidth("200px");
        Card card14  = new Card(new TitleLabel("Темперамент").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text4"));
        lineOne.add(card11, card12, card13, card14);

        HorizontalLayout lineTwo  = new HorizontalLayout();
        lineTwo.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card21  = new Card(new TitleLabel("Характер").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"));
        Card card22  = new Card(new TitleLabel("Здоровье").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text2"));
        Card card23 = new Card(new TitleLabel("Удача").withWhiteSpaceNoWrap(),
                new PrimaryLabel("3"));
        card23.setWidth("200px");
        Card card24  = new Card(new TitleLabel("Цель").withWhiteSpaceNoWrap(),
                new PrimaryLabel("10"));
        card24.setWidth("200px");
        lineTwo.add(card21,card22,card23,card24);

        HorizontalLayout lineThree  = new HorizontalLayout();
        lineThree.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card31  = new Card(new TitleLabel("Энергия").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"));
        Card card32  = new Card(new TitleLabel("Логика").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text2"));
        Card card33 = new Card(new TitleLabel("Долг").withWhiteSpaceNoWrap(),
                new PrimaryLabel("3"));
        card33.setWidth("200px");
        Card card34  = new Card(new TitleLabel("Семья").withWhiteSpaceNoWrap(),
                new PrimaryLabel("10"));
        card34.setWidth("200px");
        lineThree.add(card31,card32,card33,card34);

        HorizontalLayout lineFour  = new HorizontalLayout();
        lineFour.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card41  = new Card(new TitleLabel("Энергия").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"));
        Card card42  = new Card(new TitleLabel("Логика").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text2"));
        Card card43 = new Card(new TitleLabel("Долг").withWhiteSpaceNoWrap(),
                new PrimaryLabel("3"));
        card43.setWidth("200px");
        Card card44  = new Card(new TitleLabel("Семья").withWhiteSpaceNoWrap(),
                new PrimaryLabel("10"));
        card44.setWidth("200px");
        lineFour.add(card41,card42,card43,card44);

        HorizontalLayout lineFive  = new HorizontalLayout();
        lineFour.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card51  = new Card(new TitleLabel("Быт").withWhiteSpaceNoWrap(),
                new PrimaryLabel("Some primary text"));
        card51.setWidth("200px");
        lineFive.add(card51);

        root.add(lineOne, lineTwo, lineThree, lineFour, lineFive);
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
    }
}
