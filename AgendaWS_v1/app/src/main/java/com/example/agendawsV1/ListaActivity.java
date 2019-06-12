package com.example.agendawsV1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.agendawsV1.Objetos.Contactos;
import com.example.agendawsV1.Objetos.Device;
import com.example.agendawsV1.Objetos.ProcesosPHP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListaActivity extends ListActivity implements
        Response.Listener<JSONObject>,Response.ErrorListener {

    private Button btnNuevo;
    private final Context context = this;
    private ProcesosPHP php = new ProcesosPHP();;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Contactos> listaContactos;
    private String serverip ="https://riverajesusgpe98.000webhostapp.com/WebService/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        listaContactos = new ArrayList<Contactos>();
        request = Volley.newRequestQueue(context);
        consultarTodosWebService();
        btnNuevo = (Button)findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    public void consultarTodosWebService(){
        String url = serverip + "wsConsultarTodos.php?idMovil="+ Device.getSecureId(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
    }
    @Override
    public void onResponse(JSONObject response) {
        Contactos contacto = null;
        JSONArray json = response.optJSONArray("contactos");
        try {
            for(int i=0;i<json.length();i++){
                contacto = new Contactos();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                contacto.set_ID(jsonObject.optInt("_ID"));
                contacto.setNombre(jsonObject.optString("nombre"));
                contacto.setTelefono1(jsonObject.optString("telefono1"));
                contacto.setTelefono2(jsonObject.optString("telefono2"));
                contacto.setDireccion(jsonObject.optString("direccion"));
                contacto.setNotas(jsonObject.optString("notas"));
                contacto.setFavorite(jsonObject.optInt("favorite"));
                contacto.setIdMovil(jsonObject.optString("idMovil"));
                listaContactos.add(contacto);
            }
            MyArrayAdapter adapter = new MyArrayAdapter(context,R.layout.layout_contacto,listaContactos);
            setListAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class MyArrayAdapter extends ArrayAdapter<Contactos> {
        Context context;
        int textViewRecursoId;
        ArrayList<Contactos> objects;
        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Contactos> objects){
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }
        public View getView(final int position, View convertView, ViewGroup
                viewGroup){
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);
            TextView lblNombre = (TextView) view.findViewById(R.id.lblNombreContacto);
            TextView lblTelefono = (TextView) view.findViewById(R.id.lblTelefonoContacto);
            Button btnModificar = (Button)view.findViewById(R.id.btnModificar);
            Button btnBorrar = (Button)view.findViewById(R.id.btnBorrar);
            if(objects.get(position).getFavorite()>0){
                lblNombre.setTextColor(Color.BLUE);
                lblTelefono.setTextColor(Color.BLUE);
            }else{
                lblNombre.setTextColor(Color.BLACK);
                lblTelefono.setTextColor(Color.BLACK);
            }
            lblNombre.setText(objects.get(position).getNombre());
            lblTelefono.setText(objects.get(position).getTelefono1());
            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    php.setContext(context);
                    Log.i("id", String.valueOf(objects.get(position).get_ID()));
                    php.borrarContactoWebService(objects.get(position).get_ID());
                    objects.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Contacto eliminado con exito",
                            Toast.LENGTH_SHORT).show();
                }
            });
            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle oBundle = new Bundle();
                    oBundle.putSerializable("contacto", objects.get(position));
                    Intent i = new Intent();
                    i.putExtras(oBundle);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            });
            return view;
        }
    }

}
