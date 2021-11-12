package com.arash.altafi.caffebazar.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arash.altafi.caffebazar.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DialogExpireDate extends BottomSheetDialogFragment {

    private TextView txtExpireDate;
    private String expire;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expire = getArguments().getString("expire");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_expire,container,false);
        txtExpireDate = view.findViewById(R.id.txt_expire_date);
        txtExpireDate.setText(expire);
        return view;
    }

    public static DialogExpireDate newInstance(String expiredate) {
        Bundle args = new Bundle();
        args.putString("expire",expiredate);
        DialogExpireDate fragment = new DialogExpireDate();
        fragment.setArguments(args);
        return fragment;
    }

}
