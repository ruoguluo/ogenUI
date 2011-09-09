package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.processors.search.AbstractSearchProcessor;
import halogenui.processors.search.SearchDefaultValueByKeyProcessor;

public class SearchDefaultValueByKey extends AbstractSearchHandler{

	@Override
	public AbstractSearchProcessor getProcessor() {
		
		return new SearchDefaultValueByKeyProcessor();
	}

	@Override
	public String getDisplayValue(Entry entry) {
		
		return entry.getDefaultValue();
	}

}
