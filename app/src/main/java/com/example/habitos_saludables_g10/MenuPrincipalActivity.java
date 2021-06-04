package com.example.habitos_saludables_g10;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.params.BlackLevelPattern;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.habitos_saludables_g10.audio.AudioControl;
import com.example.habitos_saludables_g10.audio.NavegarEntreActitities;
import com.example.habitos_saludables_g10.ui.gallery.GalleryFragment;
import com.example.habitos_saludables_g10.ui.gallery.GalleryViewModel;
import com.example.habitos_saludables_g10.ui.home.HomeFragment;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.example.habitos_saludables_g10.R.id.action_audio;
import static com.example.habitos_saludables_g10.R.id.fade;
import static com.example.habitos_saludables_g10.R.id.pin;
import static com.example.habitos_saludables_g10.R.id.rectangles;
import static com.example.habitos_saludables_g10.R.id.wide;

public class MenuPrincipalActivity extends AudioControl implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imagen_perfil;
    private TextView nombre,id,email;
    private Button salirbtn,exportar,exportarPDF,videos,audios;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private String idsesion;
    ControlBD helper;
    private LoginButton login;

    HabitoAdapter adapter;
    List<Habito> listaHabitos;

    String sEmail, sPassword;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = findViewById(R.id.listHabitos);

        View headerView = navigationView.getHeaderView(0);
        nombre =  headerView.findViewById(R.id.nombre);
        imagen_perfil = headerView.findViewById(R.id.perfil);
        email = headerView.findViewById(R.id.email);
        id =  headerView.findViewById(R.id.id);
        salirbtn =  headerView.findViewById(R.id.singoutbtn);
        login = headerView.findViewById(R.id.login_button);
        setNavigationViewListener();
        helper = new ControlBD(this);
        exportar = findViewById(R.id.excel);
        exportarPDF = findViewById(R.id.pdf);
        videos = findViewById(R.id.videosVer);
        audios = findViewById(R.id.audiosVer);

        audios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuPrincipalActivity.this,AudioActivity.class);
                startActivity(intent);
            }
        });
        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuPrincipalActivity.this,videoActivity.class);
                startActivity(intent);
            }
        });
        exportarPDF.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    exportarPDF(id.getText().toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportarExcel(id.getText().toString());
            }
        });
        // Now we can get access to TextView and Button inside SomeFragment

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                helper.abrir();
                Sesion sesion = new Sesion();
                sesion.setIdUsuario(id.getText().toString());
                helper.eliminar(sesion);
                helper.cerrar();
                Intent intent=new Intent(MenuPrincipalActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idUsuario");
            //The key argument here must match that used in the other activity
        }
        if (idsesion.equals("0")) {
            Toast.makeText(this, "Usuario de Gmail", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Usuario de Facebook", Toast.LENGTH_SHORT).show();
            helper.abrir();
            Usuario usu = helper.consultarUsuario(idsesion);
            helper.cerrar();
            EnviarCorreo(usu.getCorreo());
        }

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        salirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()){
                                    gotoMainActivity();
                                }else{
                                    Toast.makeText(getApplicationContext(),"No se ha cerrado la sesion", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
        if(!id.getText().toString().equals(""))
        {
            llenarLista(id.getText().toString());
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            helper.abrir();
            boolean estado = helper.consultarSiUsuario(account.getId());
            helper.cerrar();
            if(!estado)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());
                helper.abrir();
                Usuario usuario = new Usuario();
                usuario.setUrlImagen(account.getPhotoUrl().toString());
                usuario.setCorreo(account.getEmail());
                usuario.setNomUsuario(account.getDisplayName());
                usuario.setIdUsuario(account.getId());
                usuario.setFechaUnion(date);
                helper.insertar(usuario);
                helper.cerrar();
            }
            helper.abrir();
            if(!helper.consultarSesionG(account.getId())) {
                Sesion sesion = new Sesion();
                sesion.setIdUsuario(account.getId());
                sesion.setIdSesion("SESI2");
                helper.insertar(sesion);
                EnviarCorreo(account.getEmail());
            }
            helper.cerrar();

            helper.abrir();
            Usuario usuario = helper.consultarUsuario(account.getId());
            helper.cerrar();
            nombre.setText(usuario.getNomUsuario());
            email.setText(usuario.getCorreo());
            id.setText(usuario.getIdUsuario());
            Picasso.get().load(usuario.getUrlImagen()).placeholder(R.mipmap.ic_launcher).into(imagen_perfil);
            salirbtn.setVisibility(View.VISIBLE);
            llenarLista(id.getText().toString());

        }else
        {
            helper.abrir();
            Usuario usuario = helper.consultarUsuario(idsesion);
            helper.cerrar();
            nombre.setText(usuario.getNomUsuario());
            email.setText(usuario.getCorreo());
            id.setText(usuario.getIdUsuario());
            Picasso.get().load(usuario.getUrlImagen()).placeholder(R.mipmap.ic_launcher).into(imagen_perfil);
            login.setVisibility(View.VISIBLE);
            llenarLista(id.getText().toString());
        }
    }
    private void gotoMainActivity(){
        helper.abrir();
        Sesion sesion = new Sesion();
        sesion.setIdUsuario(id.getText().toString());
        helper.eliminar(sesion);
        helper.cerrar();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        getMenuInflater().inflate(R.menu.filtro_voz, menu);
        return true;
    }
    //***********************************************************************************************

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int selected = item.getItemId();
        if (selected == R.id.action_audio){
            onClick(item.getActionView());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String audioText =  getAudioText(requestCode,resultCode,data);
       // Toast.makeText(this, audioText, Toast.LENGTH_SHORT).show();
        activityStart(audioText);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy(){
        super.onDestroy();
    }


//**********************************************************************************************************
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void irInsertarHabito(View v){
        Intent intent=new Intent(MenuPrincipalActivity.this, HabitoInsertarActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home: {
                llenarLista(id.getText().toString());
                break;
            }
            case R.id.nav_gallery: {
                Toast.makeText(this, "Esta es la gallery", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setNavigationViewListener() {
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
    }

    public class HabitoAdapter extends ArrayAdapter<Habito> {
        public HabitoAdapter(Context context, List<Habito> habitos) {
            super(context, 0, habitos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Habito habito = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_habito, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigohabito);
            TextView nombres = (TextView) convertView.findViewById(R.id.nombre);

            TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
            TextView objetivo = (TextView) convertView.findViewById(R.id.objetivo);

            // Populate the data into the template view using the data object
            codigo.setText(habito.getIdHabito());
            nombres.setText(habito.getNomHabito());
            fecha.setText(habito.getFechaCreacion());
            objetivo.setText(habito.getIdUsuario());
            return convertView;
        }
    }
    public void llenarLista(String ids)
    {
        helper.abrir();
        listaHabitos = helper.getHabitoList(ids);
        helper.cerrar();

        if(listaHabitos.size() > 0){

            adapter= new HabitoAdapter(this, listaHabitos);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listHabitos);

            listView.setAdapter(adapter);

        }
        else{
            Toast.makeText(this, "No hay habitos en la base" + ids, Toast.LENGTH_SHORT).show();
        }
    }
    public void exportarExcel(String ids){
        //generate data
        helper.abrir();
        List<Habito> listaHabitos = helper.getHabitoList(ids);
        helper.cerrar();
        StringBuilder data = new StringBuilder();
        data.append("Nombre habito,Objetivo del Habito,Fecha Creacion");
        for(int i =0;i<listaHabitos.size();i++){
            Habito list = listaHabitos.get(i);
            data.append("\n"+list.getNomHabito()+","+list.getObjetivo()+","+list.getFechaCreacion());
        }

        try{
            //saving the file into device
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.habitos_saludables_g10.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exportarPDF(String ids) throws FileNotFoundException {
        //generate data
        helper.abrir();
        List<Habito> listaHabitos = helper.getHabitoList(ids);
        Usuario usuario= helper.consultarUsuario(ids);
        helper.cerrar();



        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"mypdf.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(0,0,0,0);

        Drawable d = getDrawable(R.drawable.banner);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        Paragraph grupo1 = new Paragraph("App para promover Habitos saludables").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        Paragraph grupo2 = new Paragraph("PDM115 - G10").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);

        Paragraph paragraph = new Paragraph("Hola "+usuario.getNomUsuario()+", aqui una exportacion de sus habitos").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");

        document.add(image);
        document.add(grupo1);
        document.add(grupo2);
        document.add(paragraph);

        for(int i =0;i<listaHabitos.size();i++){
            Habito list = listaHabitos.get(i);
            Paragraph Titulo = new Paragraph(list.getNomHabito()+"con Codigo:"+list.getIdHabito()).setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
            float[] width2 = {100f,100f};
            Table tablaDatos = new Table(width2);
            tablaDatos.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tablaDatos.addCell(new Cell().add(new Paragraph("Codigo:")));
            tablaDatos.addCell(new Cell().add(new Paragraph(list.getIdHabito())));
            tablaDatos.addCell(new Cell().add(new Paragraph("Fecha Creado:")));
            tablaDatos.addCell(new Cell().add(new Paragraph(list.getFechaCreacion())));
            tablaDatos.addCell(new Cell().add(new Paragraph("Objetivo:")));
            tablaDatos.addCell(new Cell().add(new Paragraph(list.getObjetivo())));
            document.add(Titulo);
            document.add(tablaDatos);
        }
        Paragraph pie = new Paragraph("Documento creado: "+LocalDate.now().format(dateFormatter).toString() +"hora: "+LocalDateTime.now().format(TimeFormatter).toString()).setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM);
        document.add(pie);
        document.close();
        Toast.makeText(this, "Creado", Toast.LENGTH_SHORT).show();


    }
    public void EnviarCorreo(String correo)
    {
        sEmail ="pdm18003@gmail.com";//NO cambiar, correo de la empresa
        sPassword="wal123456";//NO CAMBIAR
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","587");

        try
        {
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sEmail, sPassword);
                }
            });
            //Toast.makeText(this, ""+correo, Toast.LENGTH_SHORT).show();
            if(session != null)
            {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sEmail));
                message.setSubject("Alerta");
                message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(correo));//va el correo del usuario
                message.setContent("Espero hayas sido tu que haya iniciado sesion", "text/html; charset=utf-8");//mensaje
                Transport.send(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //***************************************************************************************************************************
    public void activityStart(String parametroActivity){

        String sSubCadena ="";
        String subCadena2="";
        if (parametroActivity.length() > 5){
            sSubCadena = parametroActivity.substring(0,6);
            subCadena2 = parametroActivity.substring(6,parametroActivity.length()).trim();
            if (sSubCadena.equals("habito")){
                parametroActivity = sSubCadena;
            }
        }

        switch (parametroActivity){
            case "agregar habito":
                irInsertarHabito(new View(this));
                break;
            case "habito":
                List<Habito> newList = new ArrayList<>();;
                for (int i=0; i< listaHabitos.size(); i++){

                    String nomHabito = listaHabitos.get(i).getNomHabito();
                    String datoFiltro;
                   datoFiltro = Normalizer.normalize(nomHabito, Normalizer.Form.NFD);
                    datoFiltro = datoFiltro.replace(" ","").replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
                    if (datoFiltro.equals(subCadena2.replace(" ",""))){
                        newList.add(listaHabitos.get(i));
                        i = listaHabitos.size();
                        adapter= new HabitoAdapter(this, newList);
                        lista.setAdapter(adapter);
                    }
                }
                if (newList.size() == 0){
                    adapter= new HabitoAdapter(this, listaHabitos);
                    lista.setAdapter(adapter);
                }

                break;
            case "listado de habitos":
                adapter= new HabitoAdapter(this, listaHabitos);
                lista.setAdapter(adapter);
                break;
            case "cerrar app":
                //finish(); System.exit(0);
                moveTaskToBack(true); finish();
                // android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }

    }


    //****************************************************************************************************************************

}