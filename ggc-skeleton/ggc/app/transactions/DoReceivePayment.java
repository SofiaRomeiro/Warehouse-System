package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.exception.UnkTransactionKeyException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("transactionKey", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException, UnknownTransactionKeyException {
    Integer transactionKey = integerField("transactionKey");
    try {
      _receiver.receivePayment(transactionKey);
    } catch (UnkTransactionKeyException utke) {
      throw new UnknownTransactionKeyException(transactionKey);
    }
    /*catch (ClassNotFoundException e)  { 
       e.printStackTrace(); 
    }*/
  }

}
