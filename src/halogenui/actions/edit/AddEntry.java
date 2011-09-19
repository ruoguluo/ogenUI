package halogenui.actions.edit;

import halogenui.dialogs.EntryAddDialog;
import halogenui.model.Constants;
import halogenui.processors.append.FileContentModifier;

import java.util.Formatter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddEntry extends AbstractHandler {

	private static String confirmationText = "Confirmation: the following xml snippet will be inserted into HalogenUI.xml\n\n";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		EntryAddDialog dialog = new EntryAddDialog(shell);
		if (dialog.open() == InputDialog.OK){
			Formatter fmt = new Formatter();
			fmt.format(Constants.entryXMLFormatString
					, dialog.getKey(),dialog.getDefaultValue(),dialog.getKeyLabel()
					,dialog.getModule(),dialog.getArea());

			if (MessageDialog.openQuestion(shell, "Confirmation of the xml snippet", confirmationText + fmt.toString().replaceAll("\r\n","\n")) == true){
				FileContentModifier.addNewEntry(dialog.getModule(),dialog.getArea(),fmt.toString());
			}
		}

		return null;
	}

}
