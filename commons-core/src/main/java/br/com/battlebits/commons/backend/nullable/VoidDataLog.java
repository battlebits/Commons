package br.com.battlebits.commons.backend.nullable;

import br.com.battlebits.commons.backend.logging.DataLog;
import br.com.battlebits.commons.backend.logging.DataLogType;
import br.com.battlebits.commons.server.ServerType;

import java.util.UUID;

public class VoidDataLog implements DataLog {
    @Override
    public void log(DataLogType logType) {

    }

    @Override
    public void log(DataLogType logType, UUID uuid) {

    }

    @Override
    public void log(DataLogType logType, UUID uuid, int protocol) {

    }
}
