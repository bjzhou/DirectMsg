package org.bjzhou.directmsg.ui;

import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.weibo.Authorize;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class LoginActivity extends Activity{
    
    private EditText et_username;
    private EditText et_password;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.login_logining));
        
        et_username = (EditText) findViewById(R.id.et_login_username);
        et_password = (EditText) findViewById(R.id.et_login_password);
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new LoginTask().execute(et_username.getText().toString(),et_password.getText().toString());
        return super.onOptionsItemSelected(item);
    }





    class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... data) {
            String username = data[0];
            String password = data[1];
            Authorize.authorize(getApplicationContext(), username, password);
            return null;
        }
        
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
            super.onPostExecute(result);
        }
        
    }
    
    

}
