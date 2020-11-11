package by.zelenko.numerologic.ui.Square;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Logic.Calculate;
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

    private HorizontalLayout r1 = new HorizontalLayout();
    private SquareForm form = new SquareForm();
    private final ClientService clientService;
    public Client client;
    public Long id;
    private String date;
    private Component component;

    public SquareView(ClientService clientService) {
        this.clientService = clientService;
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        form.addListener(SquareForm.SaveEvent.class, this::saveClient);
        form.addListener(SquareForm.calculateEvent.class, this::getDate);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        add(r1);
    }
    //пытаемся взять дату
    private void getDate(SquareForm.calculateEvent event) {
        this.date = event.getDate();
        if(component == null) {
            component = drawSquare(date);
            r1.add(component);
        } else {
            r1.remove(component);
            component = drawSquare(date);
            r1.add(component);
        }
    }
    //рисуем квадрат
    public Component drawSquare(String date){
        Calculate calculate = new Calculate();
        Map<String,Integer> data = calculate.calculate(date);
        VerticalLayout root = new VerticalLayout();
        root.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        HorizontalLayout lineOne  = new HorizontalLayout();
        lineOne.setClassName("line_table");
        lineOne.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card11  = new Card(new TitleLabel("Число судьбы").withWhiteSpaceNoWrap()
                /*new PrimaryLabel(String.valueOf(data.get("fateNumber")))*/);
        card11.add(new PrimaryLabel(String.valueOf(data.get("fateNumber"))));
        card11.setHeight("128px");
        card11.setWidth("200px");
        Card card12  = new Card(new TitleLabel("Доп числа").withWhiteSpaceNoWrap(),
                new PrimaryLabel(data.get("firstNumber") + "/" +
                        data.get("secondNumber") + "/" + data.get("thirdNumber") + "/" + data.get("fourthNumber")));
        card12.setHeight("128px");
        card12.setWidth("200px");
        Card card13 = new Card();
        card13.setHeight("128px");
        card13.setWidth("200px");
        Card card14  = new Card(new TitleLabel("Темперамент").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("temperament"))));
        card14.setHeight("128px");
        card14.setWidth("200px");
        lineOne.add(card11, card12, card13, card14);

        HorizontalLayout lineTwo  = new HorizontalLayout();
        lineTwo.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card21  = new Card(new TitleLabel("Характер").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("character"))));
        card21.setHeight("128px");
        card21.setWidth("200px");
        Card card22  = new Card(new TitleLabel("Здоровье").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("health"))));
        card22.setHeight("128px");
        card22.setWidth("200px");
        Card card23 = new Card(new TitleLabel("Удача").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("luck"))));
        card23.setHeight("128px");
        card23.setWidth("200px");
        Card card24  = new Card(new TitleLabel("Цель").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("target"))));
        card24.setHeight("128px");
        card24.setWidth("200px");
        lineTwo.add(card21,card22,card23,card24);

        HorizontalLayout lineThree  = new HorizontalLayout();
        lineThree.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card31  = new Card(new TitleLabel("Энергия").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("energy"))));
        card31.setHeight("128px");
        card31.setWidth("200px");
        Card card32  = new Card(new TitleLabel("Логика").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("logic"))));
        card32.setHeight("128px");
        card32.setWidth("200px");
        Card card33 = new Card(new TitleLabel("Долг").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("trust"))));
        card33.setHeight("128px");
        card33.setWidth("200px");
        Card card34  = new Card(new TitleLabel("Семья").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("family"))));
        card34.setHeight("128px");
        card34.setWidth("200px");
        lineThree.add(card31,card32,card33,card34);

        HorizontalLayout lineFour  = new HorizontalLayout();
        lineFour.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card41  = new Card(new TitleLabel("Интерес").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("interest"))));
        card41.setHeight("128px");
        card41.setWidth("200px");
        Card card42  = new Card(new TitleLabel("Труд").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("work"))));
        card42.setHeight("128px");
        card42.setWidth("200px");
        Card card43 = new Card(new TitleLabel("Память").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("memory"))));
        card43.setHeight("128px");
        card43.setWidth("200px");
        Card card44  = new Card(new TitleLabel("Привычки").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("habit"))));
        card44.setHeight("128px");
        card44.setWidth("200px");
        lineFour.add(card41,card42,card43,card44);

        HorizontalLayout lineFive  = new HorizontalLayout();
        lineFour.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        Card card51  = new Card(new TitleLabel("Быт").withWhiteSpaceNoWrap(),
                new PrimaryLabel(String.valueOf(data.get("bit"))));
        card51.setHeight("128px");
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
        add(r1);
    }
}
