package com.eap.common.reliability;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Narrow classifier for failures that indicate that a database connection cannot be established.
 *
 * <p>Lock conflicts, serialization failures, constraint violations, and arbitrary application
 * exceptions deliberately do not match. Those failures must not pause every database-backed
 * listener in a service.</p>
 */
public final class TransientDatabaseOutageClassifier {

    private static final String SPRING_CANNOT_GET_CONNECTION =
            "org.springframework.jdbc.CannotGetJdbcConnectionException";
    private static final String HIBERNATE_CONNECTION_EXCEPTION =
            "org.hibernate.exception.JDBCConnectionException";

    private TransientDatabaseOutageClassifier() {
    }

    public static boolean isDatabaseUnavailable(Throwable failure) {
        Set<Throwable> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        Throwable current = failure;
        boolean insideDatabaseFailureChain = false;
        while (current != null && visited.add(current)) {
            String typeName = current.getClass().getName();
            if (SPRING_CANNOT_GET_CONNECTION.equals(typeName)
                    || HIBERNATE_CONNECTION_EXCEPTION.equals(typeName)) {
                return true;
            }
            if (current instanceof SQLException sqlException) {
                insideDatabaseFailureChain = true;
                if (isDatabaseConnectionState(sqlException.getSQLState())) {
                    return true;
                }
            } else if (insideDatabaseFailureChain && isNetworkTransportFailure(current)) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }

    private static boolean isDatabaseConnectionState(String sqlState) {
        return sqlState != null && (sqlState.startsWith("08")
                || "57P01".equals(sqlState)
                || "57P02".equals(sqlState)
                || "57P03".equals(sqlState));
    }

    private static boolean isNetworkTransportFailure(Throwable failure) {
        if (failure instanceof ConnectException
                || failure instanceof NoRouteToHostException
                || failure instanceof UnknownHostException) {
            return true;
        }
        if (failure instanceof SocketException) {
            String message = failure.getMessage();
            return message != null && (message.contains("Connection reset")
                    || message.contains("Broken pipe")
                    || message.contains("Connection refused"));
        }
        return false;
    }
}
