package com.meli.cupon.testutils

import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

class ResourceFileReader {

    private ResourceFileReader() {}

    def static getFileContentAsString(String filename) {
        def inputStream =
                Optional.ofNullable(ResourceFileReader.class.getClassLoader().getResourceAsStream(filename))
                        .orElseThrow(() -> new IllegalArgumentException("el archivo $filename no fue encontrado."))
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"))
    }
}
