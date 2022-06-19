package com.onurcelik.gerimedica.exception;

public class UnsupportedFileException extends RuntimeException {

    public UnsupportedFileException(String fileFormat) {
        super("System does not support the file format: " + fileFormat);
    }
}
