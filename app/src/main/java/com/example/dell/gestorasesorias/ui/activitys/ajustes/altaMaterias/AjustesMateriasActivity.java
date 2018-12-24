package com.example.dell.gestorasesorias.ui.activitys.ajustes.altaMaterias;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.ajustes.AjustesActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Dell on 24/06/2018.
 */

public class AjustesMateriasActivity extends BaseActivity implements AjustesMateriasPresenter.View {

    private AjustesMateriasPresenter ajustesMateriasPresenter;

    @BindView(R.id.et_ajustes_materias_nombre)
    EditText etMateriaName;

    @BindView(R.id.iv_ajustes_materia_imagen)
    ImageView ivMateria;

    private final int SELECT_PICTURE = 300;
    private final int MY_PERMISSIONS = 100;

    private String APP_DIRECTORY = "myPicture";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private  final int PHOTO_CODE = 100;

    private boolean insertoImagen = false;
    private String mPath;
    Bitmap bitmap;
    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Alta de materias");
        if(mayRequestStoragePermission())
            ivMateria.setEnabled(true);
        else
            ivMateria.setEnabled(false);
    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(ivMateria, "Los permisos son necesarios para poder usar la aplicación",
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


    @Override
    protected void initPresenter() {
        super.initPresenter();
        ajustesMateriasPresenter = new AjustesMateriasPresenter(this , this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ajustes_materias;
    }

    @OnClick(R.id.btn_ajustes_materias_seleccionar)
    public void seleccionarMateria(){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                Glide.with(this).load(mPath).into(ivMateria);
                break;
            case SELECT_PICTURE:
                if(data != null){
                    Uri path = data.getData();
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(path, filePath, null, null, null);
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                    bitmap = BitmapFactory.decodeFile(imagePath, options);
                    Glide.with(this).load(path).into(ivMateria);
                }
                break;
        }
    }

    @OnClick(R.id.btn_update_materia)
    public void updateMateria(){
        ajustesMateriasPresenter.setDataMateria(etMateriaName.getText().toString() , bitmap);
    }

    @Override
    public void onErrorNombre() {
        Toast.makeText(getApplicationContext() , "debe ingresar un nombre", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorDB() {
        Toast.makeText(getApplicationContext(), "Error al insertar en la base de datos", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCamaraError() {
        Toast.makeText(getApplicationContext() , "Error al guardar la imágen", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataSuccessDB() {
        Toast.makeText(getApplicationContext() , "Registro Exitoso", Toast.LENGTH_LONG).show();
        startActivity( new Intent(this , AjustesActivity.class));
    }
}
