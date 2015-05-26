package com.example.hotsystems.hs_celulas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.*;
import android.widget.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity{

    String asw;

    private EditText email;
    private EditText senha;
    private EditText cod_igrej;

    private Button entrar;
    private Button sair;




    //private ProgressDialog progresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hs_cell);

        //Chamar os objetos
        email = (EditText) findViewById(R.id.TXT_EMAIL_LOGIN);
        senha = (EditText) findViewById(R.id.TXT_SENHA_LOGIN);
        cod_igrej = (EditText) findViewById(R.id.COD_IGREJ_LOGIN);
        entrar = (Button) findViewById(R.id.BTN_ENTRA_APLIC);
        sair = (Button) findViewById(R.id.BTN_SAIRX_APLIC);



        entrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Usuario usr = new Usuario();

                String json = generateJson(usr);
                new GetSetDataWeb("http://192.168.1.20/renan/process.php", "send-json", json).execute();

            }
        });

        sair.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String generateJson(Usuario usr){
        final EditText email = (EditText) findViewById(R.id.TXT_EMAIL_LOGIN);
        final EditText senha = (EditText) findViewById(R.id.TXT_SENHA_LOGIN);
        final EditText cod_igrej = (EditText) findViewById(R.id.COD_IGREJ_LOGIN);

        JSONObject jo = new JSONObject();

        String strEmail = email.getText().toString();
        String strSenha = senha.getText().toString();
        String strCod = cod_igrej.getText().toString();

        try{

            jo.put("email", strEmail);
            jo.put("senha", strSenha);
            jo.put("cod_igrej", strCod);


        }catch (JSONException e){
            e.printStackTrace();
        }


        return(jo.toString());

    }

    public Usuario degenerateJson(String data){
        Usuario usr = new Usuario();

        try{
            JSONObject jo = new JSONObject(data);

            usr.setEmail(jo.getString("email"));
            usr.setEmail(jo.getString("senha"));
            usr.setEmail(jo.getString("cod_igrej"));

            /*jo.put("email", Usuario.getEmail());
            jo.put("senha", Usuario.getSenha() );
            jo.put("cod_igrej", Usuario.getCod());*/


        }catch (JSONException e){
            e.printStackTrace();
        }


        return(usr);
    }



   /*private void callServer(final String method, final String data){
        final ProgressDialog progresso = new ProgressDialog(this);
        progresso.setMessage("Entrando");
        progresso.show();


        new AsyncTask<String, String, String>(){



            protected void onPreExecute(){
                progresso.setMessage("Aguarde");

            }
            protected String doInBackground(String... asw){
                asw = Connection.getSetDataWeb("http://192.168.1.20/renan/process.php", method, data);

                if(asw.equals("3"))
                    progresso.setMessage("Creaódigo da igreja inesistente, por favor escreva um código válido.");
                else if (asw.equals("2")){
                    progresso.setMessage("Senha incorreto, por favor escreva uma senha válida.");
                    // makeText(MainActivity.this, "Senha incorreto, por favor escreva uma senha válida.", LENGTH_SHORT).show();
                }else if (asw.equals("1"))
                {
                    progresso.setMessage("E-mail incorreto, por favor escreva um email válido.");
                    // makeText(MainActivity.this, "E-mail incorreto, por favor escreva um email válido.", LENGTH_SHORT).show();
                }

                return null;
            }
            protected void onProgressUpdate(){

            }
            protected void onPostExecute(){
                progresso.setMessage("Seja Bem vindo.");
                progresso.show();
                Intent it = new Intent(MainActivity.this, MC_Home.class);
                startActivity(it);
                progresso.dismiss();

            }



             /*public void run(){

                if (data.isEmpty()){
                    degenerateJson(asw);
               }

                 Atividade_Entrar(asw, null,null);
                 //makeText(MainActivity.this, "Código da igreja inesistente, por favor escreva um código válido.", LENGTH_SHORT).show();

            }
        }.execute();
       }*/
   private class GetSetDataWeb extends AsyncTask<Void, String, String> {
       private ProgressDialog progresso = new ProgressDialog(MainActivity.this);

       private String url, method, data;

       public GetSetDataWeb(String url, String method, String data){
           this.url = url;
           this.method = method;
           this.data = data;
       }


       @Override
       protected void onPreExecute() {

           progresso.setMessage("Aguarde...");
           progresso.show();
       }


       protected String doInBackground(Void... params) {
           String answer = "";
           HttpClient httpClient = new DefaultHttpClient();
           HttpPost httpPost = new HttpPost(url);

           try{
               ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
               valores.add(new BasicNameValuePair("method", method));
               valores.add(new BasicNameValuePair("json", data));

               httpPost.setEntity(new UrlEncodedFormEntity(valores));
               HttpResponse resposta = httpClient.execute(httpPost);
               answer = EntityUtils.toString(resposta.getEntity());
           }
           catch(NullPointerException e){ e.printStackTrace(); }
           catch(ClientProtocolException e){ e.printStackTrace(); }
           catch(IOException e){ e.printStackTrace(); }
           return answer;
       }


       protected void onPostExecute(String result) {
           progresso.dismiss();
           if (result.equals("3")){
               Toast.makeText(MainActivity.this, "Código da igreja inesistente, por favor escreva um código válido.", Toast.LENGTH_SHORT).show();
           return;
            }else if (result.equals("2")){
               Toast.makeText(MainActivity.this, "Senha incorreto, por favor escreva uma senha válida.", Toast.LENGTH_SHORT).show();
               return;
           }else if (result.equals("1"))
           {
               Toast.makeText(MainActivity.this, "E-mail incorreto, por favor escreva um email válido.", Toast.LENGTH_SHORT).show();
               return;
           }

           Toast.makeText(MainActivity.this, "Seja bem vindo", Toast.LENGTH_SHORT).show();
           Intent it = new Intent(MainActivity.this, MC_Home.class);
           startActivity(it);
       }

   }
}




