package br.com.radiodaprefeitura.radiodaprefeitura;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

            final ProgressBar pb = (ProgressBar) findViewById(R.id.progress);
            final WebView wv = (WebView) findViewById(R.id.webv);
            WebSettings ws = wv.getSettings();
            ws.setJavaScriptEnabled(true);
            ws.setSupportZoom(false);
            wv.loadUrl("http://radiodaprefeitura.com.br/mobile.php");

            wv.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon){
                    TextView n = (TextView) findViewById(R.id.texto_caregando);
                    n.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url){
                    TextView n = (TextView) findViewById(R.id.texto_caregando);
                    n.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    wv.setVisibility(View.VISIBLE);
                }




        });


        Intent intent = new Intent(this, Principal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(Principal.this)
                        .setSmallIcon(R.drawable.iconnot)
                        .setLargeIcon(BitmapFactory.decodeResource( getResources(), R.drawable.brasao))
                        .setTicker( "Rádio da Prefeitura" )
                        .setContentTitle( "PRINCESA ISABEL" )
                        .setContentText( "Ouvindo a Rádio da Prefeitura..." );
        Intent resultIntent = new Intent(Principal.this, Principal.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(Principal.this);
        stackBuilder.addParentStack(Principal.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.principal, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_face) {
            String uri = "facebook://facebook.com/info?user=696671007159086";

            String facebookId = "fb://page/696671007159086";
            String urlPage = "https://www.facebook.com/prefeituradeprincesaisabel/";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
            }
        } else if (id == R.id.nav_site_pref) {
            Uri uri = Uri.parse("http://www.princesa.pb.gov.br/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_inst) {
            Uri uri = Uri.parse("https://www.instagram.com/prefeituradeprincesa/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_compartilhar) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String texto = "Baixe Agora o APK da Rádio da Prefeitura de Princesa Isabel\n\nhttps://play.google.com/store/apps/details?id=br.com.radiodaprefeitura.radiodaprefeitura";
            sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_sobre) {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(Principal.this);
            dialogo.setTitle("Rádio da Prefeitura");
            dialogo.setMessage("A Rádio da Prefeitura de Princesa Isabel tem como objetivo divulgar as ações e informações relacionadas a Prefeitura, visando sempre a transparência!" +
                    "\n\nDeselvolvido pela ATD Sistemas\nwww.atdsistemas.com.br\n\nVersão 2.0");
            dialogo.setNeutralButton("Sair", null);
            dialogo.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
