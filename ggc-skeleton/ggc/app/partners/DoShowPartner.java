package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show partner.
 */

class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {

    String key = stringField("partnerId");

    if (!(_receiver.hasPartner(key)))
        throw new UnknownPartnerKeyException(key);

    _display.addLine(_receiver.showPartner(key));
    for(String notification: _receiver.showParterNotifications(key)) {
      _display.addLine(notification);
    }
    _display.display();

  }

}
