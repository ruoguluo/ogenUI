package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.preferences.PreferenceConstants;
import halogenui.processes.search.AbstractSearchProcessor;
import halogenui.processes.search.SearchKeyByValueProcessor;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;

public abstract class AbstractSearchHandler extends AbstractHandler {
	
	public abstract AbstractSearchProcessor getProcessor();
	
	public abstract String getDisplayValue(Entry entry);
	
	public void okPressAction(ElementListSelectionDialog dialog, ExecutionEvent event, Object...obj){};
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();
		
		if (selection !=null){
			
			TextSelection textSelection = (TextSelection) selection;
			String textForSearching = textSelection.getText();
			String path = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.P_PATH, "", null);
			AbstractSearchProcessor processor = getProcessor();
			processor.setFilePath(path);
			ArrayList<Entry> resultEntries = processor.search(textForSearching);
			String[] options = new String[resultEntries.size()];
			for (int i=0;i<resultEntries.size();i=i+1){
				options[i] = getDisplayValue(resultEntries.get(i));
			}
			ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider());
			dialog.setElements(options);
			dialog.setTitle("Search Result for '" + textForSearching + "'");
			if (dialog.open()!=Window.OK){
				return false;
			}else{
				okPressAction(dialog,event,textSelection);
			}
		}
		return null;
	}

}
