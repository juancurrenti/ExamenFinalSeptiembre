package com.inmobiliaria.examenseptiembrefinal.ui.cargar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.inmobiliaria.examenseptiembrefinal.ProductoViewModel;
import com.inmobiliaria.examenseptiembrefinal.R;
import com.inmobiliaria.examenseptiembrefinal.databinding.FragmentCargarBinding;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CargarFragment extends Fragment {

    private FragmentCargarBinding binding;
    private ProductoViewModel productoViewModel;
    private Uri imagenSeleccionadaUri;

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    imagenSeleccionadaUri = uri;
                    Glide.with(requireContext()).load(imagenSeleccionadaUri).into(binding.imageViewPreview);
                }
            });

    private final ActivityResultLauncher<Uri> takePicture = registerForActivityResult(
            new ActivityResultContracts.TakePicture(),
            success -> {
                if (success) {
                    Glide.with(requireContext()).load(imagenSeleccionadaUri).into(binding.imageViewPreview);
                } else {
                    if (imagenSeleccionadaUri != null) {
                        imagenSeleccionadaUri = null;
                    }
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getContext(), "Permiso de camara denegado", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);
        binding = FragmentCargarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonSeleccionarGaleria.setOnClickListener(v -> getContent.launch("image/*"));

        binding.buttonTomarFoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });


        binding.buttonAgregar.setOnClickListener(v -> {
            String codigo = binding.editTextCodigo.getText().toString();
            String descripcion = binding.editTextDescripcion.getText().toString();
            String precio = binding.editTextPrecio.getText().toString();

            String uriString = (imagenSeleccionadaUri != null) ? imagenSeleccionadaUri.toString() : null;
            productoViewModel.agregarProducto(codigo, descripcion, precio, uriString);
        });

        productoViewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                if(message.contains("exito")){
                    binding.editTextCodigo.setText("");
                    binding.editTextDescripcion.setText("");
                    binding.editTextPrecio.setText("");
                    binding.editTextCodigo.requestFocus();

                    binding.imageViewPreview.setImageResource(R.drawable.ic_menu_gallery);
                    imagenSeleccionadaUri = null;
                }
                productoViewModel.onToastMessageShown();
            }
        });

        return root;
    }

    private void dispatchTakePictureIntent() {
        ContentResolver resolver = requireContext().getContentResolver();
        ContentValues contentValues = new ContentValues();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MiInventarioApp");
        }

        imagenSeleccionadaUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        if (imagenSeleccionadaUri != null) {
            takePicture.launch(imagenSeleccionadaUri);
        } else {
            Toast.makeText(getContext(), "No se pudo crear el archivo para la imagen", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}