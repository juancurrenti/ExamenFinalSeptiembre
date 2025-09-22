package com.inmobiliaria.examenseptiembrefinal;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos = new ArrayList<>();

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto productoActual = listaProductos.get(position);
        holder.tvCodigo.setText("Codigo: " + productoActual.getCodigo());
        holder.tvDescripcion.setText(productoActual.getDescripcion());
        holder.tvPrecio.setText("Precio: $" + String.format(Locale.getDefault(), "%.2f", productoActual.getPrecio()));

        if (productoActual.getImagenUri() != null && !productoActual.getImagenUri().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(productoActual.getImagenUri()))
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_menu_add)
                    .into(holder.imageViewProducto);
        } else {
            holder.imageViewProducto.setImageResource(R.drawable.ic_menu_gallery);
        }
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void setProductos(List<Producto> productos) {
        this.listaProductos = productos;
        notifyDataSetChanged();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCodigo;
        private final TextView tvDescripcion;
        private final TextView tvPrecio;
        private final ImageView imageViewProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.textViewCodigo);
            tvDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            tvPrecio = itemView.findViewById(R.id.textViewPrecio);
            imageViewProducto = itemView.findViewById(R.id.imageViewProducto);
        }
    }
}