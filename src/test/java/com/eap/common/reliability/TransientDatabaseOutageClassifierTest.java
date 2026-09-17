package com.eap.common.reliability;

import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.net.SocketException;
import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransientDatabaseOutageClassifierTest {

    @Test
    void classifiesSqlStateConnectionFailuresThroughWrappers() {
        RuntimeException failure = new RuntimeException(
                "listener failed",
                new SQLException("database unavailable", "08006"));

        assertTrue(TransientDatabaseOutageClassifier.isDatabaseUnavailable(failure));
    }

    @Test
    void classifiesNetworkConnectionFailuresOnlyInsideJdbcCauseChain() {
        assertTrue(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new SQLException("driver transport failure", null,
                        new SocketException("Connection reset"))));
        assertFalse(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new RuntimeException(new ConnectException("RabbitMQ connection refused"))));
    }

    @Test
    void classifiesPostgresShutdownAndCannotConnectStates() {
        assertTrue(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new SQLException("administrator shutdown", "57P01")));
        assertTrue(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new SQLException("crash shutdown", "57P02")));
        assertTrue(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new SQLException("cannot connect now", "57P03")));
    }

    @Test
    void doesNotClassifyDeadlockOrSerializationFailureAsDatabaseOutage() {
        assertFalse(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new SQLTransactionRollbackException("serialization failure", "40001")));
    }

    @Test
    void doesNotClassifyPoisonOrInvariantFailuresAsDatabaseOutage() {
        assertFalse(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new IllegalArgumentException("missing order id")));
        assertFalse(TransientDatabaseOutageClassifier.isDatabaseUnavailable(
                new SQLException("unique violation", "23505")));
    }
}
