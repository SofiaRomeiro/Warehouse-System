package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.InvalidDateException;
import ggc.app.main.Message;

import ggc.core.WarehouseManager;

//FIXME import classes

/**
* Advance current date.
*/

class DoAdvanceDate extends Command<WarehouseManager> {

	DoAdvanceDate(WarehouseManager receiver) {

		super(Label.ADVANCE_DATE, receiver);
		addIntegerField("days", Message.requestDaysToAdvance());
		//FIXME add command fields
	}

	@Override
	public final void execute() throws CommandException, InvalidDateException {

		//FIXME implement command
		
		Integer days = integerField("days");

		if (!(_receiver.advanceDate(days))) {
		  throw new InvalidDateException(days);
		}
	}
}