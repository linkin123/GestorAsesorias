package com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro.MaestrosActivity;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Dell on 04/06/2018.
 */

public class AltaMaestroActivity extends BaseActivity implements  AltaMaestroPresenter.View, RadioGroup.OnCheckedChangeListener {

    private AltaMaestroPresenter altaMaestroPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alta_maestro;
    }

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private String MATUTINO = "matutino";
    private String VERPERTINO = "verpertino";
    private String horario = MATUTINO;

    private String mPath;

    @BindView(R.id.ib_camera_maestro)
    ImageButton ibMaestro;

    @BindView(R.id.et_alta_maestro_nombre)
    EditText etNombreMaestro;

    @BindView(R.id.et_alta_maestro_domicilio)
    EditText etDomicilioMaestro;

    @BindView(R.id.et_alta_maestro_telefono)
    EditText etPhoneMaestro;

    @BindView(R.id.et_alta_maestro_descripcion)
    EditText etDescripcionMaestro;

    @BindView(R.id.profile_cv_contact_maestro)
    CircleImageView civMaestro;

    @BindView(R.id.rg_maestro_horario)
    RadioGroup rgHorario;

    @BindView(R.id.rbtn_matutino)
    RadioButton rbtnMatutino;

    @BindView(R.id.rbtn_vespertino)
    RadioButton rbtnVespertino;

    Bitmap bitmap;

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Alta de maestros");
        if(mayRequestStoragePermission())
            ibMaestro.setEnabled(true);
        else
            ibMaestro.setEnabled(false);

        rgHorario.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        altaMaestroPresenter = new AltaMaestroPresenter(this , this);
    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(civMaestro, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }


    @OnClick(R.id.ib_camera_maestro)
    public void tomarFoto(){
        createCustomDialog().show();

    }

    public android.app.AlertDialog createCustomDialog() {
        final android.app.AlertDialog alertDialog;
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.dialog_photo, null);
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        Button btnFire = (Button) v.findViewById(R.id.btn_camera);
        Button btnCancel = (Button) v.findViewById(R.id.btn_gallery);
        builder.setView(v);
        alertDialog = builder.create();
        // Add action buttons
        btnFire.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Aceptar
                        openCamera();
                        alertDialog.dismiss();
                    }
                }
        );
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                        alertDialog.dismiss();
                    }
                }
        );
        return alertDialog;
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    private void openGallery() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            gallery.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(gallery, SELECT_PICTURE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });
                    bitmap = BitmapFactory.decodeFile(mPath);
                    Glide.with(this).load(mPath).into(civMaestro);
                    break;
                case SELECT_PICTURE:
                    Uri imageUri = data.getData();

                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                    bitmap = BitmapFactory.decodeFile(imagePath, options);
                    Glide.with(this).load(imageUri).into(civMaestro);
                    break;

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AltaMaestroActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                ibMaestro.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AltaMaestroActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    @OnClick(R.id.btn_registrar_maestro)
    public  void registrarMaestro(){
        altaMaestroPresenter.setDataMaestro(
                etNombreMaestro.getText().toString() ,
                etDomicilioMaestro.getText().toString() ,
                etPhoneMaestro.getText().toString() ,
                horario.toString() , bitmap , etDescripcionMaestro.getText().toString()

        );
    }

    @Override
    public void onErrorNombre() {
        Toast.makeText(getApplicationContext() , "debe ingresar un nombre", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorDomicilio() {
        Toast.makeText(getApplicationContext() , "debe ingresar un domicilio", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorTelefono() {
        Toast.makeText(getApplicationContext() , "debe ingresar un teléfono", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCamaraError() {
        Toast.makeText(getApplicationContext() , "no se pudo guardar la imágen", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(getApplicationContext(), "Error al insertar en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSuccessDB() {
        Toast.makeText(getApplicationContext() , "Registro Exitoso", Toast.LENGTH_LONG).show();
        startActivity( new Intent(this , MaestrosActivity.class));
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rbtn_matutino:
                horario = MATUTINO;
                break;
            case R.id.rbtn_vespertino:
                horario = VERPERTINO;
                break;
        }

    }
}
