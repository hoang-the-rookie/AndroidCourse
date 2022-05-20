package edu.hanu.mycart;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import edu.hanu.mycart.adapters.ProductAdapter;
import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.Type;

public class BaseActivity extends AppCompatActivity {

//    get ref
    MenuItem btnRedirect, btnSearch;
    SearchView searchView;
    ProductAdapter productAdapter;

//    init
    String status;
    List<Product> products;

//    constructor
    public BaseActivity(String status){
        this.status = status;
    }
    public BaseActivity(String status, List<Product> products){
        this.status = status;
        this.products = products;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        productAdapter = new ProductAdapter(products, BaseActivity.this, Type.HOME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        btnSearch = menu.findItem(R.id.btn_search);

        if (status.equals("Cart")) btnSearch.setVisible(false);

        searchView = (SearchView) btnSearch.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
//TODO get action
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                productAdapter.getFilter().filter(s);
                Toast.makeText(BaseActivity.this, "Searching for " + s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productAdapter.getFilter().filter(s);
                Toast.makeText(BaseActivity.this, "You type " + s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        btnRedirect = menu.findItem(R.id.btn_redirect);

        if(status.equals("Cart")){
            btnRedirect.setIcon(R.drawable.ic_home)
                    .setTitle(R.string.menu_home);
            return true;
        } else {
            btnRedirect.setIcon(R.drawable.ic_cart)
                    .setTitle(R.string.menu_cart);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case(R.id.btn_redirect):
                if (item.getTitle().equals("Cart")){
                    status = "Home";
                    changeAct(CartActivity.class);
                } else {
                    status = "Cart";
                    finish();
                    changeAct(MainActivity.class);
                }
                break;
        }

        return false;
    }

    public void changeAct(Class activity){
        Intent intent = new Intent(getApplicationContext(), activity);
        if (status.equals("Cart")) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
