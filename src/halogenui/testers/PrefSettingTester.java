package halogenui.testers;

import java.io.File;

import halogenui.preferences.PreferenceConstants;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Platform;

public class PrefSettingTester extends PropertyTester {

	@Override
	public boolean test(Object arg0, String arg1, Object[] arg2, Object arg3) {
		String path = Platform.getPreferencesService().getString("halogenUI", PreferenceConstants.PATH, "", null);
//		System.out.println(path);
		if (path!=null){
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("Halogen UI File does not exist.");
				return false;
			}
		}
		return true;
	}

}
