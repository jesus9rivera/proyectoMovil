package com.example.agendawsV1.Objetos;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.util.ArrayList;

public class ProcesosPHP implements Response.Listener<JSONObject>,Response.ErrorListener {

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;
    private ArrayList<Contactos> contactos = new ArrayList<Contactos>();
    private String serverip ="https://riverajesusgpe98.000webhostapp.com/WebService/";
    public void setContext(Context context){
        request = Volley.newRequestQueue(context);
    }

    public void insertarContactoWebService(Contactos c){
        String url = serverip + "wsRegistro.php?nombre="+c.getNombre() +"&telefono1="+c.getTelefono1()+"&telefono2="
                +c.getTelefono2()+"&direccion="+c.getDireccion() +"&notas="+c.getNotas()+"&favorite="+c.getFavorite() +"&idMovil="+c.getIdMovil();
        url = url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    public void actualizarContactoWebService(Contactos c,int id){
        String url = serverip + "wsActualizar.php?_ID="+id
                +"&nombre="+c.getNombre()+"&direccion="+c.getDireccion() +"&telefono1="+c.getTelefono1()+"&telefono2="+c.getTelefono2()
                +"&notas="+c.getNotas()+"&favorite="+c.getFavorite();
        url = url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
    public void borrarContactoWebService(int id){
        String url = serverip + "wsEliminar.php?_ID="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("ERROR",error.toString());
    }
    @Override
    public void onResponse(JSONObject response) {
    }

}
