package com.intrack.session;

/**
 * 分页用的，分页查询用于大批量数据显示，如果数据表中有上千上万数据，采用分页，
 * 一次查询20条数据，提高查询效率，便于显示
 *
 * @author intrack
 */
public class RowBounds {

    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
    public static final RowBounds DEFAULT = new RowBounds();

    private int offset;
    private int limit;

    public RowBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

}
