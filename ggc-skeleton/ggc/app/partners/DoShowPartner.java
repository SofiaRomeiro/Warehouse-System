package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    //FIXME add command fields
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    //FIXME implement command
    String key = stringField("partnerId");

    if (!_receiver.duplicatePartnerKey(key))
        throw new UnknownPartnerKeyException(key);

    _display.addLine(_receiver.showPartner(key));
    _display.display();

  }

}
