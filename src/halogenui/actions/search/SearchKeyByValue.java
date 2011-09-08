package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.preferences.PreferenceConstants;
import halogenui.processes.search.AbstractSearchProcessor;
import halogenui.processes.search.SearchKeyByValueProcessor;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;


public class SearchKeyByValue extends AbstractSearchHandler {

	@Override
	public AbstractSearchProcessor getProcessor() {
		
		return new SearchKeyByValueProcessor();
	}

	@Override
	public String getDisplayValue(Entry entry) {
		
		return entry.getKey();
	}
	
	private void replace(IEditorPart editor, TextSelection textSelection, String key, String surroundingFunction) {

		int offSet = textSelection.getOffset();
		int length = textSelection.getLength();
		
		if (surroundingFunction!=""){
			key = surroundingFunction + "(\"" + key + "\")";
		}
		
		AbstractTextEditor temEditor = (AbstractTextEditor)editor;
		IDocumentProvider docProvider = temEditor.getDocumentProvider();
		IDocument doc = docProvider.getDocument(editor.getEditorInput());
		
		try {
			doc.replace(offSet, length, key);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void okPressAction(ElementListSelectionDialog dialog, ExecutionEvent event, Object...obj){
		
		IEditorPart editor = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getActiveEditor();
		TextSelection textSelection = (TextSelection)obj[0];
		Object[] result1 = dialog.getResult();
		String key = "";
		for (Object s : result1){
			key = s.toString();
			System.out.println(key);
		}
		
		String surroundingFunction = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.P_STRING, "", null);
		replace(editor,textSelection,key,surroundingFunction);
	}

}
