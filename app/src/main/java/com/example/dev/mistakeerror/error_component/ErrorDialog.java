package com.example.dev.mistakeerror.error_component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dev.mistakeerror.R;

/**
 * Created by dev on 02.02.17..
 */

public class ErrorDialog extends Dialog {

    private ErrorAdapter.MyViewHolder errorAdapter;
    private Context context;

    public ErrorDialog(Context context, ErrorAdapter.MyViewHolder errorAdapter){
        super(context);
        this.errorAdapter = errorAdapter;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        Button buttonYes = (Button) findViewById(R.id.BTNDialogYes);
        Button buttonNo = (Button) findViewById(R.id.BTNDialogNo);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorAdapter.removeNote();
                dismiss();
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
