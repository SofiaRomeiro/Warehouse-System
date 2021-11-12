package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;

import ggc.core.WarehouseManager;
import ggc.core.exception.UnkProductKeyException;
import ggc.app.exception.UnknownProductKeyException;

/**
 * Show all products.
 */
public class DoShowBatchesByProduct  extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("key", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownProductKeyException {
    String key = stringField("key");

    try {
        List<String> batchesByProduct = new ArrayList<>(_receiver.showBatchesByProduct(key));

        for (String b : batchesByProduct) {
          _display.addLine(b);
        }
        _display.display();
    }

    catch (UnkProductKeyException upke) {
        throw new UnknownProductKeyException(key);
    }
  }

}
