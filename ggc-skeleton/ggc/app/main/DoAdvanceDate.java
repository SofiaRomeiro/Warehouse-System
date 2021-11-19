package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.InvalidDateException;
import ggc.core.exception.NotValidDateException;

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

		try {
			_receiver.advanceDate(days);
		} catch (NotValidDateException nvde) {
			throw new InvalidDateException(days);
		}

	}
}