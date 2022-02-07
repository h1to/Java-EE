package finalexam.AOP;

import lombok.extern.java.Log;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Entered
@Interceptor
@Log
public class DiscountBefore implements Serializable {
    @AroundInvoke
    protected Object protocolInvocation(final InvocationContext invocationContext) throws Exception {
        if(invocationContext.getMethod().getName().equals("selectDiscounts")) {
            log.info ("Selecting all from discount");
        }
        if(invocationContext.getMethod().getName().equals("insertDiscount")) {
            log.info ("Adding discount");
        }
        if(invocationContext.getMethod().getName().equals("deleteDiscount")) {
            log.info ("Deleting discount");
        }
        return invocationContext.proceed();
    }
}