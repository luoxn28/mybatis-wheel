package com.intrack.mapping;

import com.intrack.transaction.TransactionFactory;

import javax.sql.DataSource;

/**
 * @author intrack
 */
public final class Environment {

    private final String id;
    private final TransactionFactory transactionFactory;
    private final DataSource dataSource;

    public Environment(String id, TransactionFactory transactionFactory, DataSource dataSource) {
        if ((id == null) || (transactionFactory == null) || (dataSource == null)) {
            throw new IllegalArgumentException("id/transactionFactory/dataSource must not null.");
        }

        this.id = id;
        this.transactionFactory = transactionFactory;
        this.dataSource = dataSource;
    }

    public Environment(Builder builder) {
        this(builder.id, builder.transactionFactory, builder.dataSource);
    }

    public String getId() {
        return this.id;
    }

    public TransactionFactory getTransactionFactory() {
        return this.transactionFactory;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * 建造者模式
     */
    public static class Builder {
        private String id;
        private TransactionFactory transactionFactory;
        private DataSource dataSource;

        public Builder(String id) {
            this.id = id;
        }

        public Builder transactionFactory(TransactionFactory transactionFactory) {
            this.transactionFactory = transactionFactory;
            return this;
        }

        public Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public Environment build() {
            return new Environment(this.id, this.transactionFactory, this.dataSource);
        }
    }

}
