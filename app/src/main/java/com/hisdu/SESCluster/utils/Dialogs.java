package com.hisdu.SESCluster.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.hisdu.SESCluster.interfaces.DialogCallback;
import com.hisdu.SESCluster.interfaces.OnDialogButtonClickListener;


public class Dialogs {

    public static void showDialog(String dialogMessage, String dialogTitle,
                                  final Activity activityContext, Boolean okayButton,
                                  Boolean cancelButton, String okayText, String cancelText, final Boolean shouldCloseOnOkay) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activityContext);

        alertDialogBuilder.setTitle(dialogTitle);

        alertDialogBuilder.setMessage(dialogMessage);

        if (okayButton) {
            alertDialogBuilder.setPositiveButton(okayText,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (shouldCloseOnOkay)
                                activityContext.finish();
                        }
                    });
        }
        if (cancelButton) {

            alertDialogBuilder.setNegativeButton(cancelText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

        }

        alertDialogBuilder.show();


    }

    public static void showDialog(String dialogMessage, String dialogTitle,
                                  final Activity activityContext, Boolean okayButton,
                                  Boolean cancelButton, String okayText, String cancelText,
                                  final Boolean shouldCloseOnOkay, final View.OnClickListener listener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activityContext);

        alertDialogBuilder.setTitle(dialogTitle);

        alertDialogBuilder.setMessage(dialogMessage);

        if (okayButton) {
            alertDialogBuilder.setPositiveButton(okayText,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (shouldCloseOnOkay)
                                activityContext.finish();

                            listener.onClick(null);
                        }
                    });
        }
        if (cancelButton) {

            alertDialogBuilder.setNegativeButton(cancelText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

        }

        alertDialogBuilder.show();


    }

    public static void showDialogWithCallbacks(String dialogMessage, String dialogTitle,
                                               final Activity activityContext, Boolean okayButton,
                                               Boolean cancelButton, String okayText, String cancelText, final View target) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activityContext);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);

        if (okayButton) {
            alertDialogBuilder.setPositiveButton(okayText,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((DialogCallback) activityContext).okay(target);
                        }
                    });
        }
        if (cancelButton) {

            alertDialogBuilder.setNegativeButton(cancelText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            ((DialogCallback) activityContext).cancel(target);
                        }
                    });

        }

        alertDialogBuilder.show();


    }

    public static void showYesNoDialogWithCallbacks(String dialogMessage, String dialogTitle,
                                                    final Activity activityContext, Boolean okayButton,
                                                    Boolean cancelButton, String okayText, String cancelText, final View target) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activityContext);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);

        if (okayButton) {
            alertDialogBuilder.setPositiveButton(okayText,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((DialogCallback) activityContext).okay(target);
                        }
                    });
        }
        if (cancelButton) {

            alertDialogBuilder.setNegativeButton(cancelText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            ((DialogCallback) activityContext).cancel(target);
                        }
                    });

        }

        alertDialogBuilder.show();
    }

    public static void showAlert(Context context, String message,
                                 String buttonPositiveText, String buttonNegativeText,
                                 final OnDialogButtonClickListener dialogListener,
                                 final int requestCode) {
        new AlertDialog.Builder(context).setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonPositiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onDialogPositiveButtonClick(requestCode);
                        dialog.dismiss();
                    }
                }).setNegativeButton(buttonNegativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogNegativeButtonClick(requestCode);
                dialog.dismiss();
            }
        }).show();
    }

    public static void showPositiveAlert(Context context, String message,
                                         String buttonPositiveText,
                                         final OnDialogButtonClickListener dialogListener,
                                         final int requestCode, String title) {
        new AlertDialog.Builder(context).setMessage(message).setTitle(title)
                .setCancelable(false)
                .setPositiveButton(buttonPositiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onDialogPositiveButtonClick(requestCode);
                        dialog.dismiss();
                    }
                }).show();
    }


}
