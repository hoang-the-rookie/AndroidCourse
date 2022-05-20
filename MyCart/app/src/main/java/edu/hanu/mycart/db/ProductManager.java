package edu.hanu.mycart.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.hanu.mycart.models.Product;

public class ProductManager {

//    init
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor results;
    List<Product> products;

    private static SQLiteStatement statement;
    private static final String INSERT = "INSERT INTO " + DbSchema.CartTable.TABLE + "(id, name, image, price, quantity) VALUES(?,?,?,?,?);";
    private static final String UPDATE = "UPDATE " + DbSchema.CartTable.TABLE + " SET quantity = ? WHERE id = ?;";

//    set instance
    private static ProductManager instance;

//    constructor
    public ProductManager(Context context) {
        this.dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        products = new ArrayList<>();
    }

    public static ProductManager getInstance(Context context){
        if(instance == null){
            instance = new ProductManager(context);
        }

        return instance;
    }

//function

//    get all prod in cart
    public List<Product> all(){
        results = db.rawQuery("SELECT * FROM cart", null);
        ProductCursorWrapper cursor = new ProductCursorWrapper(results);

        return cursor.getProducts();
    }

//  add prod to cart
    public boolean add(Product product){
        statement = db.compileStatement(INSERT);

        statement.bindLong(1, product.getId());
        statement.bindString(2, product.getName());
        statement.bindString(3, product.getThumbnail());
        statement.bindString(4, product.getPrice());
        statement.bindLong(5, product.getQuantity());

        long no = statement.executeInsert();
        if (no > 0){
            product.setNo(no);
            return true;
        }
//
        return false;
    }

//    update quantity of a prod
    public boolean update(Product product){

        statement = db.compileStatement(UPDATE);

        statement.bindLong(1, product.getQuantity());
        statement.bindLong(2, product.getId());

        long result = statement.executeUpdateDelete();

        return result == 1;
    }

//    delete a prod
    public boolean delete(long id){
        int result = db.delete(DbSchema.CartTable.TABLE, "id = ?", new String[]{id+""});
        return result > 0;
    }

//    delete all
    public boolean clean(){
        return db.delete(DbSchema.CartTable.TABLE,"1",null) != 0;
    }

//    END
}
