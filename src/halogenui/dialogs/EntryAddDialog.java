package halogenui.dialogs;

import halogenui.processors.search.SearchPropertyValues;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

//import com.qualityeclipse.favorites.model.FavoriteItemType;

/**
 * A specialized filter dialog for the Favorites view that presents the user the
 * option of filtering content based on name, type, or location. The dialog
 * restricts itself to presenting and gathering information from the user and
 * providing accessor methods for the filter action.
 */
public class EntryAddDialog extends Dialog {
	private String key;
	private String defaultValue;
	private String keyLabel;
	private String module;
	private String area;

	private Text keyField;
	private Text defaultValueField;
	private Text keyLabelField;
	private Combo moduleField;
	private Combo areaField;

	/**
	 * Creates a dialog instance. Note that the window will have no visual
	 * representation (no widgets) until it is told to open. By default,
	 * <code>open</code> blocks for dialogs.
	 *
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 */
	public EntryAddDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Creates and returns the contents of the upper part of this dialog (above
	 * the button bar).
	 *
	 * @param parent
	 *            the parent composite to contain the dialog area
	 * @return the dialog area control
	 */
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		final Label hintLabel = new Label(container, SWT.NONE);
		hintLabel.setLayoutData(new GridData(GridData.BEGINNING,
				GridData.CENTER, false, false, 2, 1));
		hintLabel.setText("*All fields are mandatory");
		// =======================================================================================
		final Label keyLabel = new Label(container, SWT.NONE);
		keyLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		keyLabel.setText("Key:");

		keyField = new Text(container, SWT.BORDER);
		keyField.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
				true, false));
		// =======================================================================================
		final Label defaultValueLabel = new Label(container, SWT.NONE);
		defaultValueLabel.setLayoutData(new GridData(GridData.END,
				GridData.CENTER, false, false));
		defaultValueLabel.setText("Default Value:");

		defaultValueField = new Text(container, SWT.BORDER);
		defaultValueField.setLayoutData(new GridData(GridData.FILL,
				GridData.CENTER, true, false));
		// =======================================================================================
		final Label keyLabelLabel = new Label(container, SWT.NONE);
		keyLabelLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		keyLabelLabel.setText("Key Label:");

		keyLabelField = new Text(container, SWT.BORDER);
		keyLabelField.setLayoutData(new GridData(GridData.FILL,
				GridData.CENTER, true, false));
		// =======================================================================================
		final Label moduleLabel = new Label(container, SWT.NONE);
		moduleLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		moduleLabel.setText("Module:");

		moduleField = new Combo(container, SWT.NONE);
		populateComboWithXPath(moduleField, "/globalUI/entry/module/text()");

		// =======================================================================================
		final Label areaLabel = new Label(container, SWT.NONE);
		areaLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		areaLabel.setText("Area:");

		areaField = new Combo(container, SWT.NONE);

		initComponents();
		return container;
	}

	private void populateComboWithXPath(Combo combeField, String xpath) {
		try {
			ArrayList<String> areas = SearchPropertyValues.search(xpath);
			for (String area : areas) {
				combeField.add(area);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Configures the given shell in preparation for opening this window in it.
	 * In this case, we set the dialog title.
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New halogenUI Entry");
	}

	private void initComponents() {

		keyField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				key = keyField.getText();
			}

		});

		defaultValueField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				defaultValue = defaultValueField.getText();
			}

		});

		keyLabelField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				keyLabel = keyLabelField.getText();
			}

		});
		moduleField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				module = moduleField.getText();
				populateAreaValuesWithModule(module);
			}

			private void populateAreaValuesWithModule(String module) {
				areaField.removeAll();
				populateComboWithXPath(areaField, "/globalUI/entry[module='"
						+ module + "']/area/text()");
			}

		});

		areaField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				area = areaField.getText();
			}

		});

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getKeyLabel() {
		return keyLabel;
	}

	public void setKeyLabel(String keyLabel) {
		this.keyLabel = keyLabel;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
