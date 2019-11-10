package me.parzibyte.crudsqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {
    ImageButton btnpcarmado,btnneck,btnprice,btncrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnpcarmado=(ImageButton) findViewById(R.id.btnpcsarmados);
        btnneck=(ImageButton) findViewById(R.id.btnneck);
        btnprice=(ImageButton) findViewById(R.id.btnprice);
        btncrud=(ImageButton) findViewById(R.id.btncrud);

        btncrud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnneck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CuellodeBotellaActivity.class);
                startActivity(intent);

            }
        });
        btnprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, PreciosActivity.class);
                startActivity(intent);
            }
        });
    }
}
