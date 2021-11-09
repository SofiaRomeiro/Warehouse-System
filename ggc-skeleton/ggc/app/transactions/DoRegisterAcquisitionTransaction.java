package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
    addStringField("productKey", Message.requestProductKey());
    addRealField("price", Message.requestPrice());
    addIntegerField("Amount", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException {
    
    String partnerKey = stringField("partnerKey");
    String productKey = stringField("productKey");
    Double price = realField("price");
    Integer Amount = integerField("Amount");


  }

}
