package halogenui.preferences;

import halogenui.Activator;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class HalogenUIPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	
	protected StringFieldEditor surroundingFunction;
	protected BooleanFieldEditor replaceChoice;

	public HalogenUIPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		
		addField(new FileFieldEditor(PreferenceConstants.PATH, 
				PreferenceConstants.PATH_TEXT, getFieldEditorParent()));
		
		replaceChoice = new BooleanFieldEditor(PreferenceConstants.REPLACE_WITH_UI_KEY,
				PreferenceConstants.REPLACE_WITH_UI_KEY_TEXT, getFieldEditorParent());
		
		addField(replaceChoice);
		
		surroundingFunction = new StringFieldEditor(PreferenceConstants.SURROUNDINGFUNCTION, 
				PreferenceConstants.SURROUNDINGFUNCTION_TEXT, getFieldEditorParent());
		
		addField(surroundingFunction);
		
		replaceChoice.setPropertyChangeListener(new IPropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				System.out.println("replaceChoice property changed");
				surroundingFunction.setEnabled(replaceChoice.getBooleanValue(), getFieldEditorParent());
			}
		});
		

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}