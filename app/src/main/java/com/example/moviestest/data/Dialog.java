package com.example.moviestest.data;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {

    public interface ICancDialog {
        void onResponse(boolean aResponse, long aId);
    }

    ICancDialog mListener;

    String mTitle, mMessage;
    long mId;

    public Dialog(String aTitle, String aMessage, long aId) {
        mTitle = aTitle;
        mMessage = aMessage;
        mId = aId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
        vBuilder.setTitle(mTitle);
        vBuilder.setMessage(mMessage);
        vBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onResponse(true, mId);
            }
        });
        vBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onResponse(false, mId);
            }
        });

        return vBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ICancDialog) {
            mListener = (ICancDialog) activity;
        } else {
            mListener = null;
        }
    }
}

