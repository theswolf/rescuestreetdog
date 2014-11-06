package core.september.rescue.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@Service
public class FileService {

	@Autowired
	private GridFsOperations gridOperations;

	public static class StoredFile {
		String mimeType;
		String encodedFile;

		public String getMimeType() {
			return mimeType;
		}

		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getEncodedFile() {
			return encodedFile;
		}

		public void setEncodedFile(String encodedFile) {
			this.encodedFile = encodedFile;
		}

	}

	public boolean storeFile(InputStream file, String ownerId, String title,
			String mimeType) {
		DBObject metaData = new BasicDBObject();
		metaData.put("owner", ownerId);
		try {
			gridOperations.store(file, title, mimeType, metaData);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<StoredFile> getForOwner(String owner) {
		DBObject metaData = new BasicDBObject();
		metaData.put("owner", owner);
		List<StoredFile> retList = new LinkedList<>();
		List<GridFSDBFile> result = gridOperations.find(new Query()
				.addCriteria(Criteria.where("metadata").is(metaData)));

		for (GridFSDBFile file : result) {
			try {
				File tempFile = File.createTempFile(System.currentTimeMillis()
						+ "", ".tmp");
				file.writeTo(tempFile);
				String encoded = Base64.getEncoder().encodeToString(
						read(tempFile));
				StoredFile stored = new StoredFile();
				stored.setEncodedFile(encoded);
				stored.setMimeType(file.getContentType());
				retList.add(stored);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return retList;

	}

	private byte[] read(File file) throws IOException {

		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();
	}

}
