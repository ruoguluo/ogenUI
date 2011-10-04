package halogenui.actions.search;

import halogenui.helpers.Utils;
import halogenui.models.Entry;
import halogenui.preferences.PreferenceConstants;
import halogenui.processors.search.AbstractSearchProcessor;
import halogenui.processors.search.SearchKeyByValueProcessor;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
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
		sb.append("Area:" + entry.getArea() + ")");

		return sb.toString();
	}

	private void replace(IEditorPart editor, TextSelection textSelection,
			String key, String surroundingFunction) {

		Boolean replaceWithUIKey = Platform.getPreferencesService().getBoolean(
				"halogenUI", PreferenceConstants.REPLACE_WITH_UI_KEY, false,
				null);
		if (!replaceWithUIKey) {
			return;
		}
		int offSet = textSelection.getOffset();
		int length = textSelection.getLength();
		if (surroundingFunction != "") {
			key = surroundingFunction + "(\"" + key + "\")";
		}
		AbstractTextEditor temEditor = (AbstractTextEditor) editor;
		IDocumentProvider docProvider = temEditor.getDocumentProvider();
		IDocument doc = docProvider.getDocument(editor.getEditorInput());
		try {
			if (doc.get(offSet - 1, 1).equals("\"")
					&& doc.get(offSet + length, 1).equals("\"")) {
				offSet--;
				length += 2;
			}
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			doc.replace(offSet, length, key);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void okPressAction(ElementListSelectionDialog dialog,
			ExecutionEvent event) {

		ISelection selection = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getSelection();

		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		TextSelection textSelection = (TextSelection) selection;
		String result = dialog.getFirstResult().toString();
		int endIndex = result.indexOf(" (Key Label:");
		String key = result.substring(0, endIndex);
		logger.info("key to be sub in:" + key);

		consoleOut.println("key to be sub in:" + key);

		String surroundingFunction = Platform.getPreferencesService()
				.getString("halogenUI",
						PreferenceConstants.SURROUNDINGFUNCTION, "", null);
		replace(editor, textSelection, key, surroundingFunction);
	}

}
