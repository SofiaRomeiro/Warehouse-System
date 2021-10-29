package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.InvalidDateException;

import ggc.core.WarehouseManager;

/**
* Advance current date.
*/

class DoAdvanceDate extends Command<WarehouseManager> {

	DoAdvanceDate(WarehouseManager receiver) {

		super(Label.ADVANCE_DATE, receiver);
		addIntegerField("days", Message.requestDaysToAdvance());
	}

	@Override
	public final void execute() throws CommandException, InvalidDateException {
		
		Integer days = integerField("days");

		if (!(_receiver.advanceDate(days))) {
		  throw new InvalidDateException(days);
		}
	}
}