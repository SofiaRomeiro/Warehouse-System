package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.exception.UnkPartnerKeyException;

import java.util.List;
import java.util.ArrayList;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("partnerKey", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException, UnknownPartnerKeyException{
    
    String partnerKey = stringField("partnerKey");
    
    try {

      List<String> transactions = new ArrayList<>(_receiver.showAcquisitionTransactionByPartner(partnerKey));

      for (String t : transactions) {
        _display.addLine(t);
      }
      _display.display();
    }
    catch (UnkPartnerKeyException upke) {
      throw new UnknownPartnerKeyException(partnerKey);
    }
  }

}
