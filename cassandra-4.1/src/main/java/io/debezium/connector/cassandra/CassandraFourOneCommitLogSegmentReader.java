/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.connector.cassandra;

import java.io.File;
import java.io.IOException;

import org.apache.cassandra.db.commitlog.CommitLogPosition;
import org.apache.cassandra.db.commitlog.CommitLogReadHandler;
import org.apache.cassandra.db.commitlog.CommitLogReader;

public class CassandraFourOneCommitLogSegmentReader implements CommitLogSegmentReader {

    private final CommitLogReader commitLogReader;

    private final CommitLogReadHandler commitLogReadHandler;

    public CassandraFourOneCommitLogSegmentReader(CassandraConnectorContext context, CommitLogProcessorMetrics metrics) {
        this.commitLogReader = new CommitLogReader();
        this.commitLogReadHandler = new CassandraFourOneCommitLogReadHandlerImpl(context, metrics);
    }

    @Override
    public void readCommitLogSegment(File file, long segmentId, int position) throws IOException {
        commitLogReader.readCommitLogSegment(commitLogReadHandler, new org.apache.cassandra.io.util.File(file), new CommitLogPosition(segmentId, position),
                CommitLogReader.ALL_MUTATIONS, false);
    }

}