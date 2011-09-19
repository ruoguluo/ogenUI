package halogenui.actions.edit;

import java.util.ArrayList;
import java.util.Formatter;

import halogenui.actions.search.AbstractSearchHandler;
import halogenui.actions.search.AdvancedSearch;
import halogenui.model.Constants;
import halogenui.model.Entry;
import halogenui.processors.file.FileContentModifier;
import halogenui.processors.search.SearchDefaultValueByKeyProcessor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteEntry extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		AbstractSearchHandler searchHandler = new AdvancedSearch();
		String key = searchHandler.execute(event).toString();
		if (confirmation(shell,key)){
			FileContentModifier.deleteEntry(key);
		}
		return null;
	}

	private boolean confirmation(Shell shell, String key) {
		
		SearchDefaultValueByKeyProcessor processor = new SearchDefaultValueByKeyProcessor();
		ArrayList<Entry> result = processor.search(key);
		Entry entry = result.get(0);
		Formatter fmt = new Formatter();
		fmt.format(Constants.entryXMLFormatString
				, entry.getKey(),entry.getDefaultValue(),entry.getKeyLabel()
				,entry.getModule(),entry.getArea());

		return MessageDialog.openQuestion(shell, "Confirmation of the xml snippet removal", "Are you sure to delete following xml snippet?\n\n" + fmt.toString());
		
	}

}
