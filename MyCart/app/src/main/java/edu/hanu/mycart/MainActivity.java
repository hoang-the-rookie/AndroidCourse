package edu.hanu.mycart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.hanu.mycart.adapters.ProductAdapter;
import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.Type;

public class MainActivity extends AppCompatActivity {

//    dataset
    private List<Product> productList;

//    ref
    private RecyclerView rvMain;
    private ProductAdapter productAdapter;
    MenuItem btnSearch;
    SearchView searchView;
    final String URL = "https://mpr-cart-api.herokuapp.com/products";

//    get product
    public class GetProduct extends AsyncTask<String,Void,String> {

    @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;

    //        connect to url
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

    //            reading result as string
                Scanner sc = new Scanner(urlConnection.getInputStream());
                StringBuilder result = new StringBuilder();
                while(sc.hasNextLine()){
                    result.append(sc.nextLine());
                }
//                Log.d("RESULT", result.toString());
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


//            get data
            if (result == null || result.equals("")){
                Toast.makeText(MainActivity.this, "Can not connect to server. Please try again!", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                //            get product list from json
                JSONArray products = new JSONArray(result);

                //            add product to list
                for(int i = 0; i < products.length(); i++){
                    //                get product TODO: update view by JSON ?
                    JSONObject product = products.getJSONObject(i);
                    long id = product.getLong("id");
                    String name = product.getString("name");
                    String thumbnail = product.getString("thumbnail");
                    String price = product.getString("unitPrice");

//                add new product to list
                    Product newProduct = new Product(id,name,thumbnail,price);
                    productList.add(newProduct);
                }

//                Toast.makeText(MainActivity.this,"Loading image, please wait!", Toast.LENGTH_SHORT).show();

    //            call adapter TODO: check sample code
                productAdapter = new ProductAdapter(productList,MainActivity.this, Type.HOME);

                rvMain.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                rvMain.setAdapter(productAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

//    create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        get ref
        rvMain = findViewById(R.id.rv_main);

//        create list product
        productList = new ArrayList<>();

        GetProduct getProduct = new GetProduct();
        getProduct.execute(URL);

    }

//    ========================== MENU ===========================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        btnSearch = menu.findItem(R.id.btn_search);
        searchView = (SearchView) btnSearch.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
//TODO get action
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                productAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                productAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        startActivity(intent);

        return false;
    }

}