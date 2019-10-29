package br.com.battlebits.commons.backend.logging;

import java.util.UUID;

public interface DataLog {


    void log(DataLogType logType);

    void log(DataLogType logType, UUID uuid);

    void log(DataLogType logType, UUID uuid, int protocol);

}
