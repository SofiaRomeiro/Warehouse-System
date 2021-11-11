package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.app.exception.UnknownTransactionKeyException; 
import ggc.core.exception.UnkTransactionKeyException;

import ggc.core.WarehouseManager;

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("transactionKey", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    Integer transactionKey = integerField("transactionKey");
 
    try {
      _display.addLine(_receiver.showTransaction(transactionKey));
    }
    catch (UnkTransactionKeyException urke){
      throw new UnknownTransactionKeyException(transactionKey);
    }

    _display.display();
  }  
}
