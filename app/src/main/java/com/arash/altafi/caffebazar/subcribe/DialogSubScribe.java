package com.arash.altafi.caffebazar.subcribe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arash.altafi.caffebazar.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class DialogSubScribe extends BottomSheetDialogFragment {

    private MaterialButton btn30;
    private MaterialButton btn60;
    private MaterialButton btn90;
    private OnCallBackSubcribe onCallBackSubcribe;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCallBackSubcribe)
            onCallBackSubcribe= (OnCallBackSubcribe) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_subscibe, container,false );
        findViews(view);
        listener();
        return view;
    }

    private void findViews(View view) {
        btn30 = view.findViewById(R.id.btn_subscribe_1);
        btn60 = view.findViewById(R.id.btn_subscribe_2);
        btn90 = view.findViewById(R.id.btn_subscribe_3);
    }

    private void listener() {
        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackSubcribe.onClickSubcribe(SubScribe_Activity.SUBCRIBE_30);
                dismiss();
            }
        });
        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackSubcribe.onClickSubcribe(SubScribe_Activity.SUBCRIBE_60);
                dismiss();

            }
        });
        btn90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBackSubcribe.onClickSubcribe(SubScribe_Activity.SUBCRIBE_90);
                dismiss();

            }
        });
    }
    public interface OnCallBackSubcribe
    {
        void onClickSubcribe(String subcribe);
    }
}
