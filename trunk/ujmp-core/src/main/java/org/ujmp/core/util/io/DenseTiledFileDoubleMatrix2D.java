package org.ujmp.core.util.io;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.ujmp.core.Coordinates;
import org.ujmp.core.util.MathUtil;

public class DenseTiledFileDoubleMatrix2D extends DenseTiledDoubleBufferMatrix2D implements
		Closeable, Flushable {
	private static final long serialVersionUID = 4286077108162242925L;

	private final RandomAccessFile randomAccessFile;

	public DenseTiledFileDoubleMatrix2D(long... size) throws IOException {
		this(createTempFile(), true, size);
	}

	private static File createTempFile() throws IOException {
		File file = File.createTempFile("ujmp-matrix", ".dat");
		file.deleteOnExit();
		return file;
	}

	public DenseTiledFileDoubleMatrix2D(File file, boolean writable, long... size)
			throws IOException {
		this(createRandomAccessFile(file, writable, size), writable, size);
	}

	public DenseTiledFileDoubleMatrix2D(File file, long... size) throws IOException {
		this(createRandomAccessFile(file, false, size), false, size);
	}

	private DenseTiledFileDoubleMatrix2D(RandomAccessFile randomAccessFile, boolean writable,
			long... size) throws IOException {
		super(DEFAULT_TILE_SIZE, createMappedByteBuffers(randomAccessFile, DEFAULT_TILE_SIZE,
				writable), pickSize(randomAccessFile, size));
		this.randomAccessFile = randomAccessFile;
	}

	private static long[] pickSize(RandomAccessFile randomAccessFile, long... size)
			throws IOException {
		if (size.length < 2) {
			return new long[] { randomAccessFile.length() / 8, 1 };
		} else {
			return size;
		}
	}

	public DenseTiledFileDoubleMatrix2D(String filename, long... size) throws IOException {
		this(new File(filename), size);
	}

	public DenseTiledFileDoubleMatrix2D(String filename, boolean writable, long... size)
			throws IOException {
		this(new File(filename), writable, size);
	}

	public static RandomAccessFile createRandomAccessFile(File file, boolean writable, long... size)
			throws IOException {
		final RandomAccessFile ra;
		if (writable) {
			ra = new RandomAccessFile(file, "rw");
			if (size.length > 1 && ra.length() != 8 * Coordinates.product(size)) {
				ra.setLength(8 * Coordinates.product(size));
			}
		} else {
			ra = new RandomAccessFile(file, "r");
		}
		return ra;
	}

	public static DoubleBuffer[] createMappedByteBuffers(RandomAccessFile ra, int[] bufferSize,
			boolean writable) throws IOException {
		final int bufferLength = MathUtil.longToInt(Coordinates.product(bufferSize));
		final MapMode mapMode;
		if (writable) {
			mapMode = MapMode.READ_WRITE;
		} else {
			mapMode = MapMode.READ_ONLY;
		}

		final FileChannel fc = ra.getChannel();
		final long fileLength = fc.size();

		final int bufferCount = (int) Math.ceil((double) fileLength / 8.0 / (double) bufferLength);
		final DoubleBuffer[] buffers = new DoubleBuffer[bufferCount];
		int i = 0;
		for (long filePos = 0; filePos < fileLength; filePos += bufferLength * 8) {
			ByteBuffer buf = fc.map(mapMode, filePos,
					Math.min(bufferLength * 8, fileLength - filePos));
			buffers[i++] = buf.asDoubleBuffer();
		}
		return buffers;
	}

	public void close() throws IOException {
		// fc.close();
		if (randomAccessFile != null) {
			randomAccessFile.close();
		}
	}

	public void flush() throws IOException {
		// for (DoubleBuffer mb : bufferArray) {
		// if (mb instanceof MappedByteBuffer)
		// ((MappedByteBuffer) mb).force();
		// }
	}

}
