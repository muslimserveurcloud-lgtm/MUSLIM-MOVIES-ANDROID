package com.muslimmovies.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class MainActivity extends Activity {
    private ExoPlayer player;
    private PlayerView playerView;
    private TextView status;

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private Button makeButton(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(Color.rgb(0, 112, 240))
        );
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            -1, -2
        );
        p.setMargins(0, dp(6), 0, dp(6));
        b.setLayoutParams(p);
        return b;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), dp(18));
        root.setBackgroundColor(Color.rgb(7, 20, 38));

        TextView title = new TextView(this);
        title.setText("MUSLIM MOVIES");
        title.setTextColor(Color.WHITE);
        title.setTextSize(26);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(10), 0, dp(4));
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Tes vidéos, ton cinéma");
        subtitle.setTextColor(Color.rgb(100, 190, 255));
        subtitle.setTextSize(15);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, dp(14));
        root.addView(subtitle);

        playerView = new PlayerView(this);
        playerView.setBackgroundColor(Color.BLACK);
        playerView.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(230)));
        root.addView(playerView);

        status = new TextView(this);
        status.setText("Choisis une vidéo enregistrée sur ton téléphone.");
        status.setTextColor(Color.LTGRAY);
        status.setTextSize(14);
        status.setPadding(0, dp(10), 0, dp(10));
        root.addView(status);

        Button local = makeButton("▶  Lire une vidéo locale");
        local.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("video/*");
            startActivityForResult(intent, 100);
        });
        root.addView(local);

        Button catalog = makeButton("🌍  Explorer le catalogue gratuit");
        catalog.setOnClickListener(v -> {
            String url = "https://archive.org/advancedsearch.php?q="
                + "mediatype%3Amovies%20AND%20language%3AFrench"
                + "&fl%5B%5D=identifier%2Ctitle&rows=50&page=1&output=html";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Impossible d'ouvrir le catalogue",
                    Toast.LENGTH_LONG).show();
            }
        });
        root.addView(catalog);

        TextView note = new TextView(this);
        note.setText("Catalogue externe : vérifie les droits de diffusion "
            + "et la disponibilité de la version française avant de regarder "
            + "ou de redistribuer un film.");
        note.setTextColor(Color.LTGRAY);
        note.setTextSize(12);
        note.setPadding(0, dp(10), 0, dp(10));
        root.addView(note);

        setContentView(root);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                if (player != null) player.release();
                player = new ExoPlayer.Builder(this).build();
                playerView.setPlayer(player);
                player.setMediaItem(MediaItem.fromUri(uri));
                player.prepare();
                player.play();
                status.setText("Lecture de la vidéo sélectionnée.");
            } catch (Exception e) {
                status.setText("Impossible de lire cette vidéo.");
                Toast.makeText(this, "Erreur de lecture",
                    Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (player != null) {
            player.release();
            player = null;
        }
        super.onDestroy();
    }
}
