package in.gajerait.liveena.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.gajerait.liveena.R;


public class CommonUtility {


    public static Button btn_ok;
    public static LayoutInflater m_inflater;
    public static View m_view;
    public static TextView textView, txt_title;


    public static void setFont(Context activity, TextView textView) {

        Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/BulletionBord.ttf");
        textView.setTypeface(font);
    }

    public static void setFont(Context activity, RadioButton textView) {

        Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/BulletionBord.ttf");
        textView.setTypeface(font);
    }
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidEmail(String target) {

        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(target);

        return matcher.matches();

    }



    public static void showAlertDialog(final Activity activity, String alert,
                                       String title, final boolean finish) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        m_inflater = LayoutInflater.from(activity);

        m_view = CommonUtility.m_inflater
                .inflate(R.layout.dialog_common, null);

        textView = (TextView) m_view.findViewById(R.id.success_txt);
        txt_title = (TextView) m_view.findViewById(R.id.txt_title);

        txt_title.setText(title);
        textView.setText(alert);

        btn_ok = (Button) m_view.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (finish)
                    activity.finish();

            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.CommonDialog; //style id

        dialog.setContentView(m_view);
        dialog.show();

    }
}