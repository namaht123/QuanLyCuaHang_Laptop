package com.example.quanlylaptop.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quanlylaptop.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "laptop_store.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_URL = "image_url";

    // SQL câu lệnh tạo bảng
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_CODE + " TEXT, "
            + COLUMN_PRICE + " REAL, "
            + COLUMN_IMAGE_URL + " TEXT" + ")";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng khi lần đầu tiên tạo cơ sở dữ liệu
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ và tạo lại bảng mới khi có sự thay đổi cấu trúc cơ sở dữ liệu
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Thêm một sản phẩm mới vào cơ sở dữ liệu
    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CODE, product.getCode());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE_URL, product.getImageUrl());

        // Chèn sản phẩm vào cơ sở dữ liệu
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }

    // Lấy tất cả sản phẩm từ cơ sở dữ liệu
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn để lấy tất cả sản phẩm
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // Kiểm tra cột có tồn tại hay không
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
                int codeColumnIndex = cursor.getColumnIndex(COLUMN_CODE);
                int priceColumnIndex = cursor.getColumnIndex(COLUMN_PRICE);
                int imageUrlColumnIndex = cursor.getColumnIndex(COLUMN_IMAGE_URL);

                if (idColumnIndex != -1 && nameColumnIndex != -1 && codeColumnIndex != -1 && priceColumnIndex != -1 && imageUrlColumnIndex != -1) {
                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String code = cursor.getString(codeColumnIndex);
                    double price = cursor.getDouble(priceColumnIndex);
                    String imageUrl = cursor.getString(imageUrlColumnIndex);

                    Product product = new Product(id, name, code, price, imageUrl);
                    products.add(product);
                }

                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return products;
    }

    // Lấy một sản phẩm theo ID
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, null, COLUMN_ID + "=?", new String[]{String.valueOf(productId)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // Kiểm tra cột có tồn tại hay không
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
            int codeColumnIndex = cursor.getColumnIndex(COLUMN_CODE);
            int priceColumnIndex = cursor.getColumnIndex(COLUMN_PRICE);
            int imageUrlColumnIndex = cursor.getColumnIndex(COLUMN_IMAGE_URL);

            if (idColumnIndex != -1 && nameColumnIndex != -1 && codeColumnIndex != -1 && priceColumnIndex != -1 && imageUrlColumnIndex != -1) {
                int id = cursor.getInt(idColumnIndex);
                String name = cursor.getString(nameColumnIndex);
                String code = cursor.getString(codeColumnIndex);
                double price = cursor.getDouble(priceColumnIndex);
                String imageUrl = cursor.getString(imageUrlColumnIndex);

                Product product = new Product(id, name, code, price, imageUrl);
                cursor.close();
                db.close();
                return product;
            }
        }

        db.close();
        return null;
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_CODE, product.getCode());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_IMAGE_URL, product.getImageUrl());

        int rowsUpdated = db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close();

        return rowsUpdated > 0;
    }

    // Xóa sản phẩm
    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa sản phẩm khỏi cơ sở dữ liệu
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    //timkiem
    // Tìm kiếm sản phẩm theo tên hoặc mã
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn sản phẩm với điều kiện LIKE
        String query = "SELECT * FROM " + TABLE_PRODUCTS +
                " WHERE " + COLUMN_NAME + " LIKE ? OR " + COLUMN_CODE + " LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                // Kiểm tra cột có tồn tại hay không
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
                int codeColumnIndex = cursor.getColumnIndex(COLUMN_CODE);
                int priceColumnIndex = cursor.getColumnIndex(COLUMN_PRICE);
                int imageUrlColumnIndex = cursor.getColumnIndex(COLUMN_IMAGE_URL);

                if (idColumnIndex != -1 && nameColumnIndex != -1 && codeColumnIndex != -1 && priceColumnIndex != -1 && imageUrlColumnIndex != -1) {
                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String code = cursor.getString(codeColumnIndex);
                    double price = cursor.getDouble(priceColumnIndex);
                    String imageUrl = cursor.getString(imageUrlColumnIndex);

                    Product product = new Product(id, name, code, price, imageUrl);
                    products.add(product);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return products;
    }

}
