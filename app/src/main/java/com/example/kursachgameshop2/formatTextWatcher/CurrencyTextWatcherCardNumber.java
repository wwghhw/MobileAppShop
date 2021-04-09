package com.example.kursachgameshop2.formatTextWatcher;

import android.text.Editable;
import android.text.TextWatcher;

public class CurrencyTextWatcherCardNumber implements TextWatcher {

    private String editText;
    StringBuilder sb = new StringBuilder();
    boolean ignore;
    private final char numPlace = 'X';


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

       if(!ignore){
            removeFormat(s.toString());
            applyFormat(sb.toString());
            ignore = true;
            s.replace(0,s.length(),sb.toString());
            ignore = false;
       }


    }

    private void applyFormat(String toString) {
        String template = getTemplateCard(toString);
        sb.setLength(0);
        for(int i = 0 , textIndex = 0 ; i < template.length() && textIndex < toString.length(); i++){
            if(template.charAt(i) == numPlace){
                sb.append(toString.charAt(textIndex));
                textIndex++;
            }else {
                sb.append(template.charAt(i));
            }
        }

    }

    private void removeFormat(String toString) {

        sb.setLength(0);
        for(int i = 0; i < toString.length();i++){
            char c = toString.charAt(i);
            if(isNumberChar(c)){
                sb.append(c);
            }
        }
    }

    private boolean isNumberChar(char c) {

        return c >= '0' && c<='9';
    }

    private String getTemplateCard(String editText){

        return "XXXX - XXXX - XXXX - XXXX";
    }
}
