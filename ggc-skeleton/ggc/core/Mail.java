package ggc.core;

import java.util.Collection;

public class Mail implements SendMessageMode {
    private String _address;

    public Mail() {
        _address = new String();
    }

    public Mail(String address) {
        _address = address;
    }

    public void setAddress(String address) {
        _address = address;
    }

    @Override
    public void sendMessage(Collection<Notification> notifications) {
        System.out.println(_address + "Novo correio postal");
    }
}
