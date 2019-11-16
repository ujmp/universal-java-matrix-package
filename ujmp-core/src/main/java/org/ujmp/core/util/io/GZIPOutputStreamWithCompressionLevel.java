package org.ujmp.core.util.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPOutputStreamWithCompressionLevel extends GZIPOutputStream {

    public GZIPOutputStreamWithCompressionLevel(OutputStream out, int compressionLevel) throws IOException {
        super(out);
        def.setLevel(compressionLevel);
    }
}
