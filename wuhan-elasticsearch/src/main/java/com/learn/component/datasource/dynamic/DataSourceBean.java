package com.learn.component.datasource.dynamic;

/**
 * @author dshuyou
 * @date 2019/12/5 11:07
 * @description This class is used to encapsulate the data source properties
 */
public class DataSourceBean {
    private final String beanName;		//注册在spring中bean名字
    private final String driverClassName;
    private final String url;
    private final String username;
    private final String password;
    private final String validationQuery;
    private final Boolean testOnBorrow;
    private final String dataSouceType;

    public String getBeanName() {
        return beanName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public String getDataSouceType() {
        return dataSouceType;
    }

    public DataSourceBean(DataSourceBeanBuilder builder) {
        this.beanName = builder.getBeanName();
        this.driverClassName = builder.getDriverClassName();
        this.url = builder.getFullUrl();
        this.username = builder.getUsername();
        this.password = builder.getPassword();
        this.validationQuery = builder.getValidationQuery();
        this.testOnBorrow = builder.getTestOnBorrow();
        this.dataSouceType = builder.getDataSourceType();
    }

    public static class DataSourceBeanBuilder {
        private String beanName;
        private String driverClassName = "com.mysql.jdbc.Driver";
        private String url = "jdbc:mysql://%s:%s/%s";
        private String fullUrl;
        private String databaseIP;
        private String databasePort;
        private String databaseName;
        private String username;
        private String password;
        private String validationQuery = "select 1";
        private Boolean testOnBorrow = true;
        private String dataSourceType = "com.alibaba.druid.pool.DruidDataSource";
        public DataSourceBeanBuilder(String beanName, String databaseName, String driverClassName, String fullUrl,
                                     String username, String password) {
            super();
            this.beanName = beanName;
            this.databaseName = databaseName;
            this.driverClassName = driverClassName;
            this.fullUrl = fullUrl;
            this.username = username;
            this.password = password;
        }

        public DataSourceBeanBuilder(String beanName, String databaseIP, String databasePort, String databaseName,
                                     String driverClassName, String username, String password) {
            super();
            this.beanName = beanName;
            this.databaseIP = databaseIP;
            this.databasePort = databasePort;
            this.databaseName = databaseName;
            this.driverClassName = driverClassName;
            this.username = username;
            this.password = password;
        }
        public DataSourceBeanBuilder() {
            super();
        }
        public DataSourceBeanBuilder driverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
            return this;
        }
        public DataSourceBeanBuilder validationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
            return this;
        }
        public DataSourceBeanBuilder testOnBorrow(Boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
            return this;
        }

        public DataSourceBeanBuilder dataSourceType(String dataSourceType) {
            this.dataSourceType = dataSourceType;
            return this;
        }
        public String getUrl() {
            return String.format(url,this.databaseIP,this.databasePort,this.databaseName);
        }

        public String getFullUrl() {
            return fullUrl;
        }

        public String getBeanName() {
            return beanName;
        }
        public String getDriverClassName() {
            return driverClassName;
        }
        public String getDatabaseIP() {
            return databaseIP;
        }
        public String getDatabasePort() {
            return databasePort;
        }
        public String getDatabaseName() {
            return databaseName;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public String getValidationQuery() {
            return validationQuery;
        }
        public Boolean getTestOnBorrow() {
            return testOnBorrow;
        }

        public String getDataSourceType() {
            return dataSourceType;
        }
    }
}
