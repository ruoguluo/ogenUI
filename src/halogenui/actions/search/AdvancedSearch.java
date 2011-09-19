package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.processors.search.AbstractSearchProcessor;
import halogenui.processors.search.SearchAllKeysProcessor;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class AdvancedSearch extends AbstractSearchHandler {

//	@Override
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//
//		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
////		MessageDialog.openInformation(shell, "Not implemented Yet", "You will get notified when it is implemented, stay tuned!");
//
//		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider());
//		dialog.open();
//		return null;
//	}

	@Override
	public AbstractSearchProcessor getProcessor() {
		// TODO Auto-generated method stub
		return new SearchAllKeysProcessor();
	}

	@Override
	public String getDisplayValue(Entry entry) {
		return entry.getKey();
	}


}
