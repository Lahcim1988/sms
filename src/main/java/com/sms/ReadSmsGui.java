package com.sms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class ReadSmsGui extends VerticalLayout{

    private TextField textFieldMemoryPlace;
    private Button buttonReadSms;
    private ModemConnection modemConnection;
    private Label label;

    @Autowired
    public ReadSmsGui(ModemConnection modemConnection) {
        this.modemConnection = modemConnection;
        textFieldMemoryPlace = new TextField();
        label = new Label();
        buttonReadSms = new Button("Read SMS!!!");
        add(textFieldMemoryPlace, buttonReadSms, label);                          // dodaje buttony do GUI

        // wywolanie metody z modem connection umozliwiajaca wysylanie sms-a
        // w momencie klikniecia definiuje co sie ma stac
        buttonReadSms.addClickListener(clickEvent -> {
            String sms = modemConnection.readSms(textFieldMemoryPlace.getValue());  // wysylam smsa z danymi wybranymi z text fields
            label.setText(sms);
        });
    }
}
