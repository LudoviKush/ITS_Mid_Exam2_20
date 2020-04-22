package com.example.moviestest.data;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {
    String Title, Message;

    public Dialog(String aTitle, String aMessage)
    {
        Title = aTitle;
        Message = aMessage;
    }

    public interface IDialog
    {
        void onResponse(boolean aResponse);
    }

    IDialog dialogListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder vBuilder = new android.app.AlertDialog.Builder(getActivity());
        vBuilder.setTitle(Title)
                .setMessage(Message)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onResponse(true);
                        dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onResponse(false);
                        dismiss();
                    }
                });
        return vBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IDialog)
        {
            dialogListener = (IDialog) context;
        }

    }
}
