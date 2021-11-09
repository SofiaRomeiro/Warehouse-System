package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnkProductKeyException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
    addStringField("productKey", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException {
    String partnerKey = stringField("partnerKey");
    String productKey = stringField("productKey");

    try {
      _receiver.toggleProductNotifications(partnerKey, productKey);
    }
    catch (UnkProductKeyException upke) {
      throw new UnknownPartnerKeyException(productKey);
    }
  }

}
