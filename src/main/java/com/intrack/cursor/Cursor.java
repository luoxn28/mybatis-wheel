package com.intrack.cursor;

import java.io.Closeable;
import java.util.Iterator;

/**
 * 游标是一段私有的SQL工作区,也就是一段内存区域,用于暂时存放受SQL语句影响到的数据。
 * 通俗理解就是将受影响的数据暂时放到了一个内存区域的虚表中，而这个虚表就是游标。
 *
 * @author intrack
 */
public interface Cursor<T> extends Closeable, Iterator<T> {

    /**
     * @return true if the cursor has started to fetch items from database.
     */
    boolean isOpen();

    /**
     * @return true if the cursor is fully consumed and has returned all elements matching the query.
     */
    boolean isConsumed();

    /**
     * Get the current item index. The first item has the index 0.
     * @return -1 if the first cursor item has not been retrieved. The index of the current item retrieved.
     */
    int getCurrentIndex();

}
