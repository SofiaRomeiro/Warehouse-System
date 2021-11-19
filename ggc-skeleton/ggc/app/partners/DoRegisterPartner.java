package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.DuplicatePartnerKeyException;
import ggc.core.exception.DuplPartnerKeyException;

/**
 * Register new partner.
 */

class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("key", Message.requestPartnerKey());
    addStringField("name", Message.requestPartnerName());
    addStringField("address", Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException, DuplicatePartnerKeyException {

    String key = stringField("key");
    String name = stringField("name");
    String address = stringField("address");

    try {
      _receiver.registerPartner(key, name, address);
    } catch (DuplPartnerKeyException dpke) {
      throw new DuplicatePartnerKeyException(key);
    }

  }

}
