package smkn4.bdg.sipppid.layout;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import smkn4.bdg.sipppid.R;

public class MenuPermohonanActivity extends AppCompatActivity {

    private Button kemendagri, provinsi, kabkot;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_permohonan);


        /*this.getWindow().setBackgroundDrawableResource(R.color.bgDialog);
        *//*this.getWindow().set*//*

        kemendagri = (Button)findViewById(R.id.kemendagri);

        kemendagri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPermohonanActivity.this, KemendagriPengajuanActivity.class));
                finish();
            }
        });*/

    }
}
