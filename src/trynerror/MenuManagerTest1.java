package trynerror;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

public class MenuManagerTest1 {

    public MenuManagerTest1() {
        Display display = new Display();
        Shell shell = new Shell(display, SWT.SHELL_TRIM);
        shell.setLayout(new FillLayout());

        ToolBarManager tbm = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        tbm.add(new OneAction());
        ToolBar tb = tbm.createControl(shell);

        MenuManager mm = new MenuManager("Test Menu");
        mm.add(new OneAction());
        tbm.setContextMenuManager(mm);

        // dispose shell, this will dispose all sub-widgets as well
        shell.dispose();

        // check status of our stuff
        System.out.println("Toolbar is disposed? - " + tb.isDisposed());
        System.out.println("Menu is disposed? - " + mm.getMenu().isDisposed());
        System.out.println("ToolbarManager contains what? " + tbm.getItems());
        System.out.println("MenuManager contains what? " + mm.getItems());
        System.out.println("MenuManager get id? " + mm.getId());

        System.out.println("OK");

    }

    class OneAction extends Action {
        public OneAction() {
            super("Test Action");
        }

        @Override
        public void run() {
            System.out.println("Action was run");
        }
    }

    public static void main(String[] args) {
        new MenuManagerTest1();
    }

}