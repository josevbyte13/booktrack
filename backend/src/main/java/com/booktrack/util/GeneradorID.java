package com.booktrack.util;

import java.util.UUID;

public class GeneradorID {

    public static String generarIdSeguimiento() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return "BT-" + uuid.substring(0, 8);
    }

    public static String generarIdRepartidor() {
        return "REP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}