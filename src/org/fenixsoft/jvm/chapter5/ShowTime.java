package org.fenixsoft.jvm.chapter5;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;

/**
 * Eclipse 구동 시간 측정
 * @author zzm
 */
public class ShowTime implements IStartup {
  public void earlyStartup() {
    Display.getDefault().syncExec(new Runnable() {
      public void run() {
        long eclipseStartTime = Long.parseLong(System.getProperty("eclipse.startTime"));
        long costTime = System.currentTimeMillis() - eclipseStartTime;
        Shell shell = Display.getDefault().getActiveShell();
        String message = "Eclipse startup time: " + costTime + "ms";
        MessageDialog.openInformation(shell, "Information", message);
      }
  });
  }
}
