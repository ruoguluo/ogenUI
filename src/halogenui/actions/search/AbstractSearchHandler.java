package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.preferences.PreferenceConstants;
import halogenui.processors.search.AbstractSearchProcessor;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractSearchHandler extends AbstractHandler {

	public abstract AbstractSearchProcessor getProcessor();

	public abstract String getDisplayValue(Entry entry);

	public void okPressAction(ElementListSelectionDialog dialog, ExecutionEvent event, Object...obj){};

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Object retVal = null;
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();

		if (this.getClass().equals(AdvancedSearch.class)||selection !=null && selection instanceof TextSelection){

			TextSelection textSelection = (TextSelection) selection;
			String textForSearching = textSelection.getText();
			String path = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.PATH, "", null);
			AbstractSearchProcessor processor = getProcessor();

			processor.setFilePath(path);
			ArrayList<Entry> resultEntries = processor.search(textForSearching);

			if (resultEntries.size()>0){
				String[] options = new String[resultEntries.size()];
				for (int i=0;i<resultEntries.size();i=i+1){
					options[i] = getDisplayValue(resultEntries.get(i));
				}

				ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider());
				dialog.setElements(options);
				dialog.setTitle("search Result");
//				dialog.setTitle("Search Result for '" + textForSearching + "'");
				if (dialog.open()!=Window.OK){
					return false;
				}else{
					retVal = dialog.getFirstResult();
					okPressAction(dialog,event,textSelection);
				}
			}else{
				MessageDialog.openInformation(shell, "", "No match entry found!");
			}
		}
		return retVal;
	}

}
