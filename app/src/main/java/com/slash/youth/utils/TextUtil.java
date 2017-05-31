package com.slash.youth.utils;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.slash.youth.R.array.keyword;

/**
 * Created by acer on 2017/3/9.
 */
public class TextUtil {

    public static SpannableString matcherSearchTitle(int color, String text, List<String> keyword) {
        String string = text.toLowerCase();
        SpannableString ss = new SpannableString(text);
        for (String key : keyword) {
            String tempKey = key.toLowerCase();
            Pattern pattern = Pattern.compile(tempKey);
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                ss.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ss;
    }

    public SpannableString matcherUrl(String text) {
        List<String> matchRegexList = new ArrayList<String>();
        SpannableString ss = new SpannableString(text.trim());
        String regex =
                "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?|(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(text);
        while (m.find()) {
            System.out.println("match :" + m.group());
            matchRegexList.add(m.group());
            System.out.println("match :" + matchRegexList.size());
        }
        for (String key : matchRegexList) {
            Pattern tempPattern = Pattern.compile(key);
            Matcher matcher = tempPattern.matcher(text);
            while (matcher.find()) {

                int start = matcher.start();
                int end = matcher.end();
                ss.setSpan(new URLClick(key)
                        , start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                System.out.println("match :  click:" + start + end + key);
            }
        }
        return ss;
    }


    class URLClick extends ClickableSpan {
        private String text;

        public URLClick(String text) {
            this.text = text;
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri url = Uri.parse(text);
            intent.setData(url);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            CommonUtils.getContext().startActivity(intent);

        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(0xff517fae);
            ds.setUnderlineText(true);
        }
    }


}
