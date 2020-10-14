package com.sms;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.springframework.stereotype.Component;

// komunikowanie sie z modemem - wszystko z biblioteki JSSC

@Component
public class ModemConnection {

    SerialPort serialPort;

    // Inicjalizacja połączenia z Serial Port może wyglądać w następujący sposób:
    public ModemConnection() throws SerialPortException, InterruptedException {
        this.serialPort = new SerialPort("COM3");     // moj port
        serialPort.openPort();                              // otwieranie portu
        serialPort.setParams(SerialPort.BAUDRATE_9600,      // z jaka szybkoscia bedziemy sie laczyc PUTTI
                SerialPort.DATABITS_8,                      // 5-8
                SerialPort.STOPBITS_1,                      // odstep miedzy wysylanymi wiadomosciami
                SerialPort.PARITY_NONE);

    }

    // Metody odpowiedzialne na wysyłkę SMS i odczyt SMS zgodnie z komendami AT:

    private static final byte NEW_LINE = 0x0D;          // end of line - enter
    private static final byte END_OF_LINE = 0x1A;       // asci - format 16
    public String readSms(String memoryPlace) {
        String message = "";
        try {
            serialPort.writeString("AT+CMGF=1");
            Thread.sleep(1000);
            serialPort.writeByte(NEW_LINE);
            Thread.sleep(1000);
            serialPort.writeString("AT+CMGR=" + memoryPlace);
            Thread.sleep(1000);
            serialPort.writeByte(NEW_LINE);
            Thread.sleep(1000);
            message = serialPort.readString();
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void sendSms(String phoneNumber, String message) {
        try {
            serialPort.writeString("AT+CMGF=1");
            Thread.sleep(1000);
            serialPort.writeByte(NEW_LINE);
            serialPort.writeString("AT+CMGS=\"" + phoneNumber + "\"");
            Thread.sleep(1000);
            serialPort.writeByte(NEW_LINE);
            Thread.sleep(1000);
            serialPort.writeString(message);
            Thread.sleep(1000);
            serialPort.writeByte(NEW_LINE);
            Thread.sleep(1000);
            serialPort.writeByte(END_OF_LINE);
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
