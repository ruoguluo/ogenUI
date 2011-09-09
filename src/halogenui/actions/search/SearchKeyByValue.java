package halogenui.actions.search;

import halogenui.model.Entry;
import halogenui.preferences.PreferenceConstants;
import halogenui.processors.search.AbstractSearchProcessor;
import halogenui.processors.search.SearchKeyByValueProcessor;

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
		
		StringBuilder sb = new StringBuilder();
		sb.append(entry.getKey() + " (");
		sb.append("Key Label:" + entry.getKeyLabel() + "/");
		sb.append("Module:" + entry.getModule() + "/");
		sb.append("Area:" + entry.getArea()+")");
		
		return sb.toString();
	}
	
	private void replace(IEditorPart editor, TextSelection textSelection, String key, String surroundingFunction) {

		Boolean replaceWithUIKey = Platform.getPreferencesService().getBoolean("halogenUI", PreferenceConstants.REPLACE_WITH_UI_KEY, false, null);
		if (!replaceWithUIKey){
			return;
		}
		
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
		String result = dialog.getFirstResult().toString();
		int endIndex = result.indexOf(" (key label:");
		String key = result.substring(0, endIndex);
		System.out.println(key);
		
		String surroundingFunction = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.SURROUNDINGFUNCTION, "", null);
		replace(editor,textSelection,key,surroundingFunction);
	}

}
