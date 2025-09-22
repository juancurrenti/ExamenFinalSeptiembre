package com.inmobiliaria.examenseptiembrefinal.ui.listar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.inmobiliaria.examenseptiembrefinal.ProductoAdapter;
import com.inmobiliaria.examenseptiembrefinal.ProductoViewModel;
import com.inmobiliaria.examenseptiembrefinal.databinding.FragmentListarBinding;


public class ListarFragment extends Fragment {

    private FragmentListarBinding binding;
    private ProductoViewModel productoViewModel;
    private ProductoAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);
        binding = FragmentListarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewProductos.setHasFixedSize(true);
        adapter = new ProductoAdapter();
        binding.recyclerViewProductos.setAdapter(adapter);

        productoViewModel.getListaProductosLiveData().observe(getViewLifecycleOwner(), productos -> {
            adapter.setProductos(productos);
        });

        productoViewModel.actualizarLiveData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}