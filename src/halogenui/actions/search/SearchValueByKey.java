package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.processes.search.AbstractSearchProcessor;
import halogenui.processes.search.SearchDefaultValueByKeyProcessor;

public class SearchValueByKey extends AbstractSearchHandler{

	@Override
	public AbstractSearchProcessor getProcessor() {
		
		return new SearchDefaultValueByKeyProcessor();
	}

	@Override
	public String getDisplayValue(Entry entry) {
		
		return entry.getDefaultValue();
	}

}
