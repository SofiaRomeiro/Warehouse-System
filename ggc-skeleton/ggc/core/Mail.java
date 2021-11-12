package ggc.core;

public class Mail implements SendMessageMode {
    private String _address;

    public Mail(String address) {
        _address = address;
    }

    public void SendMessage(String message) {
        System.out.println(_address + "Novo correio postal" + message);
    }
}
