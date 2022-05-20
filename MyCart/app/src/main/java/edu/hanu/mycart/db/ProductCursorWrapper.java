package edu.hanu.mycart.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mycart.models.Product;

public class ProductCursorWrapper extends CursorWrapper {

    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        moveToFirst();

        while(!isAfterLast()){
            products.add(getProduct());
            moveToNext();
        }

        return products;
    }

    public Product getProduct(){

        long no = getInt(getColumnIndex(DbSchema.CartTable.Cols.NO));
        long id = getInt(getColumnIndex(DbSchema.CartTable.Cols.ID));
        String name = getString(getColumnIndex(DbSchema.CartTable.Cols.NAME));
        String thumbnail = getString(getColumnIndex(DbSchema.CartTable.Cols.THUMBNAIL));
        String price = getString(getColumnIndex(DbSchema.CartTable.Cols.PRICE));
        int quantity = getInt(getColumnIndex(DbSchema.CartTable.Cols.QUANTITY));

        return new Product(no,id,name,thumbnail,price,quantity);
    }
}
