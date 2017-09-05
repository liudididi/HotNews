package Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jishi20170830.LoadingActivity;
import com.example.asus.jishi20170830.R;

import ShareUtils.ConnectivityUtils;

/**
 * Created by asus on 2017/8/30.
 */

public class Fragment_right extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frag_right, null);
        ConnectivityUtils connectivityUti= new ConnectivityUtils();
        return view ;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv_lixian=view.findViewById(R.id.tv_lixian);
        tv_lixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getActivity(), LoadingActivity.class);
                startActivity(in);
            }
        });
        TextView tv_network=view.findViewById(R.id.tv_network);
        tv_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "这是离线", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setSingleChoiceItems(new String[]{"大图","小图"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case  0:
                          dialogInterface.cancel();
                            break;
                        case  1:
                            dialogInterface.cancel();
                            break;
                    }
                    }
                });
                dialog.create().show();
            }
        });

    }
}
