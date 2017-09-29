package com.log.aggregator.retry;

import com.google.common.base.Function;

import java.util.List;

/**
 * Created by yashkhandelwal on 29/09/17
 */
public class RetryService<ObjectType, RetryOutput> {

    private Function<List<ObjectType>, RetryOutput> retryAction;

    public RetryService(Function<List<ObjectType>, RetryOutput> retryAction) {
        this.retryAction = retryAction;
    }

    public RetryOutput retry(List<ObjectType> object) {
        if (object != null) {
            return retryAction.apply(object);
        }
        return null;
    }
}
