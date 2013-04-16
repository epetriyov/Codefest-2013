package ru.codefest.client.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

public class HelpUtils {
    public static class AboutDialog extends DialogFragment {

        public AboutDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Build the about body view and append the link to see OSS licenses
            SpannableStringBuilder aboutBody = new SpannableStringBuilder();
            aboutBody.append(Html.fromHtml(getString(R.string.about_body)));
            LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView aboutBodyView = (TextView) layoutInflater.inflate(
                    R.layout.dialog_about, null);
            aboutBodyView.setText(aboutBody);
            aboutBodyView.setMovementMethod(new LinkMovementMethod());

            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title_about)
                    .setView(aboutBodyView)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.dismiss();
                                }
                            }).create();
        }
    }

    public static void showAbout(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AboutDialog().show(ft, "dialog_about");
    }
}
