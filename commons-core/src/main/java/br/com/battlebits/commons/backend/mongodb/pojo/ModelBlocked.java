package br.com.battlebits.commons.backend.mongodb.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class ModelBlocked {

    private UUID uniqueId;
    private Long blockedTime;
}
