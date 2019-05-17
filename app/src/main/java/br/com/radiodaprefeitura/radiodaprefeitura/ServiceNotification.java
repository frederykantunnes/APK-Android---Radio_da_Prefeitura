package br.com.radiodaprefeitura.radiodaprefeitura;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Frederyk Antunnes on 19/07/2017.
 */

public class ServiceNotification extends Service{
    String nome, senha;
    private boolean flag = false;
    private int contador=0;


    private static final int NOTIFICACAO_SIMPLES = 1;
    private static final int NOTIFICACAO_COMPLETA = 2;
    private static final int NOTIFICACAO_BIG = 3;
    private static final int NOTIFICACAO_RESPOSTA = 4;
    private static final int NOTIFICACAO_PAGINAS = 5;
    private static final int NOTIFICACAO_AGRUPADA = 6;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("SERVICE", "Startado"+contador);
        flag=true;
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        java.util.concurrent.CompletableFuture.AsynchronousCompletionTask<Object, Object, String> atBuscaUsuario = new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... objects) {
                try {
                    URL url = new URL("http://radiodaprefeitura.com.br/notificacoes.php?id="+id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Content-type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setDoOutput(false);
                    connection.setDoInput(true);
                    connection.connect();
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String resposta = scanner.nextLine();
                    connection.disconnect();
                    return resposta;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String jsonString) {
                if (jsonString == null || jsonString.isEmpty() || jsonString.equalsIgnoreCase("0")) {
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(jsonString);
                        int count = jsonArray.length();
                        for (int i = 0; i < count; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            jsonObject.getString("nome");
                            NotificationUtils.criarNotificacaoCompleta(
                                    Service_Usuario.this,
                                    "Dados Atualizados!",
                                    NOTIFICACAO_COMPLETA, user.getNome());


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPreExecute() {
            }
        };


        UsuarioDAO dao = new UsuarioDAO(Service_Usuario.this);
        final ArrayList<UsuarioVO> lista = (ArrayList<UsuarioVO>) dao.listarUsuarios();
        if(lista.isEmpty()){
        }else{
            UsuarioDAO.usuarioLogado = lista.get(0);
            nome = UsuarioDAO.usuarioLogado.getEmail();
            senha=UsuarioDAO.usuarioLogado.getSenha();
            atBuscaUsuario.execute();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("SERVICE", "Fim"+contador);
        super.onDestroy();

    }

}