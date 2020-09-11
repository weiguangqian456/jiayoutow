package com.edawtech.jiayou.utils.edit;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

// 保留小数点后两位。
public class DecimalTextWatcher implements TextWatcher {

    private EditText editText;
    private int length;

    public DecimalTextWatcher(EditText editText, int length) {
        this.editText = editText;
        this.length = length;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = s.toString();
        int pointIndex = content.indexOf(".");

        if (pointIndex == 0) {//输入点 .xxx
            editText.setText("0" + content);
            if (before == 0) {//输入的是.
                editText.setSelection(2);
            } else {//删除0.xxx的0
                editText.setSelection(1);
            }
        } else if (pointIndex > 0 && content.length() - pointIndex > length + 1) {
            String con = content.substring(0, pointIndex + length + 1);
            editText.setText(con);
            if (content.length() == start + count) {
                editText.setSelection(con.length());
            } else {
                editText.setSelection(start + 1);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
