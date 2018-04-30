package com.example.myrecipes.screens.menuItems;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.myrecipes.R;
import com.example.myrecipes.screens.Results;
import com.example.myrecipes.screens.auth.AuthenticationActivity;



public class DialogBox extends DialogFragment {
    public static final String Login = "Login";
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_box)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                             startActivity(intent);

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getContext(), Results.class);
                            startActivity(intent);

                            }
                        
                    });

            return builder.create();
        }
    }

