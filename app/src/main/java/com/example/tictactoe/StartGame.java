package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        findViewById(R.id.start_button).setOnClickListener(v -> {
            String name1 = ((EditText) findViewById(R.id.player1)).getText().toString().trim();
            String name2 = ((EditText) findViewById(R.id.player2)).getText().toString().trim();

            if (name1.isEmpty() || name2.isEmpty()) {
                Toast.makeText(this, "Enter the names of the players", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, Game.class);
            intent.putExtra("player1", name1);
            intent.putExtra("player2", name2);
            startActivity(intent);
        });
    }
}