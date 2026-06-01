package com.backend_app.backend_app.dto;

import oracle.jdbc.driver.OracleConnection;
import org.springframework.jdbc.core.support.AbstractSqlTypeValue;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlArrayValue extends AbstractSqlTypeValue {

    private final Object[] values;
    private final String typeName;

    public SqlArrayValue(Object[] values, String typeName) {
        this.values = values;
        this.typeName = typeName;
    }

    @Override
    protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
        // Unwrap a OracleConnection, ya que Oracle no soporta createArrayOf estándar
        OracleConnection oracleConn = conn.unwrap(OracleConnection.class);
        return oracleConn.createOracleArray(this.typeName, values);
    }

}
