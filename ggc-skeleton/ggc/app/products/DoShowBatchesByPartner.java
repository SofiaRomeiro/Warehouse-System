package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;

import ggc.core.WarehouseManager;
import ggc.core.exception.UnkPartnerKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
//FIXME import classes

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("key", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownPartnerKeyException {
    String key = stringField("key");

    try {

      ArrayList<String> batchesByProduct = new ArrayList<>(_receiver.showBatchesByPartner(key));

      for (String b : batchesByProduct) {
        _display.addLine(b);
      }
      _display.display();

    }

    catch (UnkPartnerKeyException upke) {
      throw new UnknownPartnerKeyException(key);
    }
  }

}
