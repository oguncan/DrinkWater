package com.ocktr.drinkwater.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.ocktr.drinkwater.R;

import static android.app.Activity.RESULT_OK;
import static com.ocktr.drinkwater.fragments.intro.AboutYouFragment.REQUEST_NAME_CODE;

public class AboutYouNameDialog extends AppCompatDialogFragment {
    private EditText nameDialogEditText;
    private Button nameDialogOKButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_about_you_name, null);
        alertDialogBuilder.setView(view);

        nameDialogEditText = view.findViewById(R.id.nameDialogEditText);
        nameDialogOKButton = view.findViewById(R.id.nameDialogOKButton);

        nameDialogEditText.requestFocus();

        nameDialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nameDialogEditText.getText().toString().trim().isEmpty()) {
                    String name = nameDialogEditText.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("name",name);
                    getTargetFragment().onActivityResult(REQUEST_NAME_CODE, RESULT_OK, intent);
                    dismiss();
                }
            }
        });

        return alertDialogBuilder.create();
    }

}
