package edu.hanu.mycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.hanu.mycart.adapters.ProductAdapter;
import edu.hanu.mycart.db.ProductManager;
import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.Type;

public class CartActivity extends AppCompatActivity implements ProductAdapter.OnPriceChangeListener {

    //    ref
    LinearLayout emptyNotice, checkoutBtn;
    RecyclerView rvCart;
    ProductAdapter productAdapter;
    ProductManager productManager;
    TextView tvCartTotal;
    List<Product> cartList;
    MenuItem btnRedirect, btnSearch;

//    public CartActivity() {
//        super("Cart");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        get ref
        emptyNotice = findViewById(R.id.ll_empty);
        rvCart = findViewById(R.id.rv_cart);
        tvCartTotal = findViewById(R.id.tv_cart_total);
        checkoutBtn = findViewById(R.id.ll_checkout);

//        get cart list
        productManager = ProductManager.getInstance(CartActivity.this);
        cartList = productManager.all();

//        if cartList has item, hide empty notice
        if (!cartList.isEmpty()) {
            emptyNotice.setVisibility(View.GONE);

            // display item
            productAdapter = new ProductAdapter(cartList, CartActivity.this, Type.CART);

            rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));

            rvCart.setAdapter(productAdapter);

//            check out btn
            checkoutBtn.setOnClickListener(view -> {
                if (productManager.clean()) {
                    Toast.makeText(CartActivity.this, "Ordering successfully !", Toast.LENGTH_SHORT).show();
                    changeAct(MainActivity.class);
                }
            });

        }


    }

    @Override
    public void onPriceChange(int price) {
        tvCartTotal.setText(price + "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        reset data when back
        changeAct(MainActivity.class);
    }

    //    ============================= MENU ===========================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        btnSearch = menu.findItem(R.id.btn_search);
        btnSearch.setVisible(false); // hide search btn

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        btnRedirect = menu.findItem(R.id.btn_redirect);
//      change button
        btnRedirect.setIcon(R.drawable.ic_home)
                .setTitle(R.string.menu_home);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        changeAct(MainActivity.class);

        return false;
    }

    public void changeAct(Class activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}