package halogenui.actions.search;

import halogenui.helpers.Utils;
import halogenui.models.Entry;
import halogenui.processors.search.AbstractSearchProcessor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractSearchHandler extends AbstractHandler {

	static Logger logger = Logger.getLogger(AbstractSearchHandler.class);
	MessageConsoleStream consoleOut = Utils.getHalogenUIPluginConsole().newMessageStream();

	public abstract AbstractSearchProcessor getProcessor();

	public abstract String getDisplayValue(Entry entry);

	public void okPressAction(ElementListSelectionDialog dialog,
			ExecutionEvent event) {
	};

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Object retVal = null;
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();

		AbstractSearchProcessor processor = getProcessor();
		boolean available = processor.calculateAvailablity(event);
		if (!available) {
			processor.showUsageWarning(shell);
			return null;
		}

		ArrayList<Entry> resultEntries = processor.search();

		if (resultEntries.size() > 0) {
			String[] options = new String[resultEntries.size()];
			for (int i = 0; i < resultEntries.size(); i = i + 1) {
				options[i] = getDisplayValue(resultEntries.get(i));
			}

			ElementListSelectionDialog dialog = new ElementListSelectionDialog(
					shell, new LabelProvider());
			dialog.setElements(options);
			dialog.setTitle("search Result");
			if (dialog.open() != Window.OK) {
				return null;
			} else {
				retVal = dialog.getFirstResult();
				okPressAction(dialog, event);
			}
		} else {
			MessageDialog.openInformation(shell, "", "No match entry found!");
		}
		return retVal;
	}

}
