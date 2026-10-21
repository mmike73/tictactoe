package com.example.tictactoe;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Random;

public class Game extends AppCompatActivity {

    private final Button[] cells = new Button[9];
    private TextView statusText;
    private Button newGameButton;
    private View root;

    private String player1;
    private String player2;
    private boolean player1Turn;
    private boolean gameOver = false;

    private static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbar = findViewById(R.id.game_toolbar);
        setSupportActionBar(toolbar);

        root = findViewById(R.id.game_root);
        statusText = findViewById(R.id.status_text);
        newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(v -> startNewGame());

        player1 = getIntent().getStringExtra("player1");
        player2 = getIntent().getStringExtra("player2");

        TableLayout table = findViewById(R.id.board);
        int index = 0;
        for (int r = 0; r < table.getChildCount(); r++) {
            TableRow row = (TableRow) table.getChildAt(r);
            for (int c = 0; c < row.getChildCount(); c++) {
                Button button = (Button) row.getChildAt(c);
                button.setOnClickListener(this::onCellClick);
                cells[index++] = button;
            }
        }

        player1Turn = new Random().nextBoolean();
        updateStatus();
    }

    private void onCellClick(View v) {
        if (gameOver) {
            Toast.makeText(this, "The game is over!", Toast.LENGTH_SHORT).show();
            return;
        }

        Button button = (Button) v;
        if (!button.getText().toString().isEmpty()) {
            Toast.makeText(this, "That cell is already taken", Toast.LENGTH_SHORT).show();
            return;
        }

        button.setText(player1Turn ? "X" : "0");

        if (hasWinner()) {
            gameOver = true;
            statusText.setText((player1Turn ? player1 : player2) + " wins!");
            newGameButton.setVisibility(View.VISIBLE);
        } else if (boardFull()) {
            gameOver = true;
            statusText.setText("It's a tie!");
            newGameButton.setVisibility(View.VISIBLE);
        } else {
            player1Turn = !player1Turn;
            updateStatus();
        }
    }

    private void startNewGame() {
        for (Button cell : cells) {
            cell.setText("");
        }
        gameOver = false;
        player1Turn = new Random().nextBoolean();
        newGameButton.setVisibility(View.GONE);
        updateStatus();
    }

    private void updateStatus() {
        statusText.setText((player1Turn ? player1 : player2) + "'s turn");
    }

    private boolean hasWinner() {
        for (int[] line : LINES) {
            String a = cells[line[0]].getText().toString();
            String b = cells[line[1]].getText().toString();
            String c = cells[line[2]].getText().toString();
            if (!a.isEmpty() && a.equals(b) && a.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean boardFull() {
        for (Button cell : cells) {
            if (cell.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.change_background_color_action) {
            return super.onOptionsItemSelected(item);
        }

        final String[] names = {"White", "Light Blue", "Light Green", "Yellow", "Pink"};
        final int[] colors = {
                Color.WHITE,
                Color.parseColor("#BBDEFB"),
                Color.parseColor("#C8E6C9"),
                Color.parseColor("#FFF9C4"),
                Color.parseColor("#F8BBD0")
        };

        new AlertDialog.Builder(this)
                .setTitle("Choose a background color")
                .setItems(names, (dialog, which) -> root.setBackgroundColor(colors[which]))
                .show();

        return true;
    }
}