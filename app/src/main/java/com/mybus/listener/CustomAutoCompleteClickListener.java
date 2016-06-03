package com.mybus.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Listener for AutoCompleteTextView when user selects an address
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class CustomAutoCompleteClickListener implements AdapterView.OnItemClickListener {

    private final EditText mEditText;

    public CustomAutoCompleteClickListener(EditText editText) {
        this.mEditText = editText;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mEditText.setText(mEditText.getText() + " ");
        mEditText.setSelection(mEditText.getText().length());
        //ToDo: Change the keyboard to NUMERIC, inputType seems not to be working as expected.
    }
}
