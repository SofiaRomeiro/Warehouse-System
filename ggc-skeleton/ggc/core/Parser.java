//package woo.core;
package ggc.core;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

//import woo.core.exception.BadEntryException;
import ggc.core.exception.BadEntryException;
// add here more imports if needed

public class Parser {

	private Warehouse _warehouse;  // ou outra entidade

	Parser(Warehouse w) {
		_warehouse = w;
	}

	void parseFile(String fileName) throws IOException, BadEntryException /* may have other exceptions */ {

	try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
		String line;

		while ((line = reader.readLine()) != null)
			parseLine(line);
		} 
	}

	private void parseLine(String line) throws BadEntryException {
		String[] components = line.split("\\|");

		switch(components[0]) {
			case "SUPPLIER":
				parseSupplier(line, components);
				break;

			case "CLIENT":
				parseClient(line, components);
				break;

			case "BOX":
				parseBox(line, components);
				break;

			case "CONTAINER":
				parseContainer(line, components);
				break;

			case "BOOK":
				parseBook(line, components);
				break;

			default:
				throw new BadEntryException("Type of line not supported: " + line);
				//break; 
		}
	}

	// Format: SUPPLIER|id|nome|endereço
	private void parseSupplier(String line, String[] components)  throws BadEntryException {
		if (components.length != 4)
			throw new BadEntryException("Invalid number of fields in supplier description (4) " + line);

		String id = components[1];
		String name = components[2];
		String address = components[3];

		// create/register supplier?
		// for example, _store.registerSupplier(id, name, address);
		// or use _store.registerSupplier(components[1];, components[2], components[3]);;
		}

	// Format: CLIENT|id|nome|endereço
	private void parseClient(String line, String[] components) throws BadEntryException {
		if (components.length != 4)
			throw new BadEntryException("Invalid number of fields (4) in client description: " + line);

		// add code here
	}

	// Format: BOX|id|tipo-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
	private void parseBox(String line, String[] components) throws BadEntryException {
		if (components.length != 7)
			throw new BadEntryException("wrongr number of fields in box description  (7) " + line);

		// ...
		int price = Integer.parseInt(components[4]);
		// add code here
	}

	// Format: BOOK|id|título|autor|isbn|id-fornecedor|preço|valor-crítico|exemplares
	private void parseBook(String line, String[] components) throws BadEntryException {
		if (components.length != 9)
			throw new BadEntryException("Invalid number of fields (9) in box description: " + line);

		// add code here
	}

	// Format: CONTAINER|id|tipo-de-serviço|nível-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
	private void parseContainer(String line, String[] components) throws BadEntryException {
		if (components.length != 8)
			throw new BadEntryException("Invalid number of fields (8) in container description: " + line);

		// add code here
	}
}