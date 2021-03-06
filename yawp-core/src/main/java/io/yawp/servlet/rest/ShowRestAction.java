package io.yawp.servlet.rest;

import io.yawp.commons.http.HttpException;
import io.yawp.repository.query.NoResultException;
import io.yawp.repository.query.QueryBuilder;

public class ShowRestAction extends RestAction {

    public ShowRestAction() {
        super("show");
    }

    @Override
    public void shield() {
        shield.protectShow();
    }

    @Override
    public Object action() {
        QueryBuilder<?> query = query();

        if (hasTransformer()) {
            Object object = query.transform(getTransformerName()).fetch(id);
            applyGetFacade(object);
            return object;
        }

        if (hasShieldCondition()) {
            query.and(shield.getWhere());
        }

        try {

            Object object = query.fetch(id);
            applyGetFacade(object);
            return object;

        } catch (NoResultException e) {
            throw new HttpException(404);
        }
    }

}
