package com.mindvalley.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.mindvalley.view.views.common.DialogListener;

/**
 * Alert dialog shown using this class.s
 *
 * @author SandeepD
 */

public class AlertUtil
{
    private static AlertDialog.Builder alertDialogBuilder;
    private static AlertDialog alertDialog;

    /**
     * This function is used to display the Alert Dialog Box (Multiple Option) to the User.
     * <p>
     * This function also handles the operations users can perform on the Alert Dialog.
     *
     * @param currentContext - The Context
     * @param dialogID       - The Unique Dialog ID for current screen.
     * @param dialogListener - The Dialog Event Listener
     * @param params         - The Alert Dialog Display Parameters (Heading,Detail Message,Action Texts)
     */
    public static void showAlertDialogMultipleOptions(Context currentContext, final int dialogID, final DialogListener dialogListener, String... params)
    {
        alertDialogBuilder = new AlertDialog.Builder(currentContext);
        alertDialogBuilder.setTitle(params[0]);
        alertDialogBuilder.setMessage(params[1]);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(params[2], new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                if (dialogListener != null)
                {
                    dialogListener.onPositiveAction(dialogID, null);
                }
            }
        });
        alertDialogBuilder.setNegativeButton(params[3], new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                if (dialogListener != null)
                {
                    dialogListener.onNegativeAction(dialogID, null);
                }
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showToast(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
