package com.project.todolist;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBtnDialogFragment extends DialogFragment {

    private EditText titleEdt, descriptionEdt;
    private Button doneBtn, cancelBtn;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();

        AlertDialog.Builder alert = new AlertDialog.Builder (getActivity ());
        alert.setView (view);
        return alert.create ();
    }



    public void initView(){
        view = getActivity ().getLayoutInflater ().inflate (R.layout.fragment_add_btn_dialog,null);

        titleEdt = view.findViewById(R.id.title_edit_txt);
        descriptionEdt = view.findViewById(R.id.description_edit_txt);
        doneBtn = view.findViewById(R.id.done_btn);
        cancelBtn = view.findViewById(R.id.cancel_btn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = descriptionEdt.getText().toString();
                String title = titleEdt.getText().toString();
                MyViewHolder myView = null;
                myView.txtTitle.setText(title);
                myView.txtDes.setText(des);
                Intent intent = new Intent(getContext(), RecyclerViewAdapter.class);
                intent.putExtra("MyView",myView.toString());
                startActivity(intent);
                dismiss();
            }


        });


    }

}
