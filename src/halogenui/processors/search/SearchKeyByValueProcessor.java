package halogenui.processors.search;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class SearchKeyByValueProcessor extends AbstractSearchProcessor {

	public boolean calculateAvailablity(ExecutionEvent event) {

		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();

		if (selection != null && selection instanceof TextSelection) {
			this.searchValue = ((TextSelection)selection).getText();
			return true;
		} else {
			return false;
		}
	}

	protected XPathExpression getXPathExpression()
			throws XPathExpressionException {

		XPath xpath = xFactory.newXPath();
		XPathExpression expr = xpath.compile("/globalUI/entry[default='"
				+ searchValue + "']");

		return expr;
	}

	public void showUsageWarning(Shell shell) {
		MessageDialog.openInformation(shell, "", "You need have hightlighted word(s) first!");
	}


}
