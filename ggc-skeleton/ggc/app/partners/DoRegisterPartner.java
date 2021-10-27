package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

 
//FIXME import classes

import ggc.app.exception.DuplicatePartnerKeyException;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    //FIXME add command fields
    addStringField("key", Message.requestPartnerKey());
    addStringField("name", Message.requestPartnerName());
    addStringField("address", Message.requestPartnerAddress());
  }

  @Override
  public void execute() throws CommandException, DuplicatePartnerKeyException {
    //FIXME implement command

      //form.parse();
      

      String key = stringField("key");
      String name = stringField("name");
      String address = stringField("address");

      if (_receiver.duplicatePartnerKey(key))
        throw new DuplicatePartnerKeyException(key);

      _receiver.registerPartner(key, name, address);

    }

  }


