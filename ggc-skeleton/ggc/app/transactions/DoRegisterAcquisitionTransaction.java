package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import javax.sound.midi.Receiver;

import ggc.core.WarehouseManager;

import ggc.core.exception.UnkPartnerKeyException;
import ggc.app.exception.UnknownPartnerKeyException;
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
  public final void execute() throws CommandException, UnknownPartnerKeyException {
    
    String partnerKey = stringField("partnerKey");
    String productKey = stringField("productKey");
    Double price = realField("price");
    Integer Amount = integerField("Amount");

    try {
    _receiver.registerAcquisitionTransaction(partnerKey, productKey, price, Amount);
    }
    catch (UnkPartnerKeyException upke){
			throw new UnknownPartnerKeyException(partnerKey);
		} 

  }

}
