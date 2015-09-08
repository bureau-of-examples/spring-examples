package zhy2002.springexamples.algorithm;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JOHNZ
 * Date: 2/12/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface VisitListCallback<T extends List<?>> {
    boolean Visit(T list);
}
