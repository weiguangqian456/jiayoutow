package com.edawtech.jiayou.utils.db;

import com.edawtech.jiayou.config.bean.ShoppingItemsBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * ClassName:      DaoSession
 * <p>
 * Author:
 * <p>
 * CreateDate:      2020/9/14 15:45
 * <p>
 * Description:
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig shoppingItemsBeanDaoConfig;

    private final ShoppingItemsBeanDao shoppingItemsBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        shoppingItemsBeanDaoConfig = daoConfigMap.get(ShoppingItemsBeanDao.class).clone();
        shoppingItemsBeanDaoConfig.initIdentityScope(type);

        shoppingItemsBeanDao = new ShoppingItemsBeanDao(shoppingItemsBeanDaoConfig, this);

        registerDao(ShoppingItemsBean.class, shoppingItemsBeanDao);
    }

    public void clear() {
        shoppingItemsBeanDaoConfig.clearIdentityScope();
    }

    public ShoppingItemsBeanDao getShoppingItemsBeanDao() {
        return shoppingItemsBeanDao;
    }

}

