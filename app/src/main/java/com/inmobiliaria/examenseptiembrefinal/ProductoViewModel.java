package com.inmobiliaria.examenseptiembrefinal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class ProductoViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Producto>> listaProductosLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public ProductoViewModel() {
        actualizarLiveData();
    }

    public LiveData<ArrayList<Producto>> getListaProductosLiveData() {
        return listaProductosLiveData;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void agregarProducto(String codigoStr, String descripcion, String precioStr, String imagenUri) {
        if (codigoStr.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
            toastMessage.setValue("Error: Todos los campos son obligatorios.");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            double precio = Double.parseDouble(precioStr);

            for (Producto p : MainActivity.listaProductos) {
                if (p.getCodigo() == codigo) {
                    toastMessage.setValue("Error: El codigo del producto ya existe.");
                    return;
                }
            }

            Producto nuevoProducto = new Producto(codigo, descripcion, precio, imagenUri);
            MainActivity.listaProductos.add(nuevoProducto);
            toastMessage.setValue("¡Producto agregado con exito!");
            actualizarLiveData();

        } catch (NumberFormatException e) {
            toastMessage.setValue("Error: Codigo y Precio deben ser numeros validos.");
        }
    }

    public void actualizarLiveData() {
        Collections.sort(MainActivity.listaProductos);
        listaProductosLiveData.setValue(MainActivity.listaProductos);
    }
    public void onToastMessageShown() {
        toastMessage.setValue(null);
    }
}
