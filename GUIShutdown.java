
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

import java.io.IOException;

public class GUIShutdown {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        GridLayout layout = new GridLayout(2, false);
        shell.setLayout(layout);

        Runtime runtime = Runtime.getRuntime();

        GridData g2x1 = new GridData(SWT.FILL, SWT.FILL, true, true, 2,1);
        GridData g1x1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1,1);
        g1x1.widthHint = SWT.DEFAULT;
        g1x1.heightHint = SWT.DEFAULT;
        g1x1.horizontalSpan = 1;

        Link info = new Link(shell, SWT.BORDER);
        info.setText("$");
        info.setLayoutData(g2x1);


        Label text1 = new Label(shell, SWT.FILL);
        text1.setText(" - - - GUI-Shutdown - - - ");
        text1.setAlignment(SWT.CENTER);
        text1.setLayoutData(g2x1);


        Label days = new Label(shell, SWT.NONE);
        days.setText("Days");

        Spinner dayspinner = new Spinner(shell, SWT.BORDER);
        dayspinner.setMinimum(0);
        dayspinner.setMaximum(364);
        dayspinner.setSelection(0);
        dayspinner.setIncrement(1);
        dayspinner.setPageIncrement(100);
        dayspinner.setLayoutData(g1x1);


        Label hours = new Label(shell, SWT.NONE);
        hours.setText("Hours");

        Spinner hourspinner = new Spinner(shell, SWT.BORDER);
        hourspinner.setMinimum(0);
        hourspinner.setMaximum(23);
        hourspinner.setSelection(0);
        hourspinner.setIncrement(1);
        hourspinner.setPageIncrement(100);
        hourspinner.setLayoutData(g1x1);


        Label minutes = new Label(shell, SWT.NONE);
        minutes.setText("Minutes");

        Spinner minutespinner = new Spinner(shell, SWT.BORDER);
        minutespinner.setMinimum(0);
        minutespinner.setMaximum(59);
        minutespinner.setSelection(0);
        minutespinner.setIncrement(1);
        minutespinner.setPageIncrement(100);
        minutespinner.setLayoutData(g1x1);



        Button force = new Button(shell, SWT.CHECK);
        force.setText("Force");
        force.setSelection(true);
        force.setToolTipText("Forces all programs to close at shutdown. (Recommended)");

        Button linux = new Button(shell, SWT.CHECK);
        linux.setText("Linux");
        linux.setEnabled(false);
        linux.setToolTipText("Coming Soon. (Maybe...)");


        Label spacer = new Label(shell, SWT.NONE);
        spacer.setText(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        spacer.setAlignment(SWT.CENTER);
        spacer.setLayoutData(g2x1);


        Button start = new Button(shell, SWT.PUSH);
        start.setText("Start");
        start.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {

            long time = dayspinner.getSelection()* 86400L + hourspinner.getSelection()* 3600L + minutespinner.getSelection()* 60L;
            //This solution isn't great, but it works
            if(force.getSelection()) {
                try {
                    runtime.exec("shutdown -s -f -t " + time);
                    info.setText("$ Shutting down in " + time + "s");
                } catch (IOException e) {
                    e.printStackTrace();
                    info.setText("$ Error! Please try again.");

                }

            } else {
                try {
                    runtime.exec("shutdown -s -t " + time);
                    info.setText("$ Shutting down in " + time + "s");
                } catch (IOException e) {
                    e.printStackTrace();
                    info.setText("$ Error! Please try again.");
                }
            }

                }
                )
        );

        Button cancel = new Button(shell, SWT.PUSH);
        cancel.setText("Cancel");
        cancel.setToolTipText("Shutdown can be cancelled after hitting \"Start\"!");
        cancel.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> {
                    try {
                        runtime.exec("shutdown -a");
                        info.setText("$ Shutdown cancelled.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        info.setText("$ Error! \n$ Try typing \"<a>shutdown -a</a>\" into CMD.exe");
                    }
                }
                )
        );

        GridData buttons = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        start.setLayoutData(buttons);
        cancel.setLayoutData(buttons);


        shell.setText("GUI-Shutdown");
        shell.setMaximumSize(280,250);
        Monitor primary = display.getPrimaryMonitor ();
        Rectangle bounds = primary.getBounds ();
        Rectangle rect = shell.getBounds ();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation (x, y);
        shell.pack();
        shell.open();
        shell.setMinimumSize(280,250);
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
