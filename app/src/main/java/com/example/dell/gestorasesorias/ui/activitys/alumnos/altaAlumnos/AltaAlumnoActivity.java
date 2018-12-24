package com.example.dell.gestorasesorias.ui.activitys.alumnos.altaAlumnos;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.gestorasesorias.R;
import com.example.dell.gestorasesorias.base.BaseActivity;
import com.example.dell.gestorasesorias.ui.activitys.alumnos.perfilAlumno.AlumnosActivity;
import com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros.AltaMaestroActivity;
import com.example.dell.gestorasesorias.ui.activitys.maestros.altaMaestros.AltaMaestroPresenter;
import com.example.dell.gestorasesorias.ui.activitys.maestros.perfilMaestro.MaestrosActivity;
import com.example.dell.gestorasesorias.utils.Mail;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Dell on 12/06/2018.
 */

public class AltaAlumnoActivity extends BaseActivity implements AltaAlumnoPresenter.View{

    private AltaAlumnoPresenter altaAlumnoPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alta_alumno;

    }

    /*datos necesarios para almacenar la imagen*/
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private  final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;


    /*campos obligatorios para el envio de correo*/
    private static final String  USER="elCirculoDelExito199@gmail.com";
    private static final String  PASS="Algoritmo199001";
    private static final String  AFFAIR_NEW_PAQUETE="paquete vendido";
    private static final String  AFFAIR_NEW_CLASS="alumno por tiempo";
    private static final String  RECEPT="linkinluisave@gmail.com";

    private String mPath;

    @BindView(R.id.ib_camera_alumno)
    ImageButton ibAlumno;

    @BindView(R.id.et_alta_alumno_nombre)
    EditText etNombreAlumno;

    @BindView(R.id.et_alta_alumno_padre_nombre)
    EditText etNombrePadre;

    @BindView(R.id.et_alta_alumno_telefono)
    EditText etPhoneAlumno;

    @BindView(R.id.et_alta_alumno_telefono_padre)
    EditText etPhonePadre;

    @BindView(R.id.profile_cv_contact_alumno)
    CircleImageView civAlumno;

    Bitmap bitmap;

    @Override
    protected void initView() {
        super.initView();
        getSupportActionBar().setTitle("Alta de alumnos");
        if(mayRequestStoragePermission())
            ibAlumno.setEnabled(true);
        else
            ibAlumno.setEnabled(false);

    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        altaAlumnoPresenter = new AltaAlumnoPresenter(this , this);
    }


    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(civAlumno, "Los permisos son necesarios para poder usar la aplicación",
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




    @OnClick(R.id.ib_camera_alumno)
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
                    Glide.with(this).load(mPath).into(civAlumno);
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
                    Glide.with(this).load(imageUri).into(civAlumno);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AltaAlumnoActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                ibAlumno.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AltaAlumnoActivity.this);
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

    @OnClick(R.id.btn_registrar_alumno)
    public  void registrarAlumno(){
        String datosAlumnoString =
                "Alumno : " + etNombreAlumno.getText().toString() + "\n" +
                "Padre: " + etNombrePadre.getText().toString() + "\n" +
                "teléfono alumno : " + etPhoneAlumno.getText().toString() + "\n" +
                "teléfono padre : " + etPhonePadre.getText().toString();

        sendMessage(datosAlumnoString);
        altaAlumnoPresenter.setDataAlumno(
                etNombreAlumno.getText().toString() ,
                etNombrePadre.getText().toString() ,
                etPhoneAlumno.getText().toString() ,
                etPhonePadre.getText().toString() , bitmap );
    }


    @Override
    public void onErrorNombre() {
        Toast.makeText(getApplicationContext() , "debe ingresar un nombre", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorNombrePadre() {
        Toast.makeText(getApplicationContext() , "debe ingresar un nombre de padre", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorTelefono() {
        Toast.makeText(getApplicationContext() , "debe ingresar un teléfono", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorTelefonoPadre() {
        Toast.makeText(getApplicationContext() , "debe ingresar un teléfono de padre", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCamaraError() {

    }

    @Override
    public void onErrorDB() {
        Toast.makeText(getApplicationContext(), "Error al insertar en la base de datos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataSuccessDB() {
        Toast.makeText(getApplicationContext() , "Registro Exitoso", Toast.LENGTH_LONG).show();
        startActivity( new Intent(this , AlumnosActivity.class));
    }


    public String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File directory = wrapper.getDir("images", Context.MODE_PRIVATE);
        File path = new File(directory, "output.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path.getAbsolutePath();
    }

    public static Bitmap TextToImageEncode(String Value, Context context) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        context.getResources().getColor(R.color.black_qr):context.getResources().getColor(R.color.white_qr);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void sendMessage(String msj) {
        String[] recipients = { RECEPT };
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.activity = this;
        email.m = new Mail(USER, PASS);
        email.m.set_from(USER);
        email.m.setBody(msj);
        email.m.set_to(recipients);
        email.m.set_subject(AFFAIR_NEW_PAQUETE);
        Bitmap qrImage = null;
        try {
            qrImage = TextToImageEncode(msj, this);
        } catch (WriterException e) {
            e.printStackTrace();
        }
//        QR.setImageBitmap(qrImage);
        String path = saveToInternalStorage(qrImage);
        try {
            email.m.addAttachment(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        email.execute();
    }



}

class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    Mail m;
    AltaAlumnoActivity activity;

    public SendEmailAsyncTask() {}

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
//                Toast.makeText(activity, "email enviado", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(activity, "fallo al enviar email", Toast.LENGTH_SHORT).show();
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            Toast.makeText(activity, "autenticacion fallo", Toast.LENGTH_SHORT).show();
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            Toast.makeText(activity, "fallo al enviar datos", Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Ocurrio un error inesperado", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
