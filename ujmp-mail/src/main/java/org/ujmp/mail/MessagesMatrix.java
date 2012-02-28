package org.ujmp.mail;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.mail.Folder;
import javax.mail.MessagingException;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.listmatrix.AbstractListMatrix;

public class MessagesMatrix extends AbstractListMatrix<Matrix> implements
		Closeable {
	private static final long serialVersionUID = -8489199262755536077L;

	private MessagesList list = null;

	public MessagesMatrix(String url, String user, String password,
			String folderName) throws Exception {
		ImapUtil util = new ImapUtil(url, user, password);
		Folder folder = util.getFolder(folderName);
		this.list = new MessagesList(folder);
	}

	public MessagesMatrix(Folder folder) {
		this.list = new MessagesList(folder);
	}

	
	public List<Matrix> getList() {
		return list;
	}

	public void setObject(Matrix value, long row, long column) {
	}

	public void setObject(Matrix value, int row, int column) {
	}

	
	public void close() throws IOException {
		list.close();
	}

}

class MessagesList implements List<Matrix>, Closeable {

	private Folder folder = null;

	private final Map<Integer, MessageMatrix> messages = new SoftHashMap<Integer, MessageMatrix>();

	public MessagesList(Folder folder) {
		this.folder = folder;
	}

	
	public boolean add(Matrix e) {
		throw new MatrixException("not implemented");
	}

	
	public void add(int index, Matrix element) {
		throw new MatrixException("not implemented");
	}

	
	public boolean addAll(Collection<? extends Matrix> c) {
		throw new MatrixException("not implemented");
	}

	
	public boolean addAll(int index, Collection<? extends Matrix> c) {
		throw new MatrixException("not implemented");
	}

	
	public void clear() {
		throw new MatrixException("not implemented");
	}

	
	public boolean contains(Object o) {
		throw new MatrixException("not implemented");
	}

	
	public boolean containsAll(Collection<?> c) {
		throw new MatrixException("not implemented");
	}

	
	public Matrix get(int index) {
		MessageMatrix m = messages.get(index);
		if (m == null) {
			try {
				if (!folder.isOpen()) {
					folder.open(Folder.READ_ONLY);
				}
				m = new MessageMatrix(folder.getMessage(index + 1));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			messages.put(index, m);
		}
		return m;
	}

	
	public int indexOf(Object o) {
		throw new MatrixException("not implemented");
	}

	
	public boolean isEmpty() {
		try {
			return folder.getMessageCount() == 0;
		} catch (MessagingException e) {
			e.printStackTrace();
			return true;
		}
	}

	
	public Iterator<Matrix> iterator() {
		return new Iterator<Matrix>() {

			int index = 0;

			
			public boolean hasNext() {
				return index < size();
			}

			
			public Matrix next() {
				Matrix m = get(index);
				index++;
				return m;
			}

			
			public void remove() {
				throw new MatrixException("not implemented");
			}
		};

	}

	
	public int lastIndexOf(Object o) {
		throw new MatrixException("not implemented");
	}

	
	public ListIterator<Matrix> listIterator() {
		throw new MatrixException("not implemented");
	}

	
	public ListIterator<Matrix> listIterator(int index) {
		throw new MatrixException("not implemented");
	}

	
	public boolean remove(Object o) {
		throw new MatrixException("not implemented");
	}

	
	public Matrix remove(int index) {
		throw new MatrixException("not implemented");
	}

	
	public boolean removeAll(Collection<?> c) {
		throw new MatrixException("not implemented");
	}

	
	public boolean retainAll(Collection<?> c) {
		throw new MatrixException("not implemented");
	}

	
	public Matrix set(int index, Matrix element) {
		throw new MatrixException("not implemented");
	}

	
	public int size() {
		try {
			return folder.getMessageCount();
		} catch (MessagingException e) {
			e.printStackTrace();
			return -1;
		}
	}

	
	public List<Matrix> subList(int fromIndex, int toIndex) {
		throw new MatrixException("not implemented");
	}

	
	public Object[] toArray() {
		throw new MatrixException("not implemented");
	}

	
	public <T> T[] toArray(T[] a) {
		throw new MatrixException("not implemented");
	}

	
	public void close() throws IOException {
		try {
			folder.close(false);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
