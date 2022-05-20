package edu.hanu.mycart.db;

public class DbSchema {

    public final class CartTable{
        public static final String TABLE = "cart";

        public final class Cols{
            public static final String  NO = "number";
            public static final String  ID = "id";
            public static final String  NAME = "name";
            public static final String  PRICE = "price";
            public static final String  THUMBNAIL = "image";
            public static final String  QUANTITY = "quantity";
        }
    }

}
