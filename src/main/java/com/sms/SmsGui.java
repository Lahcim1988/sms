package com.sms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsGui extends VerticalLayout {

    private TextField textFieldNumber;
    private TextField textFieldMessage;
    private Button buttonSendSms;
    private ModemConnection modemConnection;

    @Autowired
    public SmsGui(ModemConnection modemConnection) {
        this.modemConnection = modemConnection;
        textFieldNumber = new TextField();
        textFieldMessage = new TextField();
        buttonSendSms = new Button("send SMS!!!");
        add(textFieldNumber, textFieldMessage, buttonSendSms);                          // dodaje buttony do GUI

                            // wywolanie metody z modem connection umozliwiajaca wysylanie sms-a
                            // w momencie klikniecia definiuje co sie ma stac
        buttonSendSms.addClickListener(clickEvent -> {
            modemConnection.sendSms(textFieldNumber.getValue(),              // wysylam smsa z danymi wybranymi z text fields
            textFieldMessage.getValue());
        });
    }
}
