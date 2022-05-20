package edu.hanu.mycart.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import edu.hanu.mycart.CartActivity;
import edu.hanu.mycart.R;
import edu.hanu.mycart.db.ProductManager;
import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.Type;

/**
 * @author hoangtd _ 1901040087
 * @overview: product adapter is to handle product in both main activity and cart activity (recognise by "Type" param in constructor)
 **/
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable {

    // init ref
    Context context;
    TextView name, price, totalPrice;
    ImageButton addBtn, removeBtn;
    ImageView image;
    EditText quantity;
    String type;
    ProductManager productManager;
    boolean isSuccess = false;
    int quant, prodPrice, totalUnit, totalCheckout = 0;
    OnPriceChangeListener onPriceChangeListener;

    //dataset
    private List<Product> products;
    private List<Product> filteredProducts;  // for searchin
    private List<Product> cartList; // checkin in home

    //view holder
    protected class ProductHolder extends RecyclerView.ViewHolder {
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Product product) {

//            get info
            prodPrice = Integer.parseInt(product.getPrice());

//          get ref
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            image = itemView.findViewById(R.id.iv_img);

            switch (type) {
                case ("Home"):
                    addBtn = itemView.findViewById(R.id.ib_cart_ic);

                    // handle add btn
                    addBtn.setOnClickListener(view -> {
//                get position of product in cart
                        int position = prodPos(product.getId(), cartList);

//                if already have
                        if (position != -1) {
//                            get current quantity
                            quant = cartList.get(position).getQuantity();
                            isSuccess = changeQuantity(product, quant + 1);
                            if (isSuccess){
                                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                    add to cart list
                            product.setQuantity(1);
                            isSuccess = productManager.add(product);
                            if (isSuccess) {
                                cartList.add(product);
                                Toast.makeText(context, "Added to cart successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
                case ("Cart"):
                    addBtn = itemView.findViewById(R.id.btn_add);
                    removeBtn = itemView.findViewById(R.id.btn_remove);
                    quantity = itemView.findViewById(R.id.et_quantity);
                    totalPrice = itemView.findViewById(R.id.tv_total_price);

//                    get info
                    int quant = product.getQuantity();
                    totalUnit = quant * prodPrice;

//                    set value
                    quantity.setText(quant + "");
                    totalPrice.setText(totalUnit + "");

//                    handle button
                    addBtn.setOnClickListener(view -> {
                        getTotalCheckout(-totalCheckout); // reset totalCheckout
                        isSuccess = changeQuantity(product, quant + 1);
                        if (isSuccess) notifyDataSetChanged();
                    });

                    removeBtn.setOnClickListener(view -> {
                        getTotalCheckout(-totalCheckout); //reset totalCheckout
                        if (quant == 1) {
                            int position = prodPos(product.getId(),products);
                            Toast.makeText(context, "removing", Toast.LENGTH_SHORT).show();
                            isSuccess = productManager.delete(product.getId());
                            if (isSuccess) {
                                products.remove(position);
                                notifyDataSetChanged();
                            }
                            if (products.isEmpty()){
                                Toast.makeText(context, "Cart is empty!", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }

                        product.setQuantity(quant - 1);
                        isSuccess = productManager.update(product);
                        notifyDataSetChanged();
                    });

//                  change total checkout
                    getTotalCheckout(totalUnit);
                    break;
                default:
                    break;
            }

//            set info
            name.setText(product.getName());
            price.setText(prodPrice + "");

//            set image
            String link = product.getThumbnail();
            new LoadImage(image, context).execute(link);

        }
    }

    // constructor
    public ProductAdapter(List<Product> products, Context context, String type) {
        this.products = products;
        this.filteredProducts = products;  // set default filter list
        this.context = context;
        this.type = type;
//        data set
        productManager = ProductManager.getInstance(context);
        this.cartList = productManager.all();

//        set data transfer back
        if (type.equals("Cart")) {
            try {
                this.onPriceChangeListener = ((OnPriceChangeListener) context);
            } catch (ClassCastException e) {
                throw new ClassCastException(e.getMessage());
            }
        }
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = null;

        switch (type) {
            case "Home":
                itemView = inflater.inflate(R.layout.product_item, parent, false);
                break;
            case "Cart":
                itemView = inflater.inflate(R.layout.product_cart, parent, false);
                break;
        }

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
//        get product at position
        Product product = filteredProducts.get(position);

//      bind to view
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return filteredProducts.size();
    }

//    ==================== FUNCTION =======================

    //    load image
    private class LoadImage extends AsyncTask<String, Integer, Bitmap> {

        ImageView imgView;
        Context context;

        public LoadImage(ImageView imgView, Context context) {
            this.imgView = imgView;
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(in);
                return image;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }

    // check product in cart
    public int prodPos(long id, List<Product> products) {
        int position = -1; // position not found
        int size = products.size();

        if (size != 0) {
            for (position = 0; position < size; position++) {
                if (products.get(position).getId() == id) break; // exist -> break
            }
            if (position == size) position = -1; // not exist
        }

        return position;
    }

    // onPriceChangeListener
    public interface OnPriceChangeListener {
        void onPriceChange(int price);
    }

    // calculate
    private void getTotalCheckout(int totalUnit) {
        totalCheckout += totalUnit;

//        return totalCheckout to activity
        onPriceChangeListener.onPriceChange(totalCheckout);

    }

    // change quantity
    private boolean changeQuantity(Product product, int quantity) {
        product.setQuantity(quantity);
        return productManager.update(product);
    }

//    Search function TODO implement search function and

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String query = charSequence.toString();

                if (query.isEmpty()){
                    filteredProducts = products;
                } else{
                    filteredProducts = new ArrayList<>();
                    for (Product product : products) {
                        if(product.getName().toLowerCase().contains(query.toLowerCase())) filteredProducts.add(product);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredProducts;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredProducts = (List<Product>) filterResults.values;

                if (filteredProducts.isEmpty()) Toast.makeText(context, "No product match :( Please check again", Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();
            }
        };
    }


//=========================== END =============================
}

