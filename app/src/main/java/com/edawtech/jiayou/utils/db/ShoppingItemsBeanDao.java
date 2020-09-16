package com.edawtech.jiayou.utils.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.edawtech.jiayou.config.bean.ShoppingItemsBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * ClassName:      ShoppingItemsBeanDao
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 15:37
 * <p>
 * Description:     购物车数据库
 *
 */
public class ShoppingItemsBeanDao extends AbstractDao<ShoppingItemsBean, Long> {

    public static final String TABLENAME = "SHOPPING_ITEMS_BEAN";

    /**
     * Properties of entity ShoppingItemsBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static org.greenrobot.greendao.Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property Ischeck = new Property(2, boolean.class, "ischeck", false, "ISCHECK");
        public final static Property Image = new Property(3, String.class, "image", false, "IMAGE");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property Num = new Property(5, int.class, "num", false, "NUM");
        public final static Property MallPrice = new Property(6, String.class, "mallPrice", false, "MALL_PRICE");
        public final static Property JdPrice = new Property(7, String.class, "jdPrice", false, "JD_PRICE");
        public final static Property Desc = new Property(8, String.class, "desc", false, "DESC");
        public final static Property Property = new Property(9, String.class, "property", false, "PROPERTY");
        public final static Property ProductNo = new Property(10, String.class, "productNo", false, "PRODUCT_NO");
        public final static Property ColumnId = new Property(11, String.class, "columnId", false, "COLUMN_ID");
        public final static Property ConversionPrice = new Property(12, String.class, "conversionPrice", false, "CONVERSION_PRICE");
        public final static Property Coupon = new Property(13, String.class, "coupon", false, "COUPON");
        public final static Property IsExchange = new Property(14, String.class, "isExchange", false, "IS_EXCHANGE");
        public final static Property DeliveryType = new Property(15, String.class, "deliveryType", false, "DELIVERY_TYPE");
        public final static Property MImgaeUrl = new Property(16, String.class, "mImgaeUrl", false, "M_IMGAE_URL");
        public final static Property LogisticsMinNum = new Property(17, int.class, "logisticsMinNum", false, "LOGISTICS_MIN_NUM");
        public final static Property AddNum = new Property(18, int.class, "addNum", false, "ADD_NUM");
        public final static Property DeliveryMsg = new Property(19, String.class, "deliveryMsg", false, "DELIVERY_MSG");
    }


    public ShoppingItemsBeanDao(DaoConfig config) {
        super(config);
    }

    public ShoppingItemsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SHOPPING_ITEMS_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"ISCHECK\" INTEGER NOT NULL ," + // 2: ischeck
                "\"IMAGE\" TEXT," + // 3: image
                "\"NAME\" TEXT," + // 4: name
                "\"NUM\" INTEGER NOT NULL ," + // 5: num
                "\"MALL_PRICE\" TEXT," + // 6: mallPrice
                "\"JD_PRICE\" TEXT," + // 7: jdPrice
                "\"DESC\" TEXT," + // 8: desc
                "\"PROPERTY\" TEXT," + // 9: property
                "\"PRODUCT_NO\" TEXT," + // 10: productNo
                "\"COLUMN_ID\" TEXT," + // 11: columnId
                "\"CONVERSION_PRICE\" TEXT," + // 12: conversionPrice
                "\"COUPON\" TEXT," + // 13: coupon
                "\"IS_EXCHANGE\" TEXT," + // 14: isExchange
                "\"DELIVERY_TYPE\" TEXT," + // 15: deliveryType
                "\"M_IMGAE_URL\" TEXT," + // 16: mImgaeUrl
                "\"LOGISTICS_MIN_NUM\" INTEGER NOT NULL ," + // 17: logisticsMinNum
                "\"ADD_NUM\" INTEGER NOT NULL ," + // 18: addNum
                "\"DELIVERY_MSG\" TEXT);"); // 19: deliveryMsg
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SHOPPING_ITEMS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ShoppingItemsBean entity) {
        stmt.clearBindings();

        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }

        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
        stmt.bindLong(3, entity.getIscheck() ? 1L: 0L);

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(4, image);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
        stmt.bindLong(6, entity.getNum());

        String mallPrice = entity.getMallPrice();
        if (mallPrice != null) {
            stmt.bindString(7, mallPrice);
        }

        String jdPrice = entity.getJdPrice();
        if (jdPrice != null) {
            stmt.bindString(8, jdPrice);
        }

        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(9, desc);
        }

        String property = entity.getProperty();
        if (property != null) {
            stmt.bindString(10, property);
        }

        String productNo = entity.getProductNo();
        if (productNo != null) {
            stmt.bindString(11, productNo);
        }

        String columnId = entity.getColumnId();
        if (columnId != null) {
            stmt.bindString(12, columnId);
        }

        String conversionPrice = entity.getConversionPrice();
        if (conversionPrice != null) {
            stmt.bindString(13, conversionPrice);
        }

        String coupon = entity.getCoupon();
        if (coupon != null) {
            stmt.bindString(14, coupon);
        }

        String isExchange = entity.getIsExchange();
        if (isExchange != null) {
            stmt.bindString(15, isExchange);
        }

        String deliveryType = entity.getDeliveryType();
        if (deliveryType != null) {
            stmt.bindString(16, deliveryType);
        }

        String mImgaeUrl = entity.getMImgaeUrl();
        if (mImgaeUrl != null) {
            stmt.bindString(17, mImgaeUrl);
        }
        stmt.bindLong(18, entity.getLogisticsMinNum());
        stmt.bindLong(19, entity.getAddNum());

        String deliveryMsg = entity.getDeliveryMsg();
        if (deliveryMsg != null) {
            stmt.bindString(20, deliveryMsg);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ShoppingItemsBean entity) {
        stmt.clearBindings();

        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }

        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
        stmt.bindLong(3, entity.getIscheck() ? 1L: 0L);

        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(4, image);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
        stmt.bindLong(6, entity.getNum());

        String mallPrice = entity.getMallPrice();
        if (mallPrice != null) {
            stmt.bindString(7, mallPrice);
        }

        String jdPrice = entity.getJdPrice();
        if (jdPrice != null) {
            stmt.bindString(8, jdPrice);
        }

        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(9, desc);
        }

        String property = entity.getProperty();
        if (property != null) {
            stmt.bindString(10, property);
        }

        String productNo = entity.getProductNo();
        if (productNo != null) {
            stmt.bindString(11, productNo);
        }

        String columnId = entity.getColumnId();
        if (columnId != null) {
            stmt.bindString(12, columnId);
        }

        String conversionPrice = entity.getConversionPrice();
        if (conversionPrice != null) {
            stmt.bindString(13, conversionPrice);
        }

        String coupon = entity.getCoupon();
        if (coupon != null) {
            stmt.bindString(14, coupon);
        }

        String isExchange = entity.getIsExchange();
        if (isExchange != null) {
            stmt.bindString(15, isExchange);
        }

        String deliveryType = entity.getDeliveryType();
        if (deliveryType != null) {
            stmt.bindString(16, deliveryType);
        }

        String mImgaeUrl = entity.getMImgaeUrl();
        if (mImgaeUrl != null) {
            stmt.bindString(17, mImgaeUrl);
        }
        stmt.bindLong(18, entity.getLogisticsMinNum());
        stmt.bindLong(19, entity.getAddNum());

        String deliveryMsg = entity.getDeliveryMsg();
        if (deliveryMsg != null) {
            stmt.bindString(20, deliveryMsg);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public ShoppingItemsBean readEntity(Cursor cursor, int offset) {
        ShoppingItemsBean entity = new ShoppingItemsBean( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
                cursor.getShort(offset + 2) != 0, // ischeck
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // image
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
                cursor.getInt(offset + 5), // num
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mallPrice
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // jdPrice
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // desc
                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // property
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // productNo
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // columnId
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // conversionPrice
                cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // coupon
                cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // isExchange
                cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // deliveryType
                cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // mImgaeUrl
                cursor.getInt(offset + 17), // logisticsMinNum
                cursor.getInt(offset + 18), // addNum
                cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19) // deliveryMsg
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, ShoppingItemsBean entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setIscheck(cursor.getShort(offset + 2) != 0);
        entity.setImage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNum(cursor.getInt(offset + 5));
        entity.setMallPrice(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setJdPrice(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDesc(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setProperty(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setProductNo(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setColumnId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setConversionPrice(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCoupon(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setIsExchange(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setDeliveryType(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setMImgaeUrl(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setLogisticsMinNum(cursor.getInt(offset + 17));
        entity.setAddNum(cursor.getInt(offset + 18));
        entity.setDeliveryMsg(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
    }

    @Override
    protected final Long updateKeyAfterInsert(ShoppingItemsBean entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }

    @Override
    public Long getKey(ShoppingItemsBean entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ShoppingItemsBean entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

}
