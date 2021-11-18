package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnkPartnerKeyException;

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

    try {
      _display.addLine(_receiver.showPartner(key));
      for(String notification: _receiver.showPartnerNotifications(key)) {
        _display.addLine(notification);
      }
      _display.display();
    }
    catch (UnkPartnerKeyException upke) {
      throw new UnknownPartnerKeyException(key);
    }
    /*catch (ClassNotFoundException e)  { 
       e.printStackTrace(); 
    }*/
  }

}
