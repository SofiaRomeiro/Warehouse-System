package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

 
//FIXME import classes

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    //FIXME add command fields
  }

  @Override
  public void execute() throws CommandException {
    //FIXME implement command
    try {

      addStringField("key", Message.requestPartnerKey());
      addStringField("name", Message.requestPartnerName());
      addStringField("address", Message.requestPartnerAddress());

      //form.parse();

      String key = stringField("key");
      String name = stringField("name");
      String address = stringField("address");

      _receiver.registerPartner(key, name, address);

    }

    catch (DuplicatedPartnerKeyException dpke) {
      throw new DuplicatedPartnerKeyException(key);
    }
  }

}
